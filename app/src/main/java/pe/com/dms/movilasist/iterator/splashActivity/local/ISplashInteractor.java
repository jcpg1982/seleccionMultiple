package pe.com.dms.movilasist.iterator.splashActivity.local;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;

public interface ISplashInteractor {

    Maybe<Long[]> insertUser(List<Usuario> listUser);

    Maybe<Long[]> insertTypeMarcacion(List<TypeMarcacion> listTypeMarcacion);

    Maybe<Long[]> insertStatusPermiso(List<StatusSolicitud> listStatusSolicitud);
}
