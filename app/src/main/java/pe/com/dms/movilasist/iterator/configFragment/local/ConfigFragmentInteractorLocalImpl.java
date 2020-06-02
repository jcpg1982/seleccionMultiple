package pe.com.dms.movilasist.iterator.configFragment.local;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class ConfigFragmentInteractorLocalImpl implements IConfigFragmentInteractorLocal {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ConfigFragmentInteractorLocalImpl(@NonNull DataSourceLocal dataSourceLocal,
                                             @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Configuracion> obtainConfig() {
        return mLocal.getConfiguracionOffLine();
    }

    @Override
    public Single<Long> saveConfigOffLine(Configuracion configuracion) {
        return mLocal.setConfiguracionOffLine(configuracion);
    }
}
