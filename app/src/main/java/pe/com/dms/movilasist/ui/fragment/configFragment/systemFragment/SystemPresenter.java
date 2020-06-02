package pe.com.dms.movilasist.ui.fragment.configFragment.systemFragment;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.iterator.configFragment.local.IConfigFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.configFragment.remote.IConfigFragmentInteractorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.util.TextUtils;

public class SystemPresenter extends BasePresenter<ISystemContract.View>
        implements ISystemContract.Presenter {

    String TAG = SystemPresenter.class.getSimpleName();

    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    IConfigFragmentInteractorLocal interactorLocal;
    @Inject
    IConfigFragmentInteractorRemote interactorRemote;
    @Inject
    PreferenceManager preferenceManager;

    private String currentIdTerminal;
    private Configuracion mConfig;
    private Configuracion mConfigUpdate;

    @Inject
    SystemPresenter() {
        if (mConfig == null)
            mConfig = new Configuracion();
        if (mConfigUpdate == null)
            mConfigUpdate = new Configuracion();
    }

    @Override
    public void currentIdTerminal() {
        currentIdTerminal = preferenceManager.getidTerminal();
        if (!TextUtils.isEmpty(currentIdTerminal))
            getView().mostrarIdTerminal(currentIdTerminal);
    }

    @Override
    public void setNewIdTerminal(String newIdTerminal) {
        Log.e(TAG, "setNewIdTerminal: " + newIdTerminal);
        Log.e(TAG, "currentIdTerminal: " + currentIdTerminal);
        if (!TextUtils.isEmpty(currentIdTerminal)) {
            if (currentIdTerminal.equals(newIdTerminal))
                getView().setHasModifiedIdTerminal(false);
            else
                getView().setHasModifiedIdTerminal(true);
        } else {
            getView().setHasModifiedIdTerminal(true);
        }
    }

    @Override
    public void validaConnection() {
        Log.e(TAG, "validaConnection");
        getView().showProgressbar("Espere...", "Estamos verificando su conexion a la base de datos");
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", "");
                })
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            obtainConfigOnLine();
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
                    obtainConfigOffLine();
                }));
    }

    @Override
    public void obtainConfigOnLine() {
        Log.e(TAG, "obtainConfigOnLine");
        getView().showProgressbar("Espere...", "Estamos obteniendo su configuracion del servidor");
        getCompositeDisposable().add(interactorRemote.obtainConfig()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainConfigOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            if (response.getConfiguracion() != null
                                    && response.getConfiguracion().size() > 0) {
                                for (Configuracion item : response.getConfiguracion()) {
                                    mConfig = item;
                                    break;
                                }
                                Log.e(TAG, "obtainConfigOnLine subscribe mConfig: " + mConfig);
                                getView().cargarConfiguraciones(mConfig);
                            } else {
                                getView().showErrorMessage("No cuenta con configuracion almacenada", "No cuenta con configuracion almacenada");
                            }
                        } else {
                            getView().showErrorMessage(response.getMensaje().getVchMensaje(), response.getMensaje().getVchMensaje());
                        }
                    } else {
                        getView().showErrorMessage("Hubo un error al conectarse", "");
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainConfigOnLine error : " + error);
                    Log.e(TAG, "obtainConfigOnLine error : " + error.toString());
                    Log.e(TAG, "obtainConfigOnLine error : " + error.getMessage());
                    Log.e(TAG, "obtainConfigOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta.", error.getMessage());
                }));
    }

    @Override
    public void obtainConfigOffLine() {
        getView().showProgressbar("Espere...", "Estamos obteniendo su configuracion del dispositivo");
        Log.e(TAG, "obtainConfigOffLine");
        getCompositeDisposable().add(interactorLocal.obtainConfig()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainConfigOffLine error : ");
                    getView().showErrorMessage("Sin datos para mostrar.", "");
                })
                .subscribe(configuracion -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainConfigOnLine subscribe : " + configuracion);
                    mConfig = configuracion;
                    Log.e(TAG, "obtainConfigOnLine subscribe mConfig: " + mConfig);
                    getView().cargarConfiguraciones(mConfig);
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainConfigOffLine error : " + error);
                    Log.e(TAG, "obtainConfigOffLine error : " + error.toString());
                    Log.e(TAG, "obtainConfigOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainConfigOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta.", error.getMessage());
                }));
    }

    @Override
    public void setBitConGPS(int bitConLocalización) {
        Log.e(TAG, "setBitConGPS: " + bitConLocalización);
        mConfigUpdate.setBitConLocalizacion(bitConLocalización);
    }

    @Override
    public void setBitMarcacionGrupal(int bitMarcacionGrupal) {
        Log.e(TAG, "setBitMarcacionGrupal: " + bitMarcacionGrupal);
        mConfigUpdate.setBitMarcacionGrupal(bitMarcacionGrupal);
    }

    @Override
    public void setBitIdentificacionMarca(int bitIdentificacionMarca) {
        Log.e(TAG, "setBitIdentificacionMarca: " + bitIdentificacionMarca);
        mConfigUpdate.setBitIdentificacionMarca(bitIdentificacionMarca);
    }

    @Override
    public void setBitIntegracionVAWEB(int bitIntegraciónVAWEB) {
        Log.e(TAG, "setBitIntegraciónVAWEB: " + bitIntegraciónVAWEB);
        mConfigUpdate.setBitIntegracionVAWEB(bitIntegraciónVAWEB);
    }

    @Override
    public void setIntTiempoEntreMarca(int intTiempoEntreMarca) {
        Log.e(TAG, "setIntTiempoEntreMarca: " + intTiempoEntreMarca);
        mConfigUpdate.setIntTiempoEntreMarca(intTiempoEntreMarca);
    }

    @Override
    public void setIntMostrarMarca(int intMostrarMarca) {
        Log.e(TAG, "setIntMostrarMarca: " + intMostrarMarca);
        mConfigUpdate.setIntMostrarMarca(intMostrarMarca);
    }

    @Override
    public void setIntMostrarPermisos(int intMostrarPermisos) {
        Log.e(TAG, "setIntMostrarPermisos: " + intMostrarPermisos);
        mConfigUpdate.setIntMostrarPermisos(intMostrarPermisos);
    }

    @Override
    public void setBitMarcaPersonalNoExis(int bitMarcaPersonalNoExis) {
        Log.e(TAG, "setBitMarcaPersonalNoExis: " + bitMarcaPersonalNoExis);
        mConfigUpdate.setBitMarcaPersonalNoExis(bitMarcaPersonalNoExis);
    }

    @Override
    public void setBitLecturaPorCamara(int bitLecturaPorCamara) {
        Log.e(TAG, "setBitLecturaPorCamara: " + bitLecturaPorCamara);
        mConfigUpdate.setBitLecturaPorCamara(bitLecturaPorCamara);
    }

    @Override
    public void setBitColocarFotoMarca(int bitColocarFotoMarca) {
        Log.e(TAG, "setBitColocarFotoMarca: " + bitColocarFotoMarca);
        mConfigUpdate.setBitColocarFotoMarca(bitColocarFotoMarca);
    }

    @Override
    public boolean getModifiedConfig() {
        Log.e(TAG, "getModifiedConfig: " + mConfig.equals(mConfigUpdate));
        return mConfig.equals(mConfigUpdate);
    }

    @Override
    public Configuracion getConfiguracion() {
        Log.e(TAG, "getConfiguracion: " + mConfigUpdate);
        return mConfigUpdate;
    }
}
