package pe.com.dms.movilasist.iterator.recoveryPassword;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.model.Message;

public interface IRecoveryPasswordFragmentInteractorRemote {

    Maybe<Message> validarConexion();

    Maybe<Message> recoveryPassword(String correo);

}
