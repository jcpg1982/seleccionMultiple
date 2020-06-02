package pe.com.dms.movilasist.bd.source.remote;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.request.RequestAprobSolicitud;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListAprobSolicPers;
import pe.com.dms.movilasist.model.request.RequestListLastMarc;
import pe.com.dms.movilasist.model.request.RequestListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarcGraphics;
import pe.com.dms.movilasist.model.request.RequestListMarcPers;
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.model.request.RequestLogin;
import pe.com.dms.movilasist.model.response.ResponseCargaMarcacion;
import pe.com.dms.movilasist.model.response.ResponseCargaPermisos;
import pe.com.dms.movilasist.model.response.ResponseConfiguracion;
import pe.com.dms.movilasist.model.response.ResponseInfoUser;
import pe.com.dms.movilasist.model.response.ResponseListConcepto;
import pe.com.dms.movilasist.model.response.ResponseListGraphicsMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListLastMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMarcaciones;
import pe.com.dms.movilasist.model.response.ResponseListMotivos;
import pe.com.dms.movilasist.model.response.ResponseListPersonal;
import pe.com.dms.movilasist.model.response.ResponseListSolicPerm;
import pe.com.dms.movilasist.model.response.ResponseListSupervisores;
import pe.com.dms.movilasist.model.response.ResponseLogin;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebService {

    //region conexion
    @POST(Urls.TEST_CONNECTION)
    Maybe<Message> validarConnection();
    //endregion

    //region configuracion
    @GET(Urls.CONFIGURACION)
    Observable<ResponseConfiguracion> getConfiguracionOnLine();
    //endregion

    //region login
    @POST(Urls.LOGIN)
    Maybe<ResponseLogin> login(@Body RequestLogin requestLogin);
    //endregion

    //region recuperar contraseña
    @POST(Urls.RECOVERY_PASSWORD)
    Maybe<Message> recoveryPassword(@Path("Correo") String correo);
    //endregion

    //region carga inicial

    //region info user
    @POST(Urls.INFO_USER)
    Observable<ResponseInfoUser> infoUser(@Body HashMap<String, String> body);
    //endregion

    //region lista de personal
    @POST(Urls.LIST_PERSONAL)
    Observable<ResponseListPersonal> listPersonal(@Body HashMap<String, String> body);
    //endregion

    //region supervisores
    @POST(Urls.LIST_SUPERVISORES)
    Observable<ResponseListSupervisores> listSupervisores(@Body HashMap<String, String> body);
    //endregion

    //region lista de conceptos
    @GET(Urls.LIST_CONCEPTOS)
    Observable<ResponseListConcepto> listConceptos();
    //endregion

    //region lista de motivos
    @GET(Urls.LISTA_MOTIVOS_MARCA)
    Observable<ResponseListMotivos> listMotivo();
    //endregion

    //endregion

    //region marcaciones
    @POST(Urls.SAVE_MARC)
    Observable<Message> sendMarcOnLine(@Body Marcacion marcacion);

    @POST(Urls.LIST_LAST_MARC)
    Maybe<ResponseListLastMarcaciones> getListLastMarcOnLine(@Body RequestListLastMarc requestListLastMarc);

    @POST(Urls.LIST_MARC)
    Maybe<ResponseListMarcaciones> getListMarcOnLine(@Body RequestListMarc requestListMarc);

    @POST(Urls.LISTA_MARC_PERSONAL)
    Maybe<ResponseListMarcaciones> getListMarcPersOnLine(@Body RequestListMarcPers requestListMarcPers);

    @POST(Urls.LIST_MARC_GRAPHICS)
    Maybe<ResponseListGraphicsMarcaciones> getListMarcGraphicsOffLine(@Body RequestListMarcGraphics requestListMarcGraphics);

    @POST(Urls.DELETE_MARC)
    Maybe<Message> deleteLastMarcOnLine(@Body RequestDeleteLastMarc requestDeleteLastMarc);

    @POST(Urls.CARGA_SAVE_MARC)
    Maybe<ResponseCargaMarcacion> cargaSendMarcOnLine(@Body List<Marcacion> listMarcacion);
    //endregion

    //region solicitud permiso
    @POST(Urls.REG_SOLICITUD_PERMISO)
    Observable<Message> sendSolicPermOnLine(@Body SolicitudPermiso solicitudPermiso);

    @POST(Urls.LISTA_SOLICITUD_PERMISO)
    Maybe<ResponseListSolicPerm> getListSolicPermOnLine(@Body RequestListSolicPers requestListSolicPers);

    @POST(Urls.LIST_APROB_SOLICITUD_PERMISO)
    Maybe<ResponseListSolicPerm> getListAprobSolicPermOnLine(@Body RequestListAprobSolicPers requestListAprobSolicPers);

    @POST(Urls.DELETE_SOLICITUD_PERMISO)
    Maybe<Message> deleteSolicPermOnLine(@Body RequestDeleteSolicitud requestDeleteSolicitud);

    @POST(Urls.APROBAR_SOLICITUD)
    Maybe<Message> aprobSolicPermOnLine(@Body List<RequestAprobSolicitud> requestAprobSolicitud);

    @POST(Urls.EDIT_SOLICITUD_PERMISO)
    Maybe<Message> editSolicPermOnLine(@Body SolicitudPermiso solicitudPermiso);

    @POST(Urls.CARGA_REG_SOLICITUD_PERMISO)
    Maybe<ResponseCargaPermisos> cargaSendSolicPermOnLine(@Body List<SolicitudPermiso> listSolicitudPermiso);
    //endregion
}
