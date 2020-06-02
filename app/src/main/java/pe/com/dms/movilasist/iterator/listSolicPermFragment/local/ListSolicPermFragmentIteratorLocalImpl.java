package pe.com.dms.movilasist.iterator.listSolicPermFragment.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class ListSolicPermFragmentIteratorLocalImpl implements IListSolicPermFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListSolicPermFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                  @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }
}
