package pe.com.dms.movilasist.iterator.listMarcPersFragment.remote;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestListMarcPers;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;

public interface IListMarcPersFragmentIteratorRemote {

    Maybe<Message> validarConexion();

    Maybe<ResponseListMarcaciones> getListMarcPersOnLine(RequestListMarcPers mRequestListMarcPers);
}
