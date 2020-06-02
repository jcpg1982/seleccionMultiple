package pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.solicHE;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.local.IListSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.remote.IListSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListSolicPers;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ListSolicHEFragmentPresenter extends BasePresenter<IListSolicHEFragmentContract.View>
        implements IListSolicHEFragmentContract.Presenter {

    String TAG = ListSolicHEFragmentPresenter.class.getSimpleName();

    @Inject
    IListSolicPermFragmentIteratorLocal iteratorLocal;
    @Inject
    IListSolicPermFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private List<SolicitudesPermiso> mListsolicPerm;
    private RequestListSolicPers mRequestListSolicPers;
    private RequestDeleteSolicitud mRequestDeleteSolicitud;
    private int allPages;
    private int currentPage;
    private int PAGE_SIZE = 20;
    private boolean isLastPage;

    @Inject
    public ListSolicHEFragmentPresenter() {
        mListsolicPerm = new ArrayList<>();
        if (mRequestListSolicPers == null)
            mRequestListSolicPers = new RequestListSolicPers();
        if (mRequestDeleteSolicitud == null)
            mRequestDeleteSolicitud = new RequestDeleteSolicitud();
    }

    @Override
    public void setResultListSolicPers(ResultListSolicPers resultListSolicPers) {
        mRequestListSolicPers.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
        mRequestListSolicPers.setDtmFechaInicio(resultListSolicPers.getDateIni());
        mRequestListSolicPers.setDtmFechaFin(resultListSolicPers.getDateFin());
        mRequestListSolicPers.setIntTipoUso(2);
        mRequestListSolicPers.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        mRequestListSolicPers.setIntMostrarPermisos(preferenceManager.getConfig().getIntMostrarPermisos());
        mRequestListSolicPers.setIntEstadoSolicitud(resultListSolicPers.getStatus());
        mRequestListSolicPers.setIntPaginaSize(PAGE_SIZE);
        validaConnection();
    }

    @Override
    public List<SolicitudesPermiso> getDataDummyListPerm() {
       /* mListsolicPerm.clear();
        SolicitudPermiso s1 = new SolicitudPermiso("12345678",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);
        SolicitudPermiso s2 = new SolicitudPermiso("12345679",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);
        SolicitudPermiso s3 = new SolicitudPermiso("12345680",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);
        SolicitudPermiso s4 = new SolicitudPermiso("12345681",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);
        SolicitudPermiso s5 = new SolicitudPermiso("12345682",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);
        SolicitudPermiso s6 = new SolicitudPermiso("12345683",
                "1", "1982/05/24", "2020/05/24",
                "00:00", "05:00", 1, 1,
                "Quiero permiso", "1", "supervisor@mail.com",
                1, 1, 1);

        mListsolicPerm.add(s1);
        mListsolicPerm.add(s2);
        mListsolicPerm.add(s3);
        mListsolicPerm.add(s4);
        mListsolicPerm.add(s5);
        mListsolicPerm.add(s6);*/
        return mListsolicPerm;
    }

    @Override
    public void validaConnection() {
        getView().showProgressbar("Espere", "Verificando su conexion a la base de datos");
        Log.e(TAG, "validaConnection");
        getCompositeDisposable().add(iteratorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getListSolicPermOnLine();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                            getView().viewListSolicPerm(null);
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : " + error);
                    Log.e(TAG, "validaConnection error : " + error.toString());
                    Log.e(TAG, "validaConnection error : " + error.getMessage());
                    Log.e(TAG, "validaConnection error : " + error.getLocalizedMessage());
                    getView().showErrorMessage(error.getMessage(), error.getMessage());
                    getView().viewListSolicPerm(null);
                }));
    }

    @Override
    public void getListSolicPermOnLine() {
        getView().showProgressbar("Espere", "Obteniendo datos del servidor");
        Log.e(TAG, "getListSolicPermOnLine");
        mRequestListSolicPers.setIntPaginaNum(1);
        getCompositeDisposable().add(iteratorRemote.getListSolicPermOnLine(mRequestListSolicPers)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    mListsolicPerm.clear();
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            mListsolicPerm = response.getSolicitud();
                            Log.e(TAG, "getListSolicPermOnLine subscribe : " + mListsolicPerm);
                            getView().viewListSolicPerm(mListsolicPerm);
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListSolicPermOnLine error : " + error);
                    Log.e(TAG, "getListSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "getListSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "getListSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public void getMoreListSolicPermOnLine(int page) {
        Log.e(TAG, "getMoreListSolicPermOnLine");
        mRequestListSolicPers.setIntPaginaNum(page);
        getCompositeDisposable().add(iteratorRemote.getListSolicPermOnLine(mRequestListSolicPers)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            mListsolicPerm.addAll(response.getSolicitud());
                            Log.e(TAG, "getListSolicPermOnLine subscribe : " + mListsolicPerm);
                            getView().viewListSolicPerm(mListsolicPerm);
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getMoreListSolicPermOnLine error : " + error);
                    Log.e(TAG, "getMoreListSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "getMoreListSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "getMoreListSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public void setRequestDeleteSolicitud(SolicitudesPermiso solicitud) {
        mRequestDeleteSolicitud.setIntIdmSolicitud(solicitud.getIntIdmSolicitud());
        mRequestDeleteSolicitud.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
        mRequestDeleteSolicitud.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());

        deleteSolicitudOnLine();
    }

    @Override
    public void deleteSolicitudOnLine() {
        getView().showProgressbar("Espere", "Eliminando su solicitud");
        Log.e(TAG, "deleteSolicitudOnLine");
        getCompositeDisposable().add(iteratorRemote.getDeleteSolicPermOnLine(mRequestDeleteSolicitud)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteSolicitudOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getView().mostrarMensaje("Su solicitud fue eliminada con exito");
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                            getView().viewListSolicPerm(null);
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteSolicitudOnLine error : " + error);
                    Log.e(TAG, "deleteSolicitudOnLine error : " + error.toString());
                    Log.e(TAG, "deleteSolicitudOnLine error : " + error.getMessage());
                    Log.e(TAG, "deleteSolicitudOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage(error.getMessage(), error.getMessage());
                    getView().viewListSolicPerm(null);
                }));
    }
}
