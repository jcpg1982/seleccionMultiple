package pe.com.dms.movilasist.iterator.loginFragment.local;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.request.RequestLogin;

public interface ILoginFragmentInteractorLocal {

    Maybe<Usuario> loginOffLine(RequestLogin requestLogin);

    Maybe<Configuracion> getConfiguracionOffLine();

    Single<Long> setUsuarioOffLine(Usuario user);

}
