package pe.com.dms.movilasist.iterator.navigationDrawer.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class NavigationDrawerFragmentInteractorImpl implements INavigationDrawerFragmentInteractor {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public NavigationDrawerFragmentInteractorImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                  @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Usuario> obtainUsuarioWithDocument(String document) {
        return mLocal.obtainUsuarioWithDocument(document);
    }
}
