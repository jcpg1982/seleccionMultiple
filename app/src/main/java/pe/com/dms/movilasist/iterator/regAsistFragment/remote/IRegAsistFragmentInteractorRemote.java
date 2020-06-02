package pe.com.dms.movilasist.iterator.regAsistFragment.remote;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.Message;

public interface IRegAsistFragmentInteractorRemote {

    Maybe<Message> validarConexion();

   /* Observable<Usuario> getInfoUserOnLine();

    Observable<Configuracion> getConfigOnLine();

    Observable<List<Personal>> getlistPersonalOnLine();

    Observable<List<Conceptos>> getListConceptosOnLine();

    Observable<List<Motivo>> getListMotivoOnLine();

    Observable<List<Supervisor>> getListSupervisoresOnLine();

    Observable<Configuracion> getConfiguracionOnLine();*/

    Observable<Message> sendMarcOnLine(Marcacion marcacion);
}
