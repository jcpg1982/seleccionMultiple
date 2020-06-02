package pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment;

import android.text.method.TextKeyListener;
import android.util.Log;
import android.widget.EditText;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.configFragment.remote.IConfigFragmentInteractorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ConfigIpPresenter extends BasePresenter<IConfigIpContract.View>
        implements IConfigIpContract.Presenter {

    String TAG = ConfigIpPresenter.class.getSimpleName();

    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    IConfigFragmentInteractorRemote interactorRemote;

    private String mCurrentIp;
    private String mNewCurrentIp;
    private boolean mHasModified;

    @Inject
    ConfigIpPresenter() {
    }

    @Override
    public void currentIp() {
        mCurrentIp = preferenceManager.getWebService();
        getView().mostrarIp(mCurrentIp);
    }

    @Override
    public void validateConnection() {
        Log.e(TAG, "validateConnection");
        getView().showProgressbar("Espere...", "Estamos verificando su conexion a la base de datos");
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validateConnection error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "validateConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getView().showSuccessMessage("Configuracion Existosa");
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validateConnection error : " + error);
                    Log.e(TAG, "validateConnection error : " + error.toString());
                    Log.e(TAG, "validateConnection error : " + error.getMessage());
                    Log.e(TAG, "validateConnection error : " + error.getLocalizedMessage());
                    getView().showWarningMessage("Error en la conexion");
                }));
    }

    @Override
    public void setNewIp(String newIp) {
        mNewCurrentIp = newIp;
        Log.e(TAG, "mNewCurrentIp: " + mNewCurrentIp);
        Log.e(TAG, "newIp: " + newIp);
        Log.e(TAG, "currentIp: " + mCurrentIp);
        if (mNewCurrentIp.equals(mCurrentIp))
            mHasModified = false;
        else
            mHasModified = true;
    }

    @Override
    public void setEditable(EditText editText, boolean editable) {
        if (editable) {
            editText.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.WORDS, false));
            editText.requestFocus();
        } else {
            editText.setKeyListener(null);
        }
    }

    @Override
    public boolean hasModified() {
        return mHasModified;
    }

    @Override
    public void saveIp() {
        if (mHasModified) {
            preferenceManager.setWebService(mNewCurrentIp);
            getView().reiniciar();
        } else {
            getView().closed();
        }
    }
}
