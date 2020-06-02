package pe.com.dms.movilasist.iterator.loginFragment.remote;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.model.response.ResponseConfiguracion;
import pe.com.dms.movilasist.model.response.ResponseInfoUser;
import pe.com.dms.movilasist.model.response.ResponseListConcepto;
import pe.com.dms.movilasist.model.response.ResponseListMotivos;
import pe.com.dms.movilasist.model.response.ResponseListPersonal;
import pe.com.dms.movilasist.model.request.RequestLogin;
import pe.com.dms.movilasist.model.response.ResponseListSupervisores;
import pe.com.dms.movilasist.model.response.ResponseLogin;

public interface ILoginFragmentInteractorRemote {

    Observable<ResponseConfiguracion> getConfiguracionOnLine();

    Maybe<Message> validarConexion();

    Maybe<ResponseLogin> login(RequestLogin requestLogin);

    Observable<ResponseInfoUser> getInfoUserOnLine(HashMap<String, String> body);

    Observable<ResponseListPersonal> getListPersonalOnLine(HashMap<String, String> body);

    Observable<ResponseListConcepto> getListConceptosOnLine();

    Observable<ResponseListMotivos> getListMotivoOnLine();

    Observable<ResponseListSupervisores> getListSupervisoresOnLine(HashMap<String, String> body);
}
