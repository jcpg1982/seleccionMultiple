package pe.com.dms.movilasist.iterator.splashActivity.local;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;

public class SplashInteractorImpl implements ISplashInteractor {

    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public SplashInteractorImpl(@NonNull DataSourceLocal dataSourceLocal,
                                @NonNull PreferenceManager preferences) {
        mLocal = dataSourceLocal;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Long[]> insertUser(List<Usuario> listUser) {
        return mLocal.setListUsuarioOffLine(listUser);
    }

    @Override
    public Maybe<Long[]> insertTypeMarcacion(List<TypeMarcacion> listTypeMarcacion) {
        return mLocal.setListTypeMarcacionOffLine(listTypeMarcacion);
    }

    @Override
    public Maybe<Long[]> insertStatusPermiso(List<StatusSolicitud> listStatusSolicitud) {
        return mLocal.setListStatusSolicitudOffLine(listStatusSolicitud);
    }
}
