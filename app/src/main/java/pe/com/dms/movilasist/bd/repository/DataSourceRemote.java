package pe.com.dms.movilasist.bd.repository;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import pe.com.dms.movilasist.bd.IDataSource;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.source.remote.WebService;
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

public class DataSourceRemote implements IDataSource.remote {

    private WebService api;

    @Inject
    public DataSourceRemote(WebService api) {
        this.api = api;
    }

    @Override
    public Maybe<Message> validarConnection() {
        return api.validarConnection();
    }

    @Override
    public Maybe<ResponseLogin> login(RequestLogin requestLogin) {
        return api.login(requestLogin);
    }

    @Override
    public Observable<ResponseInfoUser> infoUserOnLine(HashMap<String, String> body) {
        return api.infoUser(body);
    }

    @Override
    public Maybe<Message> recoveryPassword(String correo) {
        return api.recoveryPassword(correo);
    }

    @Override
    public Observable<ResponseListPersonal> getListPersonalOnLine(HashMap<String, String> body) {
        return api.listPersonal(body);
    }

    @Override
    public Observable<ResponseListConcepto> getListConceptosOnLine() {
        return api.listConceptos();
    }

    @Override
    public Observable<ResponseListMotivos> getListMotivoOnLine() {
        return api.listMotivo();
    }

    @Override
    public Observable<ResponseListSupervisores> getlistSupervisoresOnLine(HashMap<String, String> body) {
        return api.listSupervisores(body);
    }

    @Override
    public Observable<ResponseConfiguracion> getConfiguracionOnLine() {
        return api.getConfiguracionOnLine();
    }

    @Override
    public Observable<Message> setMarcOnLine(Marcacion marcacion) {
        return api.sendMarcOnLine(marcacion);
    }

    @Override
    public Maybe<ResponseListLastMarcaciones> getListLastMarcOnLine(RequestListLastMarc requestListLastMarc) {
        return api.getListLastMarcOnLine(requestListLastMarc);
    }

    @Override
    public Maybe<ResponseListMarcaciones> getListMarcOnLine(RequestListMarc requestListMarc) {
        return api.getListMarcOnLine(requestListMarc);
    }

    @Override
    public Maybe<ResponseListMarcaciones> getListMarcPersOnLine(RequestListMarcPers requestListMarcPers) {
        return api.getListMarcPersOnLine(requestListMarcPers);
    }

    @Override
    public Maybe<ResponseListGraphicsMarcaciones> getListMarcGraphicsOnLine(RequestListMarcGraphics requestListMarcGraphics) {
        return api.getListMarcGraphicsOffLine(requestListMarcGraphics);
    }

    @Override
    public Maybe<Message> deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc) {
        return api.deleteLastMarcOnLine(requestDeleteLastMarc);
    }

    @Override
    public Maybe<ResponseCargaMarcacion> cargaSetMarcOnLine(List<Marcacion> listMarcacion) {
        return api.cargaSendMarcOnLine(listMarcacion);
    }

    @Override
    public Observable<Message> setSolicPermOnLine(SolicitudPermiso solicitudPermiso) {
        return api.sendSolicPermOnLine(solicitudPermiso);
    }

    @Override
    public Maybe<ResponseListSolicPerm> getListSolicPermOnLine(RequestListSolicPers requestListSolicPers) {
        return api.getListSolicPermOnLine(requestListSolicPers);
    }

    @Override
    public Maybe<ResponseListSolicPerm> getListAprobSolicPermOnLine(RequestListAprobSolicPers requestListAprobSolicPers) {
        return api.getListAprobSolicPermOnLine(requestListAprobSolicPers);
    }

    @Override
    public Maybe<Message> getDeletePermOnLine(RequestDeleteSolicitud requestDeleteSolicitud) {
        return api.deleteSolicPermOnLine(requestDeleteSolicitud);
    }

    @Override
    public Maybe<Message> getAprobPermOnLine(List<RequestAprobSolicitud> requestAprobSolicitud) {
        return api.aprobSolicPermOnLine(requestAprobSolicitud);
    }

    @Override
    public Maybe<Message> getEditPermOnLine(SolicitudPermiso solicitudPermiso) {
        return api.editSolicPermOnLine(solicitudPermiso);
    }

    @Override
    public Maybe<ResponseCargaPermisos> cargaSetSolicPermOnLine(List<SolicitudPermiso> ListSolicitudPermiso) {
        return api.cargaSendSolicPermOnLine(ListSolicitudPermiso);
    }
}
