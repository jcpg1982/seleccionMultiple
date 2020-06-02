package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class Message implements Serializable {
    private int intValor;
    private String vchMensaje;

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
        return "Response{" +
                "intValor=" + intValor +
                ", vchMensaje='" + vchMensaje + '\'' +
                '}';
    }
}
