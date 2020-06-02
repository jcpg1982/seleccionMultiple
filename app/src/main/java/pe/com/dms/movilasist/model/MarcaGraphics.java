package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class MarcaGraphics implements Serializable {
    private String vchValor;
    private int intCantidad;

    public String getVchValor() {
        return vchValor;
    }

    public void setVchValor(String vchValor) {
        this.vchValor = vchValor;
    }

    public int getIntCantidad() {
        return intCantidad;
    }

    public void setIntCantidad(int intCantidad) {
        this.intCantidad = intCantidad;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "vchValor='" + vchValor + '\'' +
                ", intCantidad=" + intCantidad +
                '}';
    }
}
