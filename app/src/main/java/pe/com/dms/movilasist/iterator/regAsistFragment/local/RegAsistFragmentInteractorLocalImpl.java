package pe.com.dms.movilasist.iterator.regAsistFragment.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.model.Marcaciones;

public class RegAsistFragmentInteractorLocalImpl implements IRegAsistFragmentInteractorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public RegAsistFragmentInteractorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                               @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Configuracion> getConfigOffLine() {
        return mLocal.getConfiguracionOffLine();
    }

    @Override
    public Maybe<Usuario> getUserOffLine(String document) {
        return mLocal.obtainUsuarioWithDocument(document);
    }

    @Override
    public Maybe<List<Motivo>> getListMotivoOffLine() {
        return mLocal.getListMotivoOffLine();
    }

    @Override
    public Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine() {
        return mLocal.getListTypeMarcacionOffLine();
    }

    @Override
    public Single<Long> sendMarcOffLine(Marcacion marcacion) {
        return mLocal.sendMarcOffLine(marcacion);
    }
}
