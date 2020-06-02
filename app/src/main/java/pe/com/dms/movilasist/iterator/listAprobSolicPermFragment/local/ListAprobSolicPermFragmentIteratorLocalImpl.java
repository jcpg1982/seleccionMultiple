package pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class ListAprobSolicPermFragmentIteratorLocalImpl implements IListAprobSolicPermFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListAprobSolicPermFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                       @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }
}
