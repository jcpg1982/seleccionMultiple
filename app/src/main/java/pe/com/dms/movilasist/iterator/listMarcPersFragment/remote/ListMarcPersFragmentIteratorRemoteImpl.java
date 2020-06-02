package pe.com.dms.movilasist.iterator.listMarcPersFragment.remote;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestListMarcPers;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public class ListMarcPersFragmentIteratorRemoteImpl implements IListMarcPersFragmentIteratorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListMarcPersFragmentIteratorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
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
    public Maybe<ResponseListMarcaciones> getListMarcPersOnLine(RequestListMarcPers requestListMarcPers) {
        return mRemote.getListMarcPersOnLine(requestListMarcPers);
    }
}