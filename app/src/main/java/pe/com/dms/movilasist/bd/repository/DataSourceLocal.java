package pe.com.dms.movilasist.bd.repository;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.annotacion.StatusServer;
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
import pe.com.dms.movilasist.bd.source.local.DataBase;
import pe.com.dms.movilasist.bd.source.local.dao.ConceptoDao;
import pe.com.dms.movilasist.bd.source.local.dao.ConfiguracionDao;
import pe.com.dms.movilasist.bd.source.local.dao.MarcacionDao;
import pe.com.dms.movilasist.bd.source.local.dao.MotivoDao;
import pe.com.dms.movilasist.bd.source.local.dao.PersonalDao;
import pe.com.dms.movilasist.bd.source.local.dao.SolicitudPermisoDao;
import pe.com.dms.movilasist.bd.source.local.dao.StatusSolicitudDao;
import pe.com.dms.movilasist.bd.source.local.dao.SupervisorDao;
import pe.com.dms.movilasist.bd.source.local.dao.TypeMarcacionDao;
import pe.com.dms.movilasist.bd.source.local.dao.UsuarioDao;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestListMarc;

public class DataSourceLocal implements IDataSource.local {

    private DataBase mDatabase;

    private UsuarioDao daoUsuario;
    private ConfiguracionDao daoConfiguracion;
    private SupervisorDao daoSupervisor;
    private MotivoDao daoMotivo;
    private PersonalDao daoPersonal;
    private ConceptoDao daoConcepto;
    private MarcacionDao daoMarcacion;
    private SolicitudPermisoDao daoSolicitudPermiso;
    private TypeMarcacionDao daoTypeMarcacion;
    private StatusSolicitudDao daoStatusSolicitud;

    private final ThreadPoolExecutor threadPoolExecute = new ThreadPoolExecutor(4,
            8, 0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(30));

    @Inject
    public DataSourceLocal(DataBase appDatabase) {
        mDatabase = appDatabase;
        daoUsuario = mDatabase.usuarioDao();
        daoConfiguracion = mDatabase.configuracionDao();
        daoSupervisor = mDatabase.supervisorDao();
        daoMotivo = mDatabase.motivoDao();
        daoPersonal = mDatabase.personalDao();
        daoConcepto = mDatabase.conceptoDao();
        daoMarcacion = mDatabase.marcacionDao();
        daoSolicitudPermiso = mDatabase.solicitudPermisoDao();
        daoTypeMarcacion = mDatabase.typeMarcacionDao();
        daoStatusSolicitud = mDatabase.statusSolicitudDao();
    }

    //region Usuario

    @Override
    public void voidUsuarioOffLine(Usuario user) {
        threadPoolExecute.execute(() -> {
            daoUsuario.deleteAll();
            daoUsuario.voidUsuarioOffLine(user);
        });
    }

    @Override
    public void voidListUsuarioOffLine(List<Usuario> listUser) {
        threadPoolExecute.execute(() -> {
            daoUsuario.deleteAll();
            daoUsuario.voidListUsuarioOffLine(listUser);
        });
    }

    @Override
    public Single<Long> setUsuarioOffLine(Usuario user) {
        return Single.create(emitter -> {
            try {
                daoUsuario.deleteAll();
                daoUsuario.setUsuarioOffLine(user);
                emitter.onSuccess(1L);
            } catch (Exception e) {
                emitter.onError(new Throwable("No se inserto el perfil actual"));
            }
        });
    }

    @Override
    public Maybe<Long[]> setListUsuarioOffLine(List<Usuario> listUser) {
        threadPoolExecute.execute(() -> {
            daoUsuario.deleteAll();
        });
        return daoUsuario.setListUsuarioOffLine(listUser);
    }


    @Override
    public Maybe<Usuario> validarUsuario(String user, String password) {
        return daoUsuario.validarUsuario(user, password);
    }

    @Override
    public Maybe<Usuario> obtainUsuarioWithDocument(String document) {
        return daoUsuario.obtainUsuarioWithDocument(document);
    }

    @Override
    public Maybe<Usuario> obtainUser() {
        return daoUsuario.obtainUser();
    }
    //endregion

    //region Configuracion
    @Override
    public void voidConfiguracionOffLine(List<Configuracion> configuracion) {
        threadPoolExecute.execute(() -> {
            daoConfiguracion.deleteAllOffLine();
            daoConfiguracion.voidListConfiguracionOffLine(configuracion);
        });
    }

    @Override
    public Single<Long> setConfiguracionOffLine(Configuracion configuracion) {
        return Single.create(emitter -> {
            try {
                daoConfiguracion.deleteAllOffLine();
                daoConfiguracion.voidConfiguracionOffLine(configuracion);
                emitter.onSuccess(1L);
            } catch (Exception e) {
                emitter.onError(new Throwable("No se inserto el perfil actual"));
            }
        });
    }

