package pe.com.dms.movilasist.iterator.lastMarcFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.Marcaciones;

public class LastMarcFragmentInteractorLocalImpl implements ILastMarcFragmentInteractorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public LastMarcFragmentInteractorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                               @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<List<Marcaciones>> setListLastMarcacionesOffLine() {
        return mLocal.setListLastMarcacionesOffLine();
    }

    @Override
    public Single<Long>  deleteLastMarcOffLine(Marcacion marcacion) {
        return mLocal.deleteLastMarcOffLine(marcacion);
    }
}
