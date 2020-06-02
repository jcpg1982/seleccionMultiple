package pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicPerm;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.local.IListAprobSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.remote.IListAprobSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.model.request.RequestAprobSolicitud;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListAprobSolicPers;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.util.DateUtils;

public class ListAprobSolicPermFragmentPresenter extends BasePresenter<IListAprobSolicPermFragmentContract.View>
        implements IListAprobSolicPermFragmentContract.Presenter {

    String TAG = ListAprobSolicPermFragmentPresenter.class.getSimpleName();

    @Inject
    IListAprobSolicPermFragmentIteratorLocal iteratorLocal;
    @Inject
    IListAprobSolicPermFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private List<SolicitudesPermiso> mListsolicPerm;
    private RequestListAprobSolicPers mRequestListAprobSolicPers;
    private int allPages;
    private int currentPage;
    private int PAGE_SIZE = 20;
    private boolean isLastPage;

    @Inject
    public ListAprobSolicPermFragmentPresenter() {
        if (mListsolicPerm == null)
            mListsolicPerm = new ArrayList<>();
        if (mRequestListAprobSolicPers == null)
            mRequestListAprobSolicPers = new RequestListAprobSolicPers();
    }


    @Override
    public List<SolicitudesPermiso> getDataDummyListPerm() {
        mListsolicPerm.clear();
       /* SolicitudPermiso s1 = new SolicitudPermiso("12345678",
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
    public void setResultListApobSolic(ResultListApobSolic resultListApobSolic) {

        mRequestListAprobSolicPers.setVchCodSupervisor(preferenceManager.getUser().getVchCodigoPersonal());
        mRequestListAprobSolicPers.setVchCodPersonal(resultListApobSolic.getCodPersonal());
        mRequestListAprobSolicPers.setVchNombreApe(resultListApobSolic.getNameLastName());
        mRequestListAprobSolicPers.setDtmFechaInicio(resultListApobSolic.getDateIni());
        mRequestListAprobSolicPers.setDtmFechaFin(resultListApobSolic.getDateFin());
        mRequestListAprobSolicPers.setIntTipoUso(0);
        mRequestListAprobSolicPers.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        mRequestListAprobSolicPers.setIntMostrarPermisos(preferenceManager.getConfig().getIntMostrarPermisos());
        mRequestListAprobSolicPers.setIntEstadoSolicitud(resultListApobSolic.getStatus());
        mRequestListAprobSolicPers.setIntPaginaSize(PAGE_SIZE);
        validaConnection();
    }

    @Override
    public int allPages() {
        return allPages;
    }

    @Override
    public int currentPage() {
        return currentPage;
    }

    @Override
    public int PAGE_SIZE() {
        return PAGE_SIZE;
    }

    @Override
    public boolean islastPage() {
        return isLastPage;
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
                            getListAprobSolicPermOnLine();
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
    public void getListAprobSolicPermOnLine() {
        getView().showProgressbar("Espere", "Obteniendo datos del servidor");
        Log.e(TAG, "getListAprobSolicPermOnLine");
        mRequestListAprobSolicPers.setIntPaginaNum(1);
        getCompositeDisposable().add(iteratorRemote.getListAprobSolicPermOnLine(mRequestListAprobSolicPers)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            getView().viewListSolicPerm(response.getSolicitud());
                        } else {
                            getView().showErrorMessage(response.getMensaje().getVchMensaje(), response.getMensaje().getVchMensaje());
                            getView().viewListSolicPerm(null);
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error);
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage(error.getMessage(), error.getMessage());
                    getView().viewListSolicPerm(null);
                }));
    }

    @Override
    public void getMoreListSolicPermOnLine(int page) {

    }

    @Override
    public void deleteSolicitudOnLine(SolicitudesPermiso solicitudesPermiso) {
        RequestDeleteSolicitud requestDeleteSolicitud = new RequestDeleteSolicitud(
                solicitudesPermiso.getIntIdmSolicitud(), solicitudesPermiso.getVchCodPersonal(),
                preferenceManager.getConfig().getBitIntegracionVAWEB());
        getCompositeDisposable().add(iteratorRemote.getDeleteSolicPermOnLine(requestDeleteSolicitud)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getListAprobSolicPermOnLine();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error);
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage(error.getMessage(), error.getMessage());
                    getView().viewListSolicPerm(null);
                }));

    }

    @Override
    public void aprobeSolicitudOnLine(SolicitudesPermiso solicitudesPermiso) {
        getView().showProgressbar("Espere", "Aprobando su solicitud");
        List<RequestAprobSolicitud> listAprobe = new ArrayList<>();
        listAprobe.add(new RequestAprobSolicitud(solicitudesPermiso.getIntIdmSolicitud(),
                solicitudesPermiso.getVchCodPersonal(), solicitudesPermiso.getIntEstadoSolicitud(),
                solicitudesPermiso.getVchObservacion(), solicitudesPermiso.getVchCodSupervisor(),
                solicitudesPermiso.getVchEmailSupervisor(), 0,
                preferenceManager.getConfig().getBitIntegracionVAWEB()));

        getCompositeDisposable().add(iteratorRemote.getAprobarSolicPermOnLine(listAprobe)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getListAprobSolicPermOnLine();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error);
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "getListAprobSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage(error.getMessage(), error.getMessage());
                    getView().viewListSolicPerm(null);
                }));
    }
}
