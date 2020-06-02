package pe.com.dms.movilasist.iterator.lastMarcFragment.remote;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.model.request.RequestListLastMarc;
import pe.com.dms.movilasist.model.response.ResponseListLastMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public interface ILastMarcFragmentInteractorRemote {

    Maybe<Message> validarConexion();

    Maybe<ResponseListLastMarcaciones> obtainLastMarcOnLine(RequestListLastMarc requestListLastMarc);

    Maybe<Message> deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc);

}
