package pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.Marcaciones;

public class FiltroListSolicPermFragmentIteratorLocalImpl implements IFiltroListSolicPermFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public FiltroListSolicPermFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                                        @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine() {
        return mLocal.getListStatusSolicitudOffLine();
    }
}
