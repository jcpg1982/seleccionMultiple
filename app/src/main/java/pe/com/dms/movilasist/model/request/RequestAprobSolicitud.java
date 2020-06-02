package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestAprobSolicitud implements Serializable {
    private int intIdmSolicitud;
    private String vchCodPersonal;
    private int intEstadoSolicitud;
    private String vchObservacion;
    private String vchCodSupervisor;
    private String vchEmailSupervisor;
    private int intTipoUso;
    private int IntIntegracionVAWEB;

    public RequestAprobSolicitud() {
    }

    public RequestAprobSolicitud(int intIdmSolicitud, String vchCodPersonal,
                                 int intEstadoSolicitud, String vchObservacion,
                                 String vchCodSupervisor, String vchEmailSupervisor,
                                 int intTipoUso, int intIntegracionVAWEB) {
        this.intIdmSolicitud = intIdmSolicitud;
        this.vchCodPersonal = vchCodPersonal;
        this.intEstadoSolicitud = intEstadoSolicitud;
        this.vchObservacion = vchObservacion;
        this.vchCodSupervisor = vchCodSupervisor;
        this.vchEmailSupervisor = vchEmailSupervisor;
        this.intTipoUso = intTipoUso;
        IntIntegracionVAWEB = intIntegracionVAWEB;
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

    public int getIntEstadoSolicitud() {
        return intEstadoSolicitud;
    }

    public void setIntEstadoSolicitud(int intEstadoSolicitud) {
        this.intEstadoSolicitud = intEstadoSolicitud;
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

    @Override
    public String toString() {
        return "RequestAprobSolicitud{" +
                "intIdmSolicitud=" + intIdmSolicitud +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", intEstadoSolicitud=" + intEstadoSolicitud +
                ", vchObservacion='" + vchObservacion + '\'' +
                ", vchCodSupervisor='" + vchCodSupervisor + '\'' +
                ", vchEmailSupervisor='" + vchEmailSupervisor + '\'' +
                ", intTipoUso=" + intTipoUso +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                '}';
    }
}
