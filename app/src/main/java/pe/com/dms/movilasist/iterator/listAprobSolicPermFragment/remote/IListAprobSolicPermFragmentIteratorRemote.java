package pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.remote;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestAprobSolicitud;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListAprobSolicPers;
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.model.response.ResponseListSolicPerm;

public interface IListAprobSolicPermFragmentIteratorRemote {

    Maybe<Message> validarConexion();

    Maybe<ResponseListSolicPerm> getListAprobSolicPermOnLine(RequestListAprobSolicPers requestListAprobSolicPers);

    Maybe<Message> getDeleteSolicPermOnLine(RequestDeleteSolicitud requestDeleteSolicitud);

    Maybe<Message> getAprobarSolicPermOnLine(List<RequestAprobSolicitud> requestAprobSolicitud);
}
