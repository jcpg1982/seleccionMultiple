package pe.com.dms.movilasist.iterator.listSolicPermFragment.remote;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.model.response.ResponseListSolicPerm;

public interface IListSolicPermFragmentIteratorRemote {

    Maybe<Message> validarConexion();

    Maybe<ResponseListSolicPerm> getListSolicPermOnLine(RequestListSolicPers requestListSolicPers);

    Maybe<Message> getDeleteSolicPermOnLine(RequestDeleteSolicitud requestDeleteSolicitud);
}
