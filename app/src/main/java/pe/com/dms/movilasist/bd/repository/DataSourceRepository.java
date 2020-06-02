package pe.com.dms.movilasist.bd.repository;


import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.IDataSource;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.Message;
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

public class DataSourceRepository implements IDataSource.local, IDataSource.remote {

    private DataSourceLocal mLocal;
    private DataSourceRemote mRemote;
    private Context mContext;

    private final ThreadPoolExecutor threadPoolExecute = new ThreadPoolExecutor(4,
            8, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(30));

    @Inject
    public DataSourceRepository(DataSourceLocal local, DataSourceRemote remote, Context context) {
        this.mContext = context;
        this.mLocal = local;
        this.mRemote = remote;
    }

    //region local

    //region Usuario

    @Override
    public void voidUsuarioOffLine(Usuario user) {
        mLocal.voidUsuarioOffLine(user);
    }

    @Override
    public void voidListUsuarioOffLine(List<Usuario> listUser) {
        mLocal.voidListUsuarioOffLine(listUser);
    }

    @Override
    public Single<Long> setUsuarioOffLine(Usuario user) {
        return mLocal.setUsuarioOffLine(user);
    }

    @Override
    public Maybe<Long[]> setListUsuarioOffLine(List<Usuario> listUser) {
        return mLocal.setListUsuarioOffLine(listUser);
    }

    @Override
    public Maybe<Usuario> validarUsuario(String user, String password) {
        return mLocal.validarUsuario(user, password);
    }

    @Override
    public Maybe<Usuario> obtainUsuarioWithDocument(String document) {
        return mLocal.obtainUsuarioWithDocument(document);
    }

    @Override
    public Maybe<Usuario> obtainUser() {
        return mLocal.obtainUser();
    }
    //endregion

    //region Configuracion
    @Override
    public void voidConfiguracionOffLine(List<Configuracion> configuracion) {
        mLocal.voidConfiguracionOffLine(configuracion);
    }

    @Override
    public Single<Long> setConfiguracionOffLine(Configuracion configuracion) {
        return mLocal.setConfiguracionOffLine(configuracion);
    }

    @Override
    public Single<Long[]> setListConfiguracionOffLine(List<Configuracion> ListConfig) {
        return mLocal.setListConfiguracionOffLine(ListConfig);
    }

    @Override
    public Maybe<Configuracion> getConfiguracionOffLine() {
        return mLocal.getConfiguracionOffLine();
    }
    //endregion

    //region Supervisor
    @Override
    public void voidListSupervisoresOffLine(List<Supervisor> listSuper) {
        mLocal.voidListSupervisoresOffLine(listSuper);
    }

    @Override
    public Maybe<List<Supervisor>> getListSupervisoresOffLine() {
        return mLocal.getListSupervisoresOffLine();
    }
    //endregion

    //region Motivo
    @Override
    public void voidListMotivoOffLine(List<Motivo> listMotivo) {
        mLocal.voidListMotivoOffLine(listMotivo);
    }

    @Override
    public Maybe<List<Motivo>> getListMotivoOffLine() {
        return mLocal.getListMotivoOffLine();
    }
    //endregion

    //region Personal
    @Override
    public void voidListPersonalOffLine(List<Personal> listPersonal) {
        mLocal.voidListPersonalOffLine(listPersonal);
    }
    //endregion

    //region Conceptos
    @Override
    public void voidListConceptosOffLine(List<Conceptos> listConceptos) {
        mLocal.voidListConceptosOffLine(listConceptos);
    }

    @Override
    public Maybe<List<Conceptos>> getListConceptosOffLine() {
        return mLocal.getListConceptosOffLine();
    }
    //endregion

    //region Marcacion
    @Override
    public Single<Long> sendMarcOffLine(Marcacion marcacion) {
        return mLocal.sendMarcOffLine(marcacion);
    }

    @Override
    public Maybe<List<Marcaciones>> setListLastMarcacionesOffLine() {
        return mLocal.setListLastMarcacionesOffLine();
    }

    @Override
    public Maybe<List<Marcaciones>> setListMarcacionesOffLine() {
        return mLocal.setListMarcacionesOffLine();
    }

    @Override
    public Single<Long> deleteLastMarcOffLine(Marcacion marcacion) {
        return mLocal.deleteLastMarcOffLine(marcacion);
    }

    @Override
    public Maybe<List<Marcaciones>> obtainListMarcWithDate(RequestListMarc requestListMarc) {
        return mLocal.obtainListMarcWithDate(requestListMarc);
    }

    @Override
    public Maybe<List<Marcacion>> getAllMarcacionesOffLine(String statusServer) {
        return mLocal.getAllMarcacionesOffLine(statusServer);
    }

    @Override
    public Single<Integer>  deleteMarcacionesEnviadas(List<Integer> listCodEnviados) {
        return mLocal.deleteMarcacionesEnviadas(listCodEnviados);
    }
    //endregion

    //region Solicitud Permiso
    @Override
    public Single<Long> sendSolicPermOffLine(SolicitudPermiso solicitudPermiso) {
        return mLocal.sendSolicPermOffLine(solicitudPermiso);
    }

    @Override
    public Maybe<List<SolicitudPermiso>> getAllSolicitudPermisoOffLine(String statusServer) {
        return mLocal.getAllSolicitudPermisoOffLine(statusServer);
    }

    @Override
    public Single<Integer>  deletePermisosEnviados(List<Integer> listCodEnviados) {
        return mLocal.deletePermisosEnviados(listCodEnviados);
    }
    //endregion

    //region tipo de marcacion
    @Override
    public Maybe<Long[]> setListTypeMarcacionOffLine(List<TypeMarcacion> listTypeMarcacion) {
        return mLocal.setListTypeMarcacionOffLine(listTypeMarcacion);
    }

