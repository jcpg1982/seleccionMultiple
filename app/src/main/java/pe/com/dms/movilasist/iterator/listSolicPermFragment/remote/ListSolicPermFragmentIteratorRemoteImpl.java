package pe.com.dms.movilasist.iterator.listSolicPermFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.model.response.ResponseListSolicPerm;

public class ListSolicPermFragmentIteratorRemoteImpl implements IListSolicPermFragmentIteratorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListSolicPermFragmentIteratorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
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
    public Maybe<ResponseListSolicPerm> getListSolicPermOnLine(RequestListSolicPers requestListSolicPers) {
        return mRemote.getListSolicPermOnLine(requestListSolicPers);
    }

    @Override
    public Maybe<Message> getDeleteSolicPermOnLine(RequestDeleteSolicitud requestDeleteSolicitud) {
        return mRemote.getDeletePermOnLine(requestDeleteSolicitud);
    }
}