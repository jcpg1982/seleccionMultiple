package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestListSolicPers implements Serializable {

    private String vchCodPersonal;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    private int IntTipoUso;
    private int IntIntegracionVAWEB;
    private int intMostrarPermisos;
    private int intEstadoSolicitud;
    private int intPaginaNum;
    private int intPaginaSize;

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
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

    public int getIntTipoUso() {
        return IntTipoUso;
    }

    public void setIntTipoUso(int intTipoUso) {
        IntTipoUso = intTipoUso;
    }

    public int getIntIntegracionVAWEB() {
        return IntIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    public int getIntMostrarPermisos() {
        return intMostrarPermisos;
    }

    public void setIntMostrarPermisos(int intMostrarPermisos) {
        this.intMostrarPermisos = intMostrarPermisos;
    }

    public int getIntEstadoSolicitud() {
        return intEstadoSolicitud;
    }

    public void setIntEstadoSolicitud(int intEstadoSolicitud) {
        this.intEstadoSolicitud = intEstadoSolicitud;
    }

    public int getIntPaginaNum() {
        return intPaginaNum;
    }

    public void setIntPaginaNum(int intPaginaNum) {
        this.intPaginaNum = intPaginaNum;
    }

    public int getIntPaginaSize() {
        return intPaginaSize;
    }

    public void setIntPaginaSize(int intPaginaSize) {
        this.intPaginaSize = intPaginaSize;
    }

    @Override
    public String toString() {
        return "RequestListSolicPers{" +
                "vchCodPersonal='" + vchCodPersonal + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", IntTipoUso=" + IntTipoUso +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                ", intMostrarPermisos=" + intMostrarPermisos +
                ", intEstadoSolicitud=" + intEstadoSolicitud +
                ", intPaginaNum=" + intPaginaNum +
                ", intPaginaSize=" + intPaginaSize +
                '}';
    }
}
