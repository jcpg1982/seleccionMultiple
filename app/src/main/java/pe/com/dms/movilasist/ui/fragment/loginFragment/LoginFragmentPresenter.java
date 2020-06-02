package pe.com.dms.movilasist.ui.fragment.loginFragment;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.loginFragment.remote.ILoginFragmentInteractorRemote;
import pe.com.dms.movilasist.model.request.RequestLogin;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class LoginFragmentPresenter extends BasePresenter<ILoginFragmentContract.View>
        implements ILoginFragmentContract.Presenter {

    String TAG = LoginFragmentPresenter.class.getSimpleName();

    @Inject
    ILoginFragmentInteractorLocal iteratorLocal;
    @Inject
    ILoginFragmentInteractorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private String mUser, mPassword;

    @Inject
    public LoginFragmentPresenter() {
    }


    @Override
    public void getConfigOffLine() {
        Log.e(TAG, "getConfigOffLine");
        getCompositeDisposable().add(iteratorLocal.getConfiguracionOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "getConfigOffLine doOnComplete : ");
                    getConfigOnLine();
                })
                .subscribe(configuracion -> {
                    Log.e(TAG, "getConfigOffLine subscribe : " + configuracion);
                    if (configuracion != null) {
                        preferenceManager.setconfig(configuracion);
                    }
                }, error -> {
                    Log.e(TAG, "getConfigOffLine error : " + error);
                    Log.e(TAG, "getConfigOffLine error : " + error.toString());
                    Log.e(TAG, "getConfigOffLine error : " + error.getMessage());
                    Log.e(TAG, "getConfigOffLine error : " + error.getLocalizedMessage());
                }));
    }

    @Override
    public void getConfigOnLine() {
        Log.e(TAG, "getConfigOnLine");
        getCompositeDisposable().add(iteratorRemote.getConfiguracionOnLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "getConfigOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            Configuracion configuracion = new Configuracion();
                            for (Configuracion item : response.getConfiguracion()) {
                                configuracion = item;
                                break;
                            }
                            preferenceManager.setconfig(configuracion);
                        }
                    }
                }, error -> {
                    Log.e(TAG, "getConfigOnLine error : " + error);
                    Log.e(TAG, "getConfigOnLine error : " + error.toString());
                    Log.e(TAG, "getConfigOnLine error : " + error.getMessage());
                    Log.e(TAG, "getConfigOnLine error : " + error.getLocalizedMessage());
                }));
    }

    @Override
    public void setUser(String user) {
        mUser = user;
    }

    @Override
    public void setPassword(String password) {
        mPassword = password;
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
                            loginOnLine();
                        } else {
                            getView().showErrorMessage("No hay conexion con la base de datos.", response.getVchMensaje());
                            loginOffLine();
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : " + error);
                    Log.e(TAG, "validaConnection error : " + error.toString());
                    Log.e(TAG, "validaConnection error : " + error.getMessage());
                    Log.e(TAG, "validaConnection error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                    loginOffLine();
                }));
    }

    @Override
    public void loginOnLine() {
        getView().showProgressbar("Inicio de sesion", "Iniciando sesion por favor espere");
        Log.e(TAG, "loginOnLine : ");
        getCompositeDisposable().add(iteratorRemote.login(requestLogin())
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "loginOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje() != null) {
                            if (response.getMensaje().getIntValor() == 1) {
                                if (response.getUsuario() != null) {
                                    preferenceManager.setUser(response.getUsuario());
                                    preferenceManager.setTypeUser(response.getUsuario().getIntTipoPerfil());
                                    if (response.getUsuario().getIntTipoPerfil() != 1) {
                                        preferenceManager.setDocumentUser(response.getUsuario().getVchDocumento());
                                        syncTablas();
                                        setUserOffLine(response.getUsuario());
                                    } else {
                                        getView().moveToMain();
                                    }
                                }
                            } else {
                                getView().showWarningMessage("Usuario Incorrecto revise su información");
                            }
                        } else {
                            getView().showErrorMessage("Usuario incorrecto", "");
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "loginOnLine error : " + error);
                    Log.e(TAG, "loginOnLine error : " + error.toString());
                    Log.e(TAG, "loginOnLine error : " + error.getMessage());
                    Log.e(TAG, "loginOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", error.getMessage());
                }));
    }

    @Override
    public void loginOffLine() {
        getView().showProgressbar("Inicio de sesion", "Iniciando sesion por favor espere");
        Log.e(TAG, "loginOfLine : ");
        getCompositeDisposable().add(iteratorLocal.loginOffLine(requestLogin())
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "loginOfLine doOnComplete : ");
                })
                .subscribe(usuario -> {
                    getView().hiddenProgressbar();
                    preferenceManager.setTypeUser(usuario.getIntTipoPerfil());
                    preferenceManager.setDocumentUser(usuario.getVchDocumento());
                    preferenceManager.setUser(usuario);
                    Log.e(TAG, "loginOfLine subscribe : " + usuario);
                    getView().moveToMain();
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "loginOfLine error : " + error);
                    Log.e(TAG, "loginOfLine error : " + error.toString());
                    Log.e(TAG, "loginOfLine error : " + error.getMessage());
                    Log.e(TAG, "loginOfLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", error.getMessage());
                }));
    }

    @Override
    public void syncTablas() {
        Log.e(TAG, "syncTablas: ");
        getView().showProgressbar("Espere", "Sincronizando su informacion con el servidor");
        HashMap<String, String> body = new HashMap<>();
        body.put("vchDocumento", preferenceManager.getDocumentUser());
        List<Observable> observableList = new ArrayList<>(Arrays.asList(
                iteratorRemote.getInfoUserOnLine(body),
                iteratorRemote.getListSupervisoresOnLine(body),
                iteratorRemote.getListPersonalOnLine(body),
                iteratorRemote.getListMotivoOnLine(),
                iteratorRemote.getListConceptosOnLine()
        ));

        getCompositeDisposable().add(Observable.fromIterable(observableList)
                .subscribeOn(ExecutorThread)
                .flatMap((Function<Observable, ObservableSource<?>>) observable -> observable.observeOn(UiThread))
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "syncTablas doOnComplete: ");
                    getView().moveToMain();
                })
                .subscribe(list -> {
                    Log.e(TAG, "syncTablas subscribe: " + list);
                }, throwable -> {
                    if (isViewAttached()) {
                        getView().hiddenProgressbar();
                        getView().showErrorMessage("No se pudo sincronizar las tablas.", throwable.getMessage());
                        Log.e(TAG, "syncTablas throwable: " + throwable);
                        Log.e(TAG, "syncTablas throwable: " + throwable.toString());
                        Log.e(TAG, "syncTablas throwable: " + throwable.getMessage());
                        Log.e(TAG, "syncTablas throwable: " + throwable.getLocalizedMessage());
                        Log.e(TAG, "syncTablas throwable: " + throwable.getCause());
                    }
                })
        );
    }

    private void setUserOffLine(Usuario usuario) {
        Log.e(TAG, "loginOfLine : ");
        getCompositeDisposable().add(iteratorLocal.setUsuarioOffLine(usuario)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "loginOfLine subscribe : " + usuario);
                }, error -> {
                    Log.e(TAG, "loginOfLine error : " + error);
                    Log.e(TAG, "loginOfLine error : " + error.toString());
                    Log.e(TAG, "loginOfLine error : " + error.getMessage());
                    Log.e(TAG, "loginOfLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", error.getMessage());
                }));
    }

    private RequestLogin requestLogin() {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setVchDocumento(mUser);
        requestLogin.setVchPassword(mPassword);
        Log.e(TAG, "requestLogin: " + requestLogin);
        return requestLogin;
    }
}
