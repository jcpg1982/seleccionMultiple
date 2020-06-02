package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import pe.com.dms.movilasist.annotacion.StatusServer;

@Entity(tableName = "SolicitudPermiso")
public class SolicitudPermiso implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int intIdmSolicitudInt;
    private String vchCodPersonal;
    private String vchCodConcepto;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    private String vchHoraInicio;
    private String vchHoraFin;
    private int intPertenenciaInicio;
    private int intPertenenciaFin;
    private String vchObservacion;
    private String vchCodSupervisor;
    private String vchEmailSupervisor;
    private int intTipoUso;
    private int IntIntegracionVAWEB;
    private int intEstadoSolicitud;
    @StatusServer
    private String statusServer;

    public SolicitudPermiso() {
    }

    public SolicitudPermiso(String vchCodPersonal, String vchCodConcepto, String dtmFechaInicio,
                            String dtmFechaFin, String vchHoraInicio, String vchHoraFin,
                            int intPertenenciaInicio, int intPertenenciaFin, String vchObservacion,
                            String vchCodSupervisor, String vchEmailSupervisor, int intTipoUso,
                            int intIntegracionVAWEB, int intEstadoSolicitud) {
        this.vchCodPersonal = vchCodPersonal;
        this.vchCodConcepto = vchCodConcepto;
        this.dtmFechaInicio = dtmFechaInicio;
        this.dtmFechaFin = dtmFechaFin;
        this.vchHoraInicio = vchHoraInicio;
        this.vchHoraFin = vchHoraFin;
        this.intPertenenciaInicio = intPertenenciaInicio;
        this.intPertenenciaFin = intPertenenciaFin;
        this.vchObservacion = vchObservacion;
        this.vchCodSupervisor = vchCodSupervisor;
        this.vchEmailSupervisor = vchEmailSupervisor;
        this.intTipoUso = intTipoUso;
        IntIntegracionVAWEB = intIntegracionVAWEB;
        this.intEstadoSolicitud = intEstadoSolicitud;
    }

    @NonNull
    public int getIntIdmSolicitudInt() {
        return intIdmSolicitudInt;
    }

    public void setIntIdmSolicitudInt(int intIdmSolicitudInt) {
        this.intIdmSolicitudInt = intIdmSolicitudInt;
    }

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public String getVchCodConcepto() {
        return vchCodConcepto;
    }

    public void setVchCodConcepto(String vchCodConcepto) {
        this.vchCodConcepto = vchCodConcepto;
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

    public String getVchEmailSupervisor() {
        return vchEmailSupervisor;
    }

    public void setVchEmailSupervisor(String vchEmailSupervisor) {
        this.vchEmailSupervisor = vchEmailSupervisor;
    }

    public int getIntTipoUso() {
        return intTipoUso;
    }

    public void setIntTipoUso(int intTipoUso) {
        this.intTipoUso = intTipoUso;
    }

    public int getIntIntegracionVAWEB() {
        return IntIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    public int getIntEstadoSolicitud() {
        return intEstadoSolicitud;
    }

    public void setIntEstadoSolicitud(int intEstadoSolicitud) {
        this.intEstadoSolicitud = intEstadoSolicitud;
    }

    public String getStatusServer() {
        return statusServer;
    }

    public void setStatusServer(String statusServer) {
        this.statusServer = statusServer;
    }

    @Override
    public String toString() {
        return "SolicitudPermiso{" +
                "intIdmSolicitudInt=" + intIdmSolicitudInt +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", vchCodConcepto='" + vchCodConcepto + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", vchHoraInicio='" + vchHoraInicio + '\'' +
                ", vchHoraFin='" + vchHoraFin + '\'' +
                ", intPertenenciaInicio=" + intPertenenciaInicio +
                ", intPertenenciaFin=" + intPertenenciaFin +
                ", vchObservacion='" + vchObservacion + '\'' +
                ", vchCodSupervisor='" + vchCodSupervisor + '\'' +
                ", vchEmailSupervisor='" + vchEmailSupervisor + '\'' +
                ", intTipoUso=" + intTipoUso +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                ", intEstadoSolicitud=" + intEstadoSolicitud +
                ", statusServer='" + statusServer + '\'' +
                '}';
    }
}
