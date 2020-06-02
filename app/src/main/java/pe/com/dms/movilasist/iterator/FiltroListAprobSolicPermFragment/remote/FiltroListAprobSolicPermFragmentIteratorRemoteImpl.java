package pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;

public class FiltroListAprobSolicPermFragmentIteratorRemoteImpl implements IFiltroListAprobSolicPermFragmentIteratorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public FiltroListAprobSolicPermFragmentIteratorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                                              @NonNull DataSourceLocal mLocal,
                                                              @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        this.mLocal = mLocal;
        mPreferences = preferences;
    }

}