    @Override
    public Single<Long[]> setListConfiguracionOffLine(List<Configuracion> ListConfig) {
        return daoConfiguracion.setListConfiguracionOffLine(ListConfig);
    }

    @Override
    public Maybe<Configuracion> getConfiguracionOffLine() {
        return daoConfiguracion.getConfiguracionOffLine();
    }
    //endregion

    //region Supervisor
    @Override
    public void voidListSupervisoresOffLine(List<Supervisor> listSuper) {
        threadPoolExecute.execute(() -> {
            daoSupervisor.deleteAll();
            daoSupervisor.voidListSupervisorOffLine(listSuper);
        });
    }

    @Override
    public Maybe<List<Supervisor>> getListSupervisoresOffLine() {
        return daoSupervisor.getListSupervisoresOffLine();
    }
    //endregion

    //region Motivo
    @Override
    public void voidListMotivoOffLine(List<Motivo> listMotivo) {
        threadPoolExecute.execute(() -> {
            daoMotivo.deleteAll();
            daoMotivo.voidListMotivoOffLine(listMotivo);
        });
    }

    @Override
    public Maybe<List<Motivo>> getListMotivoOffLine() {
        return daoMotivo.getListMotivoOffLine();
    }
    //endregion

    //region Personal
    @Override
    public void voidListPersonalOffLine(List<Personal> listPersonal) {
        threadPoolExecute.execute(() -> {
            daoPersonal.deleteAll();
            daoPersonal.voidListPersonalOffLine(listPersonal);
        });
    }
    //endregion

    //region Conceptos
    @Override
    public void voidListConceptosOffLine(List<Conceptos> listConceptos) {
        threadPoolExecute.execute(() -> {
            daoConcepto.deleteAll();
            daoConcepto.voidListConceptosOffLine(listConceptos);
        });
    }

    @Override
    public Maybe<List<Conceptos>> getListConceptosOffLine() {
        return daoConcepto.getListConceptosOffLine();
    }
    //endregion

    //region Marcacion
    @Override
    public Single<Long> sendMarcOffLine(Marcacion marcacion) {
        return daoMarcacion.insert(marcacion);
    }

    @Override
    public Maybe<List<Marcaciones>> setListLastMarcacionesOffLine() {
        return daoMarcacion.setListLastMarcaciones();
    }

    @Override
    public Maybe<List<Marcaciones>> setListMarcacionesOffLine() {
        return daoMarcacion.setListMarcaciones();
    }

    @Override
    public Single<Long> deleteLastMarcOffLine(Marcacion marcacion) {
        return Single.create(emitter -> {
            try {
                daoMarcacion.deleteLastMarcOffLine(marcacion.getDtmFechaMarca());
                emitter.onSuccess(1L);
            } catch (Exception e) {
                emitter.onError(new Throwable("No se inserto el perfil actual"));
            }
        });
    }

    @Override
    public Maybe<List<Marcaciones>> obtainListMarcWithDate(RequestListMarc requestListMarc) {
        return daoMarcacion.obtainListMarcWithDate(requestListMarc.getDtmFechaInicio(),
                requestListMarc.getDtmFechaFin(), requestListMarc.getVchCodPersonal(),
                requestListMarc.getIntIntegracionVAWEB());
    }

    @Override
    public Maybe<List<Marcacion>> getAllMarcacionesOffLine(String statusServer) {
        return daoMarcacion.allMarcaciones(statusServer);
    }

    @Override
    public Single<Integer>  deleteMarcacionesEnviadas(List<Integer> listCodEnviados) {
        return daoMarcacion.deleteMarcacionesEnviadas(listCodEnviados);
    }
    //endregion

    //region Solicitud Permiso
    @Override
    public Single<Long> sendSolicPermOffLine(SolicitudPermiso solicitudPermiso) {
        return daoSolicitudPermiso.insert(solicitudPermiso);
    }

    @Override
    public Maybe<List<SolicitudPermiso>> getAllSolicitudPermisoOffLine(@StatusServer String statusServer) {
        return daoSolicitudPermiso.allSolicitudPermiso(statusServer);
    }

    @Override
    public Single<Integer>  deletePermisosEnviados(List<Integer> listCodEnviados) {
        return daoSolicitudPermiso.deletePermisosEnviados(listCodEnviados);
    }
    //endregion

    //region tipo de marcacion
    @Override
    public Maybe<Long[]> setListTypeMarcacionOffLine(List<TypeMarcacion> listTypeMarcacion) {
        return daoTypeMarcacion.insertAll(listTypeMarcacion);
    }

    @Override
    public Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine() {
        return daoTypeMarcacion.getListTypeMarcacionOffLine();
    }
    //endregion

    //region estado de solicitud
    @Override
    public Maybe<Long[]> setListStatusSolicitudOffLine(List<StatusSolicitud> listStatusSolicitud) {
        return daoStatusSolicitud.insertAll(listStatusSolicitud);
    }

    @Override
    public Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine() {
        return daoStatusSolicitud.getListStatusSolicitudOffLine();
    }
    //endregion
}
