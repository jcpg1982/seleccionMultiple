package pe.com.dms.movilasist.iterator.recoveryPassword;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;

public class RecoveryPasswordFragmentInteractorRemoteImpl implements IRecoveryPasswordFragmentInteractorRemote {

    private final DataSourceRemote mRemote;
    private final PreferenceManager mPreferences;

    @Inject
    public RecoveryPasswordFragmentInteractorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                                        @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Message> validarConexion() {
        return mRemote.validarConnection();
    }

    @Override
    public Maybe<Message> recoveryPassword(String correo) {
        return mRemote.recoveryPassword(correo);
    }
}
