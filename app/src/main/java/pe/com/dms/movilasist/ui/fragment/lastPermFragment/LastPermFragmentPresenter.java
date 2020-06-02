package pe.com.dms.movilasist.ui.fragment.lastPermFragment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.lastMarcFragment.local.ILastMarcFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.lastMarcFragment.remote.ILastMarcFragmentInteractorRemote;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class LastPermFragmentPresenter extends BasePresenter<ILastPermFragmentContract.View>
        implements ILastPermFragmentContract.Presenter {

    String TAG = LastPermFragmentPresenter.class.getSimpleName();

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
    private List<SolicitudesPermiso> mListsolicPerm;

    @Inject
    public LastPermFragmentPresenter() {
        mListsolicPerm = new ArrayList<>();
    }

    @Override
    public void validateConnection(@TypeOption int typeOption,
                                   SolicitudesPermiso marcacion) {
        getView().showProgressbar("Espere...", "Verificando la base de datos en el servidor");
        Log.e(TAG, "validaConnection");/**/
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("Conexion incorrecta verifique sus datos.", "");
                })
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
                                    deleteLastMarcOnLine(marcacion);
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
       /* getCompositeDisposable().add(interactorLocal.obtainLastMarcOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", "");
                })
                .subscribe(list -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOffLine subscribe : " + list);
                    getView().mostrarLastMarc((ArrayList<SolicitudPermiso>) list);
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error);
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.toString());
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainLastMarcOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));*/
    }

    @Override
    public void obtainLastMarcOnLine() {
        getView().showProgressbar("Espere..", "Estamos obteniendo su informacion del servidor");
        Log.e(TAG, "obtainLastMarcOnLine");
        /*getCompositeDisposable().add(interactorRemote.obtainLastMarcOnLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", "");
                })
                .subscribe(list -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOnLine subscribe : " + list);
                    getView().mostrarLastMarc((ArrayList<SolicitudPermiso>) list);
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error);
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.toString());
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "obtainLastMarcOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));*/
    }

    @Override
    public void deleteLastMarcOffLine(SolicitudesPermiso marcacion) {
        getView().showProgressbar("Espere..", "Estamos eliminando su marcación del dispositivo");
        Log.e(TAG, "deleteLastMarcOffLine");
        /*getCompositeDisposable().add(interactorLocal.deleteLastMarcOffLine(marcacion)
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
                }));*/
    }

    @Override
    public void deleteLastMarcOnLine(SolicitudesPermiso marcacion) {
       /* getView().showProgressbar("Espere..", "Estamos Eliminando su marcación del servidor");
        Log.e(TAG, "deleteLastMarcOnLine");
        getCompositeDisposable().add(interactorRemote.deleteLastMarcOnLine(marcacion)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteLastMarcOnLine error : ");
                    getView().showErrorMessage("Error.", "");
                })
                .subscribe(list -> {
                    Log.e(TAG, "deleteLastMarcOnLine subscribe : " + list);
                    getView().hiddenProgressbar();
                    getView().loadData();
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error);
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.toString());
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "deleteLastMarcOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al eliminar la marcación.", error.getMessage());
                }));*/
    }

    @Override
    public List<SolicitudesPermiso> getDataDummyListPerm() {
     /*   SolicitudPermiso s1 = new SolicitudPermiso("12345678",
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
}