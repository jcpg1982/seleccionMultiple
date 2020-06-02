package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class ListResultadoMarcacion implements Serializable {

    private int intIdmMarcaInt;
    private int intValor;
    private String vchMensaje;

    public int getIntIdmMarcaInt() {
        return intIdmMarcaInt;
    }

    public void setIntIdmMarcaInt(int intIdmMarcaInt) {
        this.intIdmMarcaInt = intIdmMarcaInt;
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
        return "ListResultadoMarcacion{" +
                "intIdmMarcaInt=" + intIdmMarcaInt +
                ", intValor=" + intValor +
                ", vchMensaje='" + vchMensaje + '\'' +
                '}';
    }
}
