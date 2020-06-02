package pe.com.dms.movilasist.bd;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.model.request.RequestAprobSolicitud;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.model.request.RequestDeleteSolicitud;
import pe.com.dms.movilasist.model.request.RequestListAprobSolicPers;
import pe.com.dms.movilasist.model.request.RequestListLastMarc;
import pe.com.dms.movilasist.model.request.RequestListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarcGraphics;
import pe.com.dms.movilasist.model.request.RequestListMarcPers;
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
import pe.com.dms.movilasist.model.request.RequestListSolicPers;
import pe.com.dms.movilasist.model.request.RequestLogin;
import pe.com.dms.movilasist.model.response.ResponseListSolicPerm;
import pe.com.dms.movilasist.model.response.ResponseListSupervisores;
import pe.com.dms.movilasist.model.response.ResponseLogin;

public interface IDataSource {

    interface local extends IDataSource {

        //region Usuario
        void voidUsuarioOffLine(Usuario user);

        void voidListUsuarioOffLine(List<Usuario> listUser);

        Single<Long> setUsuarioOffLine(Usuario user);

        Maybe<Long[]> setListUsuarioOffLine(List<Usuario> listUser);

        Maybe<Usuario> validarUsuario(String user, String password);

        Maybe<Usuario> obtainUsuarioWithDocument(String document);

        Maybe<Usuario> obtainUser();
        //endregion

        //region Configuracion
        void voidConfiguracionOffLine(List<Configuracion> configuracion);

        Single<Long> setConfiguracionOffLine(Configuracion configuracion);

        Single<Long[]> setListConfiguracionOffLine(List<Configuracion> ListConfig);


        Maybe<Configuracion> getConfiguracionOffLine();
        //endregion

        //region Supervisor
        void voidListSupervisoresOffLine(List<Supervisor> listSuper);

        Maybe<List<Supervisor>> getListSupervisoresOffLine();
        //endregion

        //region motivos
        void voidListMotivoOffLine(List<Motivo> listMotivo);

        Maybe<List<Motivo>> getListMotivoOffLine();
        //endregion

        //region personal
        void voidListPersonalOffLine(List<Personal> listPersonal);
        //endregion

        //region conceptos
        void voidListConceptosOffLine(List<Conceptos> listConceptos);

        Maybe<List<Conceptos>> getListConceptosOffLine();
        //endregion

        //region marcacion
        Single<Long> sendMarcOffLine(Marcacion marcacion);

        Maybe<List<Marcaciones>> setListLastMarcacionesOffLine();

        Maybe<List<Marcaciones>> setListMarcacionesOffLine();

        Single<Long> deleteLastMarcOffLine(Marcacion marcacion);

        Maybe<List<Marcaciones>> obtainListMarcWithDate(RequestListMarc requestListMarc);

        Maybe<List<Marcacion>> getAllMarcacionesOffLine(@StatusServer String statusServer);

        Single<Integer>  deleteMarcacionesEnviadas(List<Integer> listCodEnviados);
        //endregion

        //region solicutud permiso
        Single<Long> sendSolicPermOffLine(SolicitudPermiso solicitudPermiso);

        Maybe<List<SolicitudPermiso>> getAllSolicitudPermisoOffLine(@StatusServer String statusServer);

        Single<Integer>  deletePermisosEnviados(List<Integer> listCodEnviados);
        //endregion

        //region tipo de marcacion
        Maybe<Long[]> setListTypeMarcacionOffLine(List<TypeMarcacion> listTypeMarcacion);

        Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine();
        //endregion

        //region status solicitud
        Maybe<Long[]> setListStatusSolicitudOffLine(List<StatusSolicitud> listStatusSolicitud);

        Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine();
        //endregion
    }

    interface remote extends IDataSource {

        //region Conexxion
        Maybe<Message> validarConnection();
        //endregion

        //region Login
        Maybe<ResponseLogin> login(RequestLogin requestLogin);

        Observable<ResponseInfoUser> infoUserOnLine(HashMap<String, String> body);

        Maybe<Message> recoveryPassword(String correo);
        //endregion

        //region Personal
        Observable<ResponseListPersonal> getListPersonalOnLine(HashMap<String, String> body);
        //endregion

        //region Conceptos
        Observable<ResponseListConcepto> getListConceptosOnLine();
        //endregion

        //region Motivo
        Observable<ResponseListMotivos> getListMotivoOnLine();
        //endregion

        //region Supervisor
        Observable<ResponseListSupervisores> getlistSupervisoresOnLine(HashMap<String, String> body);
        //endregion

        //region Configuarcion
        Observable<ResponseConfiguracion> getConfiguracionOnLine();
        //endregion

        //regionMarcacion
        Observable<Message> setMarcOnLine(Marcacion marcacion);

        Maybe<ResponseListLastMarcaciones> getListLastMarcOnLine(RequestListLastMarc requestListLastMarc);

        Maybe<ResponseListMarcaciones> getListMarcOnLine(RequestListMarc requestListMarc);

        Maybe<ResponseListMarcaciones> getListMarcPersOnLine(RequestListMarcPers requestListMarcPers);

        Maybe<ResponseListGraphicsMarcaciones> getListMarcGraphicsOnLine(RequestListMarcGraphics requestListMarcGraphics);

        Maybe<Message> deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc);

        Maybe<ResponseCargaMarcacion> cargaSetMarcOnLine(List<Marcacion> listMarcacion);
        //endregion

        //region Solicitud Permiso
        Observable<Message> setSolicPermOnLine(SolicitudPermiso solicitudPermiso);

        Maybe<ResponseListSolicPerm> getListSolicPermOnLine(RequestListSolicPers requestListSolicPers);

        Maybe<ResponseListSolicPerm> getListAprobSolicPermOnLine(RequestListAprobSolicPers requestListAprobSolicPers);

        Maybe<Message> getDeletePermOnLine(RequestDeleteSolicitud requestDeleteSolicitud);

        Maybe<Message> getAprobPermOnLine(List<RequestAprobSolicitud> requestAprobSolicitud);

        Maybe<Message> getEditPermOnLine(SolicitudPermiso solicitudPermiso);

        Maybe<ResponseCargaPermisos> cargaSetSolicPermOnLine(List<SolicitudPermiso> ListSolicitudPermiso);
        //endregion
    }
}
