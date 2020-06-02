package pe.com.dms.movilasist.iterator.listMarcFragment.remote;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarcGraphics;
import pe.com.dms.movilasist.model.response.ResponseListGraphicsMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public class ListMarcFragmentIteratorRemoteImpl implements IListMarcFragmentIteratorRemote {

    private final DataSourceRemote mRemote;
    private final DataSourceLocal mLocal;
    private final PreferenceManager mPreferences;

    @Inject
    public ListMarcFragmentIteratorRemoteImpl(@NonNull DataSourceRemote dataSourceRemote,
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
    public Maybe<ResponseListMarcaciones> getListMarcOnLine(RequestListMarc requestListMarc) {
        return mRemote.getListMarcOnLine(requestListMarc);
    }

    @Override
    public Maybe<ResponseListGraphicsMarcaciones> getListMarcCantDiariaOnLine(RequestListMarcGraphics requestListMarcGraphics) {
        return mRemote.getListMarcGraphicsOnLine(requestListMarcGraphics);
    }

    @Override
    public Maybe<ResponseListGraphicsMarcaciones> getListMarcCantSemanalOnLine(RequestListMarcGraphics requestListMarcGraphics) {
        return mRemote.getListMarcGraphicsOnLine(requestListMarcGraphics);
    }

    @Override
    public Maybe<ResponseListGraphicsMarcaciones> getListMarcCantMensualOnLine(RequestListMarcGraphics requestListMarcGraphics) {
        return mRemote.getListMarcGraphicsOnLine(requestListMarcGraphics);
    }
}