package pe.com.dms.movilasist.bd.source.remote;

public class Urls {

    //region CONEXION A LA BASE DE DATOS
    public static final String TEST_CONNECTION = "WSConextividadBD/";
    //endregion

    //region OBTENER CONFIGURACION
    public static final String CONFIGURACION = "WSObtenerConfiguracion/";
    //endregion

    //region LOGIN
    public static final String LOGIN = "WSValidarLogeo/";
    //endregion

    //region recuperar contaseña
    public static final String RECOVERY_PASSWORD = "WSObtenerConfiguracion/";//TODO no existe el servicio
    //endregion

    //region carga inicial

    //region carga la informacion del usuario logueado
    public static final String INFO_USER = "WSCargaInfoUsuarioLogeo/";
    //endregion

    //region carga la lista del personal
    public static final String LIST_PERSONAL = "WSCargaListaPersonal/";
    //endregion

    //region carga la lista de conceptos
    public static final String LIST_CONCEPTOS = "WSCargaListaConceptoPermisosHE/";
    //endregion

    //region carga la lista de motivos de marcacion
    public static final String LISTA_MOTIVOS_MARCA = "WSCargaListaMotivoMarca/";
    //endregion

    //region lista de supervisores
    public static final String LIST_SUPERVISORES = "WSCargaListaSupervisores/";
    //endregion

    //endregion

    //region marcacion

    //region registrar marcacion
    public static final String SAVE_MARC = "WSRegistrarMarcacion/";
    //endregion

    //region cargar lista Marcaciones
    public static final String LIST_MARC = "WSListarMarcaciones/";
    //endregion

    //region carga lista de marcaciones del personal
    public static final String LISTA_MARC_PERSONAL = "WSListarMarcacionesPersonal/";
    //endregion

    //region cargar lista ultimas Marcaciones
    public static final String LIST_LAST_MARC = "WSListarUltimaMarca/";
    //endregion

    //region cargar lista de marcaciones (GRAFICOS)
    public static final String LIST_MARC_GRAPHICS = "WSListadoCantidadMarca/";
    //endregion

    //region eliminar una marcacion
    public static final String DELETE_MARC = "WSEliminarMarcacion/";
    //endregion


    //region carga en segundo plano de solicitudes
    public static final String CARGA_SAVE_MARC = "WSDescargaMarcaciones/";
    //endregion

    //endregion

    //region solicitud

    //region registrar solicitud permiso
    public static final String REG_SOLICITUD_PERMISO = "WSRegistrarSolicitudes/";
    //endregion

    //region lista de solicitudes de permiso
    public static final String LIST_APROB_SOLICITUD_PERMISO = "WSListadoAprobacionesSolicitudes/";
    //endregion

    //region editar solicitud
    public static final String EDIT_SOLICITUD_PERMISO = "WSActualizarSolicitudes/";
    //endregion

    //region lista de solicitudes de permiso
    public static final String LISTA_SOLICITUD_PERMISO = "WSListadoSolicitudes/";
    //endregion

    //region aprobar solicitud de permiso
    public static final String APROBAR_SOLICITUD = "WSAprobarSolicitud/";
    //endregion

    //region eliminar solicitud de permiso
    public static final String DELETE_SOLICITUD_PERMISO = "WSEliminarSolicitud/";
    //endregion

    //region carga en segundo plano de solicitudes
    public static final String CARGA_REG_SOLICITUD_PERMISO = "WSDescargaSolicitudes/";
    //endregion

    //region aprobacion de solicitudes
    public static final String APROB_SOLICITUD_PERMISO = "WSAprobarSolicitud/";
    //endregion

    //endregion
}
