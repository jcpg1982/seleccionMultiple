package pe.com.dms.movilasist.iterator.solicPermFragment.remote;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.Message;

public interface ISolicPermFragmentIteratorRemote {

    Maybe<Message> validarConexion();

    Observable<Message> sendSolicPermOnLine(SolicitudPermiso solicitudPermiso);

    Maybe<Message> SetEdithSolicPermOnLine(SolicitudPermiso solicitudPermiso);
}
