package pe.com.dms.movilasist.ui.fragment.recoveryPasword;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.recoveryPassword.IRecoveryPasswordFragmentInteractorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.util.Utils;

public class RecoveryPasswordFragmentPresenter extends BasePresenter<IRecoveryPasswordFragmentContract.View>
        implements IRecoveryPasswordFragmentContract.Presenter {

    String TAG = RecoveryPasswordFragmentPresenter.class.getSimpleName();

    @Inject
    IRecoveryPasswordFragmentInteractorRemote interactorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private String mCorreo;

    @Inject
    public RecoveryPasswordFragmentPresenter() {
    }


    @Override
    public void setCorreo(String correo) {
        mCorreo = correo;
    }

    @Override
    public boolean validateCorreo() {
        return Utils.validateEmailFormat(mCorreo);
    }

    @Override
    public void validaConnection() {
        getView().showProgressbar("Verificando su conexion", "");
        Log.e(TAG, "validaConnection");
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("Sin conexion a la base de datos.", "");
                })
                .subscribe(response -> {
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            recoveryPassword();
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
                }));
    }

    @Override
    public void recoveryPassword() {
        getView().showProgressbar("Verificando su conexion", "");
        Log.e(TAG, "recoveryPassword");
        getCompositeDisposable().add(interactorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "recoveryPassword error : ");
                    getView().showErrorMessage("Sin conexion a la base de datos.", "");
                })
                .subscribe(response -> {
                    Log.e(TAG, "recoveryPassword subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            recoveryPassword();
                        } else {
                            getView().showErrorMessage("No hay conexion con la base de datos.", response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "recoveryPassword error : " + error);
                    Log.e(TAG, "recoveryPassword error : " + error.toString());
                    Log.e(TAG, "recoveryPassword error : " + error.getMessage());
                    Log.e(TAG, "recoveryPassword error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al conectarse al servidor.", error.getMessage());
                }));
    }
}
