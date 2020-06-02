package pe.com.dms.movilasist.iterator.configFragment.remote;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.response.ResponseConfiguracion;

public interface IConfigFragmentInteractorRemote {

    Maybe<Message> validarConexion();

    Observable<ResponseConfiguracion> obtainConfig();
}
