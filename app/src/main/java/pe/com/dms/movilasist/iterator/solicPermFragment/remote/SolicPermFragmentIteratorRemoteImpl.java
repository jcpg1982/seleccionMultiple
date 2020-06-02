package pe.com.dms.movilasist.iterator.solicPermFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;

public class SolicPermFragmentIteratorRemoteImpl implements ISolicPermFragmentIteratorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public SolicPermFragmentIteratorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
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
    public Observable<Message> sendSolicPermOnLine(SolicitudPermiso solicitudPermiso) {
        return mRemote.setSolicPermOnLine(solicitudPermiso);
    }

    @Override
    public Maybe<Message> SetEdithSolicPermOnLine(SolicitudPermiso solicitudPermiso) {
        return mRemote.getEditPermOnLine(solicitudPermiso);
    }
}
