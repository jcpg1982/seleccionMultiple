package pe.com.dms.movilasist.ui.fragment.listLastMarcFragment;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.iterator.lastMarcFragment.local.ILastMarcFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.lastMarcFragment.remote.ILastMarcFragmentInteractorRemote;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.model.request.RequestListLastMarc;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ListLastMarcFragmentPresenter extends BasePresenter<IListLastMarcFragmentContract.View>
        implements IListLastMarcFragmentContract.Presenter {

    String TAG = ListLastMarcFragmentPresenter.class.getSimpleName();

    @Inject
    ILastMarcFragmentInteractorLocal interactorLocal;
    @Inject
    ILastMarcFragmentInteractorRemote interactorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;
    private RequestListLastMarc mRequestListLastMarc;

    @Inject
    public ListLastMarcFragmentPresenter() {
        if (mRequestListLastMarc == null)
            mRequestListLastMarc = new RequestListLastMarc();
    }

    @Override
    public void validateConnection(@TypeOption int typeOption,
                                   Marcacion marcacion,
                                   RequestDeleteLastMarc requestDeleteLastMarc) {
        getView().showProgressbar("Espere...", "Verificando la base de datos en el servidor");
        Log.e(TAG, "validaConnection");
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    getView().hiddenProgressbar();
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            switch (typeOption) {
                                case TypeOption.OBTAIN_LAST_MARC:
                                    obtainLastMarcOnLine();
                                    break;
                                case TypeOption.DELETE_LAST_MARC:
                                    requestDeleteLastMarc.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
                                    deleteLastMarcOnLine(requestDeleteLastMarc);
                                    break;
                            }
                        } else {
                            getView().showErrorMessage("No hay conexion con la base de datos.", response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : " + error);
                    Log.e(TAG, "validaConnection error : " + error.toString());
                    Log.e(TAG, "validaConnection error : " + error.getMessage());
                    Log.e(TAG, "validaConnection error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                    switch (typeOption) {
                        case TypeOption.OBTAIN_LAST_MARC:
                            obtainLastMarcOffLine();
                            break;
                        case TypeOption.DELETE_LAST_MARC:
                            deleteLastMarcOffLine(marcacion);
                            break;
                    }
                }));
    }

    @Override
    public void obtainLastMarcOffLine() {
        getView().showProgressbar("Espere..", "Estamos obteniendo su informacion del dispositivo");
        Log.e(TAG, "obtainLastMarcOffLine");
        getCompositeDisposable().add(interactorLocal.setListLastMarcacionesOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOffLine subscribe : " + list);
                    getView().mostrarLastMarc((ArrayList<Marcaciones>) list);
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error);
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.toString());
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta", error.getMessage());
                }));
    }

    @Override
    public void obtainLastMarcOnLine() {
        getView().showProgressbar("Espere..", "Estamos obteniendo su informacion del servidor");
        Log.e(TAG, "obtainLastMarcOnLine");
        mRequestListLastMarc.setIntLectura(0);
        mRequestListLastMarc.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
        mRequestListLastMarc.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        getCompositeDisposable().add(interactorRemote.obtainLastMarcOnLine(mRequestListLastMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOnLine subscribe : " + response);
                    getView().mostrarLastMarc((ArrayList<Marcaciones>) response.getMarca());
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error);
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.toString());
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta", error.getMessage());
                }));
    }

    @Override
    public void deleteLastMarcOffLine(Marcacion marcacion) {
        getView().showProgressbar("Espere..", "Estamos eliminando su marcación del dispositivo");
        Log.e(TAG, "deleteLastMarcOffLine");
        getCompositeDisposable().add(interactorLocal.deleteLastMarcOffLine(marcacion)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "deleteLastMarcOffLine subscribe : " + list);
                    getView().hiddenProgressbar();
                    getView().loadData();
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteLastMarcOffLine error : " + error);
                    Log.e(TAG, "deleteLastMarcOffLine error : " + error.toString());
                    Log.e(TAG, "deleteLastMarcOffLine error : " + error.getMessage());
                    Log.e(TAG, "deleteLastMarcOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al eliminar la marcación.", error.getMessage());
                }));
    }

    @Override
    public void deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc) {
        getView().showProgressbar("Espere..", "Estamos Eliminando su marcación del servidor");
        Log.e(TAG, "deleteLastMarcOnLine");
        getCompositeDisposable().add(interactorRemote.deleteLastMarcOnLine(requestDeleteLastMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "deleteLastMarcOnLine subscribe : " + response);
                    getView().hiddenProgressbar();
                    if (response.getIntValor() == 1) {
                        getView().loadData();
                    } else {
                        getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error);
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.toString());
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al eliminar la marcación.", error.getMessage());
                }));
    }
}