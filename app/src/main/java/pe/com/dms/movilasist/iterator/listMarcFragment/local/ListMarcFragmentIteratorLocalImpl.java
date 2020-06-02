package pe.com.dms.movilasist.iterator.listMarcFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestListMarc;

public class ListMarcFragmentIteratorLocalImpl implements IListMarcFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListMarcFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                             @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<List<Marcaciones>> obtainListMarcWithDate(RequestListMarc requestListMarc) {
        return mLocal.obtainListMarcWithDate(requestListMarc);
    }
}
