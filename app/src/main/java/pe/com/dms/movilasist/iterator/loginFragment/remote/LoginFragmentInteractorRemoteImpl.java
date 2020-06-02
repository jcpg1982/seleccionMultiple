package pe.com.dms.movilasist.iterator.loginFragment.remote;

import androidx.annotation.NonNull;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.response.ResponseConfiguracion;
import pe.com.dms.movilasist.model.response.ResponseInfoUser;
import pe.com.dms.movilasist.model.response.ResponseListConcepto;
import pe.com.dms.movilasist.model.response.ResponseListMotivos;
import pe.com.dms.movilasist.model.response.ResponseListPersonal;
import pe.com.dms.movilasist.model.request.RequestLogin;
import pe.com.dms.movilasist.model.response.ResponseListSupervisores;
import pe.com.dms.movilasist.model.response.ResponseLogin;

public class LoginFragmentInteractorRemoteImpl implements ILoginFragmentInteractorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public LoginFragmentInteractorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                             @NonNull DataSourceLocal dataSourceLocal,
                                             @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        mPreferences = preferences;
        mLocal = dataSourceLocal;
    }

    @Override
    public Observable<ResponseConfiguracion> getConfiguracionOnLine() {
        return mRemote.getConfiguracionOnLine().doOnNext(response -> mLocal.voidConfiguracionOffLine(response.getConfiguracion()));
    }

    @Override
    public Maybe<Message> validarConexion() {
        return mRemote.validarConnection();
    }

    @Override
    public Maybe<ResponseLogin> login(RequestLogin requestLogin) {
        return mRemote.login(requestLogin);
    }

    @Override
    public Observable<ResponseInfoUser> getInfoUserOnLine(HashMap<String, String> body) {
        return mRemote.infoUserOnLine(body).doOnNext(response -> mLocal.voidUsuarioOffLine(response.getUsuario()));
    }

    @Override
    public Observable<ResponseListPersonal> getListPersonalOnLine(HashMap<String, String> body) {
        return mRemote.getListPersonalOnLine(body).doOnNext(object -> mLocal.voidListPersonalOffLine(object.getPersonal()));
    }

    @Override
    public Observable<ResponseListConcepto> getListConceptosOnLine() {
        return mRemote.getListConceptosOnLine().doOnNext(response -> mLocal.voidListConceptosOffLine(response.getConcepto()));
    }

    @Override
    public Observable<ResponseListMotivos> getListMotivoOnLine() {
        return mRemote.getListMotivoOnLine().doOnNext(response -> mLocal.voidListMotivoOffLine(response.getMotivo()));
    }

    @Override
    public Observable<ResponseListSupervisores> getListSupervisoresOnLine(HashMap<String, String> body) {
        return mRemote.getlistSupervisoresOnLine(body).doOnNext(response -> mLocal.voidListSupervisoresOffLine(response.getSupervisor()));
    }
}
