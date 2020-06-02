package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

import pe.com.dms.movilasist.annotacion.TypeGraphics;

public class RequestListMarcGraphics implements Serializable {
    private String vchCodPersonal;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    @TypeGraphics
    private int intTipo;
    private int intIntegracionVAWEB;
    private int intMostrarMarca;

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

    public int getIntTipo() {
        return intTipo;
    }

    public void setIntTipo(int intTipo) {
        this.intTipo = intTipo;
    }

    public int getIntIntegracionVAWEB() {
        return intIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        this.intIntegracionVAWEB = intIntegracionVAWEB;
    }

    public int getIntMostrarMarca() {
        return intMostrarMarca;
    }

    public void setIntMostrarMarca(int intMostrarMarca) {
        this.intMostrarMarca = intMostrarMarca;
    }

    @Override
    public String toString() {
        return "RequestListMarcGraphics{" +
                "vchCodPersonal='" + vchCodPersonal + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", intTipo=" + intTipo +
                ", intIntegracionVAWEB=" + intIntegracionVAWEB +
                ", intMostrarMarca=" + intMostrarMarca +
                '}';
    }
}