    @Override
    public Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine() {
        return mLocal.getListTypeMarcacionOffLine();
    }
    //endregion

    //region status solicitud
    @Override
    public Maybe<Long[]> setListStatusSolicitudOffLine(List<StatusSolicitud> listStatusSolicitud) {
        return mLocal.setListStatusSolicitudOffLine(listStatusSolicitud);
    }

    @Override
    public Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine() {
        return mLocal.getListStatusSolicitudOffLine();
    }
    //endregion

    //endregion

    //region OnLine

    //region Conexion
    @Override
    public Maybe<Message> validarConnection() {
        return mRemote.validarConnection();
    }
    //endregion

    //region Usuario
    @Override
    public Maybe<ResponseLogin> login(RequestLogin requestLogin) {
        return mRemote.login(requestLogin);
    }

    @Override
    public Observable<ResponseInfoUser> infoUserOnLine(HashMap<String, String> body) {
        return mRemote.infoUserOnLine(body).doOnNext(response -> mLocal.voidUsuarioOffLine(response.getUsuario()));
    }

    @Override
    public Maybe<Message> recoveryPassword(String correo) {
        return mRemote.recoveryPassword(correo);
    }
    //endregion

    //region Personal
    @Override
    public Observable<ResponseListPersonal> getListPersonalOnLine(HashMap<String, String> body) {
        return mRemote.getListPersonalOnLine(body).doOnNext(response -> mLocal.voidListPersonalOffLine(response.getPersonal()));
    }
    //endregion

    //region Conceptos
    @Override
    public Observable<ResponseListConcepto> getListConceptosOnLine() {
        return mRemote.getListConceptosOnLine().doOnNext(response -> mLocal.voidListConceptosOffLine(response.getConcepto()));
    }
    //endregion

    //region Motivo
    @Override
    public Observable<ResponseListMotivos> getListMotivoOnLine() {
        return mRemote.getListMotivoOnLine().doOnNext(response -> mLocal.voidListMotivoOffLine(response.getMotivo()));
    }
    //endregion

    //region Supervisores
    @Override
    public Observable<ResponseListSupervisores> getlistSupervisoresOnLine(HashMap<String, String> body) {
        return mRemote.getlistSupervisoresOnLine(body).doOnNext(response -> mLocal.voidListSupervisoresOffLine(response.getSupervisor()));
    }
    //endregion

    //region Configuracion
    @Override
    public Observable<ResponseConfiguracion> getConfiguracionOnLine() {
        return mRemote.getConfiguracionOnLine().doOnNext(response -> mLocal.voidConfiguracionOffLine(response.getConfiguracion()));
    }
    //endregion

    //region Marcaciones
    @Override
    public Observable<Message> setMarcOnLine(Marcacion marcacion) {
        return mRemote.setMarcOnLine(marcacion);
    }

    @Override
    public Maybe<ResponseListLastMarcaciones> getListLastMarcOnLine(RequestListLastMarc requestListLastMarc) {
        return mRemote.getListLastMarcOnLine(requestListLastMarc);
    }

    @Override
    public Maybe<ResponseListMarcaciones> getListMarcOnLine(RequestListMarc requestListMarc) {
        return mRemote.getListMarcOnLine(requestListMarc);
    }

    @Override
    public Maybe<ResponseListMarcaciones> getListMarcPersOnLine(RequestListMarcPers requestListMarcPers) {
        return mRemote.getListMarcPersOnLine(requestListMarcPers);
    }

    @Override
    public Maybe<ResponseListGraphicsMarcaciones> getListMarcGraphicsOnLine(RequestListMarcGraphics requestListMarcGraphics) {
        return mRemote.getListMarcGraphicsOnLine(requestListMarcGraphics);
    }

    @Override
    public Maybe<Message> deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc) {
        return mRemote.deleteLastMarcOnLine(requestDeleteLastMarc);
    }

    @Override
    public Maybe<ResponseCargaMarcacion> cargaSetMarcOnLine(List<Marcacion> listMarcacion) {
        return mRemote.cargaSetMarcOnLine(listMarcacion);
    }

    //endregion

    //region Solicitud Permiso
    @Override
    public Observable<Message> setSolicPermOnLine(SolicitudPermiso solicitudPermiso) {
        return mRemote.setSolicPermOnLine(solicitudPermiso);
    }

    @Override
    public Maybe<ResponseListSolicPerm> getListSolicPermOnLine(RequestListSolicPers requestListSolicPers) {
        return mRemote.getListSolicPermOnLine(requestListSolicPers);
    }

    @Override
    public Maybe<ResponseListSolicPerm> getListAprobSolicPermOnLine(RequestListAprobSolicPers requestListAprobSolicPers) {
        return mRemote.getListAprobSolicPermOnLine(requestListAprobSolicPers);
    }

    @Override
    public Maybe<Message> getDeletePermOnLine(RequestDeleteSolicitud requestDeleteSolicitud) {
        return mRemote.getDeletePermOnLine(requestDeleteSolicitud);
    }

    @Override
    public Maybe<Message> getAprobPermOnLine(List<RequestAprobSolicitud> requestAprobSolicitud) {
        return mRemote.getAprobPermOnLine(requestAprobSolicitud);
    }

    @Override
    public Maybe<Message> getEditPermOnLine(SolicitudPermiso solicitudPermiso) {
        return mRemote.getEditPermOnLine(solicitudPermiso);
    }

    @Override
    public Maybe<ResponseCargaPermisos> cargaSetSolicPermOnLine(List<SolicitudPermiso> ListSolicitudPermiso) {
        return mRemote.cargaSetSolicPermOnLine(ListSolicitudPermiso);
    }
    //endregion

    //endregion
}
