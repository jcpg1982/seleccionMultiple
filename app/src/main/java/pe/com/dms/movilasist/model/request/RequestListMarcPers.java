package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestListMarcPers implements Serializable {
    private String vchCodPersonal;
    private String vchNombreApe;
    private String dtmFechaInicio;
    private String dtmFechaFin;
    private int intIntegracionVAWEB;
    private int intMostrarMarca;
    private int intPaginaNum;
    private int IntPaginaSize;

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public String getVchNombreApe() {
        return vchNombreApe;
    }

    public void setVchNombreApe(String vchNombreApe) {
        this.vchNombreApe = vchNombreApe;
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

    public int getIntPaginaNum() {
        return intPaginaNum;
    }

    public void setIntPaginaNum(int intPaginaNum) {
        this.intPaginaNum = intPaginaNum;
    }

    public int getIntPaginaSize() {
        return IntPaginaSize;
    }

    public void setIntPaginaSize(int intPaginaSize) {
        IntPaginaSize = intPaginaSize;
    }

    @Override
    public String toString() {
        return "RequestListMarcPers{" +
                "vchCodPersonal='" + vchCodPersonal + '\'' +
                ", vchNombreApe='" + vchNombreApe + '\'' +
                ", dtmFechaInicio='" + dtmFechaInicio + '\'' +
                ", dtmFechaFin='" + dtmFechaFin + '\'' +
                ", intIntegracionVAWEB=" + intIntegracionVAWEB +
                ", intMostrarMarca=" + intMostrarMarca +
                ", intPaginaNum=" + intPaginaNum +
                ", IntPaginaSize=" + IntPaginaSize +
                '}';
    }
}
