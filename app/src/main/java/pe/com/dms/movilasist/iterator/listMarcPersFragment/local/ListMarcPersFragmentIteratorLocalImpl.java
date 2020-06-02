package pe.com.dms.movilasist.iterator.listMarcPersFragment.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class ListMarcPersFragmentIteratorLocalImpl implements IListMarcPersFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListMarcPersFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                 @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }
}
