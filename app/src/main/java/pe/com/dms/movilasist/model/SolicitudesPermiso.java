package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class SolicitudesPermiso implements Serializable {
    private int intIdmSolicitud;
    private String vchCodPersonal;
    private String vchNombreApellidos;
    private String vchCodConcepto;
    private String vchConcepto;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    private String vchHoraInicio;
    private String vchHoraFin;
    private int intPertenenciaInicio;
    private int intPertenenciaFin;
    private String vchObservacion;
    private String vchCodSupervisor;
    private String vchNombreSupervisor;
    private String vchEmailSupervisor;
    private int intEstadoSolicitud;
    private String vchEstadoSolicitud;
    private boolean isChecked;

    public SolicitudesPermiso() {
    }

    public SolicitudesPermiso(String vchCodPersonal, String vchNombreApellidos,
                              String vchCodConcepto, String vchConcepto, String dtmFechaInicio,
                              String dtmFechaFin, String vchHoraInicio, String vchHoraFin,
                              int intPertenenciaInicio, int intPertenenciaFin, String vchObservacion,
                              String vchCodSupervisor, String vchNombreSupervisor,
                              String vchEmailSupervisor, int intEstadoSolicitud,
                              String vchEstadoSolicitud, int intIdmSolicitud) {
        this.vchCodPersonal = vchCodPersonal;
        this.vchNombreApellidos = vchNombreApellidos;
        this.vchCodConcepto = vchCodConcepto;
        this.vchConcepto = vchConcepto;
        this.dtmFechaInicio = dtmFechaInicio;
        this.dtmFechaFin = dtmFechaFin;
        this.vchHoraInicio = vchHoraInicio;
        this.vchHoraFin = vchHoraFin;
        this.intPertenenciaInicio = intPertenenciaInicio;
        this.intPertenenciaFin = intPertenenciaFin;
        this.vchObservacion = vchObservacion;
        this.vchCodSupervisor = vchCodSupervisor;
        this.vchNombreSupervisor = vchNombreSupervisor;
        this.vchEmailSupervisor = vchEmailSupervisor;
        this.intEstadoSolicitud = intEstadoSolicitud;
        this.vchEstadoSolicitud = vchEstadoSolicitud;
        this.intIdmSolicitud = intIdmSolicitud;
    }

    public int getIntIdmSolicitud() {
        return intIdmSolicitud;
    }

    public void setIntIdmSolicitud(int intIdmSolicitud) {
        this.intIdmSolicitud = intIdmSolicitud;
    }

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public String getVchNombreApellidos() {
        return vchNombreApellidos;
    }

    public void setVchNombreApellidos(String vchNombreApellidos) {
        this.vchNombreApellidos = vchNombreApellidos;
    }

    public String getVchCodConcepto() {
        return vchCodConcepto;
    }

    public void setVchCodConcepto(String vchCodConcepto) {
        this.vchCodConcepto = vchCodConcepto;
    }

    public String getVchConcepto() {
        return vchConcepto;
    }

    public void setVchConcepto(String vchConcepto) {
        this.vchConcepto = vchConcepto;
    }

    public String getDtmFechaInicio() {
        return dtmFechaInicio;
    }

    public void setDtmFechaInicio(String dtmFechaInicio) {
        this.dtmFechaInicio = dtmFechaInicio;
    }

    public String getDtmFechaFin() {
        return dtmFechaFin;
    }

    public void setDtmFechaFin(String dtmFechaFin) {
        this.dtmFechaFin = dtmFechaFin;
    }

    public String getVchHoraInicio() {
        return vchHoraInicio;
    }

    public void setVchHoraInicio(String vchHoraInicio) {
        this.vchHoraInicio = vchHoraInicio;
    }

    public String getVchHoraFin() {
        return vchHoraFin;
    }

    public void setVchHoraFin(String vchHoraFin) {
        this.vchHoraFin = vchHoraFin;
    }

    public int getIntPertenenciaInicio() {
        return intPertenenciaInicio;
    }

    public void setIntPertenenciaInicio(int intPertenenciaInicio) {
        this.intPertenenciaInicio = intPertenenciaInicio;
    }

    public int getIntPertenenciaFin() {
        return intPertenenciaFin;
    }

    public void setIntPertenenciaFin(int intPertenenciaFin) {
        this.intPertenenciaFin = intPertenenciaFin;
    }

    public String getVchObservacion() {
        return vchObservacion;
    }

    public void setVchObservacion(String vchObservacion) {
        this.vchObservacion = vchObservacion;
    }

    public String getVchCodSupervisor() {
        return vchCodSupervisor;
    }

    public void setVchCodSupervisor(String vchCodSupervisor) {
        this.vchCodSupervisor = vchCodSupervisor;
    }

    public String getVchNombreSupervisor() {
        return vchNombreSupervisor;
    }

    public void setVchNombreSupervisor(String vchNombreSupervisor) {
        this.vchNombreSupervisor = vchNombreSupervisor;
    }

    public String getVchEmailSupervisor() {
        return vchEmailSupervisor;
    }

    public void setVchEmailSupervisor(String vchEmailSupervisor) {
        this.vchEmailSupervisor = vchEmailSupervisor;
    }

    public int getIntEstadoSolicitud() {
        return intEstadoSolicitud;
    }

    public void setIntEstadoSolicitud(int intEstadoSolicitud) {
        this.intEstadoSolicitud = intEstadoSolicitud;
    }

    public String getVchEstadoSolicitud() {
        return vchEstadoSolicitud;
    }

    public void setVchEstadoSolicitud(String vchEstadoSolicitud) {
        this.vchEstadoSolicitud = vchEstadoSolicitud;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "SolicitudesPermiso{" +
                "intIdmSolicitud=" + intIdmSolicitud +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", vchNombreApellidos='" + vchNombreApellidos + '\'' +
                ", vchCodConcepto='" + vchCodConcepto + '\'' +
                ", vchConcepto='" + vchConcepto + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", vchHoraInicio='" + vchHoraInicio + '\'' +
                ", vchHoraFin='" + vchHoraFin + '\'' +
                ", intPertenenciaInicio=" + intPertenenciaInicio +
                ", intPertenenciaFin=" + intPertenenciaFin +
                ", vchObservacion='" + vchObservacion + '\'' +
                ", vchCodSupervisor='" + vchCodSupervisor + '\'' +
                ", vchNombreSupervisor='" + vchNombreSupervisor + '\'' +
                ", vchEmailSupervisor='" + vchEmailSupervisor + '\'' +
                ", intEstadoSolicitud=" + intEstadoSolicitud +
                ", vchEstadoSolicitud='" + vchEstadoSolicitud + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
