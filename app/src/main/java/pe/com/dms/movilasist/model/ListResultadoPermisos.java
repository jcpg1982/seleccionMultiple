package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class ListResultadoPermisos implements Serializable {

    private int intIdmSolicitudInt;
    private int intValor;
    private String vchMensaje;

    public int getIntIdmSolicitudInt() {
        return intIdmSolicitudInt;
    }

    public void setIntIdmSolicitudInt(int intIdmSolicitudInt) {
        this.intIdmSolicitudInt = intIdmSolicitudInt;
    }

    public int getIntValor() {
        return intValor;
    }

    public void setIntValor(int intValor) {
        this.intValor = intValor;
    }

    public String getVchMensaje() {
        return vchMensaje;
    }

    public void setVchMensaje(String vchMensaje) {
        this.vchMensaje = vchMensaje;
    }

    @Override
    public String toString() {
        return "ListResultadoPermisos{" +
                "intIdmSolicitudInt=" + intIdmSolicitudInt +
                ", intValor=" + intValor +
                ", vchMensaje='" + vchMensaje + '\'' +
                '}';
    }
}
