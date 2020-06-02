package pe.com.dms.movilasist.iterator.solicPermFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.Marcaciones;

public class SolicPermFragmentIteratorLocalImpl implements ISolicPermFragmentIteratorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public SolicPermFragmentIteratorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                              @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Single<Long> sendSolicPermOffLine(SolicitudPermiso solicitudPermiso) {
        return mLocal.sendSolicPermOffLine(solicitudPermiso);
    }

    @Override
    public Maybe<List<Conceptos>> getListConceptosOffLine() {
        return mLocal.getListConceptosOffLine();
    }

    @Override
    public Maybe<List<Supervisor>> getListSupervisoresOffLine() {
        return mLocal.getListSupervisoresOffLine();
    }
}
