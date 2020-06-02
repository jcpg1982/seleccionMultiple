package pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class FiltroListAprobSolicPermFragmentIteratorLocalImpl implements IFiltroListAprobSolicPermFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public FiltroListAprobSolicPermFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                             @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine() {
        return mLocal.getListStatusSolicitudOffLine();
    }
}
