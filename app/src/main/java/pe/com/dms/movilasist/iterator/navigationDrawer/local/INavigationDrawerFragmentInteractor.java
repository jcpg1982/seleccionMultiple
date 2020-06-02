package pe.com.dms.movilasist.iterator.navigationDrawer.local;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.Usuario;

public interface INavigationDrawerFragmentInteractor {

    Maybe<Usuario> obtainUsuarioWithDocument(String document);


}
