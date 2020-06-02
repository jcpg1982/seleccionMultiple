package pe.com.dms.movilasist.ui.fragment.configFragment;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.iterator.configFragment.local.IConfigFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.configFragment.remote.IConfigFragmentInteractorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ConfigPagerPresenter extends BasePresenter<IConfigPagerContract.View>
        implements IConfigPagerContract.Presenter {

    String TAG = ConfigPagerPresenter.class.getSimpleName();

    @Inject
    IConfigFragmentInteractorLocal interactorLocal;
    @Inject
    IConfigFragmentInteractorRemote interactorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private Configuracion mConfig;
    private String mModifiedIp;
    private String mModifiedIdTerminal;
    @TypeActivity
    private int mTypeActivity;

    @Inject
    public ConfigPagerPresenter() {
    }

    @Override
    public void setTypeActivity(int typeActivity) {
        mTypeActivity = typeActivity;
    }

    @Override
    public void setModifiedIp(String modifiedIp) {
        mModifiedIp = modifiedIp;
    }

    @Override
    public void setModifiedIdTerminal(String modifiedIdTerminal) {
        Log.e(TAG, "setModifiedIdTerminal modifiedIdTerminal: " + modifiedIdTerminal);
        mModifiedIdTerminal = modifiedIdTerminal;
    }

    @Override
    public void setConfiguracion(Configuracion configuracion) {
        Log.e(TAG, "setConfiguracion configuracion: " + configuracion);
        mConfig = configuracion;
        Log.e(TAG, "setConfiguracion mConfig: " + mConfig);
    }

    @Override
    public void saveConfigOffLine() {
        Log.e(TAG, "saveConfigOffLine: " + mConfig);
        getView().showProgressbar("Espere...", "Estamos guardando su informacion en el dispisitivo");
        getCompositeDisposable().add(interactorLocal.saveConfigOffLine(mConfig)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(configuracion -> {
                    preferenceManager.setconfig(mConfig);
                    getView().hiddenProgressbar();
                    Log.e(TAG, "saveConfigOffLine subscribe : " + configuracion);
                    getView().showSuccessMessage("Configuracion guardada con exito");
                    switch (mTypeActivity) {
                        case TypeActivity.CONFIG:
                            getView().moveToLogin();
                            break;
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "saveConfigOffLine error : " + error);
                    Log.e(TAG, "saveConfigOffLine error : " + error.toString());
                    Log.e(TAG, "saveConfigOffLine error : " + error.getMessage());
                    Log.e(TAG, "saveConfigOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta.", error.getMessage());
                }));
    }

    @Override
    public void saveIp() {
        preferenceManager.setWebService(mModifiedIp);
        getView().reiniciar();
    }

    @Override
    public void saveConfig() {
        preferenceManager.setIdTerminal(mModifiedIdTerminal);
        saveConfigOffLine();
    }
}
