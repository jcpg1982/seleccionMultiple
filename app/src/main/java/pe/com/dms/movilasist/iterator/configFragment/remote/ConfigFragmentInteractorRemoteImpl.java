package pe.com.dms.movilasist.iterator.configFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.response.ResponseConfiguracion;

public class ConfigFragmentInteractorRemoteImpl implements IConfigFragmentInteractorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ConfigFragmentInteractorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                              @NonNull DataSourceLocal dataSourceLocal,
                                              @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        mPreferences = preferences;
        mLocal = dataSourceLocal;
    }

    @Override
    public Maybe<Message> validarConexion() {
        return mRemote.validarConnection();
    }

    @Override
    public Observable<ResponseConfiguracion> obtainConfig() {
        return mRemote.getConfiguracionOnLine().doOnNext(response -> mLocal.voidConfiguracionOffLine(response.getConfiguracion()));
    }
}
