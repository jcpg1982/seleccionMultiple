package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestListMarc implements Serializable {
    private String vchCodPersonal;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    private int IntIntegracionVAWEB;
    private int intMostrarMarca;
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

    public int getIntIntegracionVAWEB() {
        return IntIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    public int getIntMostrarMarca() {
        return intMostrarMarca;
    }

    public void setIntMostrarMarca(int intMostrarMarca) {
        this.intMostrarMarca = intMostrarMarca;
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
        return "RequestListMarc{" +
                "vchCodPersonal='" + vchCodPersonal + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                ", intMostrarMarca=" + intMostrarMarca +
                ", intPaginaNum=" + intPaginaNum +
                ", intPaginaSize=" + intPaginaSize +
                '}';
    }
}
