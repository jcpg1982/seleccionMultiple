package pe.com.dms.movilasist.iterator.loginFragment.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.request.RequestLogin;

public class LoginFragmentInteractorLocalImpl implements ILoginFragmentInteractorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public LoginFragmentInteractorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                            @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Usuario> loginOffLine(RequestLogin requestLogin) {
        return mLocal.validarUsuario(requestLogin.getVchDocumento(), requestLogin.getVchPassword());
    }

    @Override
    public Maybe<Configuracion> getConfiguracionOffLine() {
        return mLocal.getConfiguracionOffLine();
    }

    @Override
    public Single<Long> setUsuarioOffLine(Usuario user) {
        return mLocal.setUsuarioOffLine(user);
    }
}
