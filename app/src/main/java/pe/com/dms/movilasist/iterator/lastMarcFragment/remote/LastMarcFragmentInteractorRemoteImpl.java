package pe.com.dms.movilasist.iterator.lastMarcFragment.remote;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.model.request.RequestListLastMarc;
import pe.com.dms.movilasist.model.response.ResponseListLastMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public class LastMarcFragmentInteractorRemoteImpl implements ILastMarcFragmentInteractorRemote {

    private final DataSourceRemote mRemote;
    private final PreferenceManager mPreferences;

    @Inject
    public LastMarcFragmentInteractorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
                                                @NonNull PreferenceManager preferences) {
        mRemote = dataSourceRemote;
        mPreferences = preferences;
    }

    @Override
    public Maybe<Message> validarConexion() {
        return mRemote.validarConnection();
    }

    @Override
    public Maybe<ResponseListLastMarcaciones> obtainLastMarcOnLine(RequestListLastMarc requestListLastMarc) {
        return mRemote.getListLastMarcOnLine(requestListLastMarc);
    }

    @Override
    public Maybe<Message> deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc) {
        return mRemote.deleteLastMarcOnLine(requestDeleteLastMarc);
    }
}
