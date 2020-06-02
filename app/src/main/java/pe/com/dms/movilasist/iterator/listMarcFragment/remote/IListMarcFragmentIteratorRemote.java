package pe.com.dms.movilasist.iterator.listMarcFragment.remote;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarcGraphics;
import pe.com.dms.movilasist.model.response.ResponseListGraphicsMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public interface IListMarcFragmentIteratorRemote {

    Maybe<Message> validarConexion();

    Maybe<ResponseListMarcaciones> getListMarcOnLine(RequestListMarc requestListMarc);

    Maybe<ResponseListGraphicsMarcaciones> getListMarcCantDiariaOnLine(RequestListMarcGraphics requestListMarcGraphics);

    Maybe<ResponseListGraphicsMarcaciones> getListMarcCantSemanalOnLine(RequestListMarcGraphics requestListMarcGraphics);

    Maybe<ResponseListGraphicsMarcaciones> getListMarcCantMensualOnLine(RequestListMarcGraphics requestListMarcGraphics);
}
