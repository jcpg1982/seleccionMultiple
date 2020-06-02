package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class Paginacion implements Serializable {
    private int cantPaginas;
    private int cantRegistro;

    public int getCantPaginas() {
        return cantPaginas;
    }

    public void setCantPaginas(int cantPaginas) {
        this.cantPaginas = cantPaginas;
    }

    public int getCantRegistro() {
        return cantRegistro;
    }

    public void setCantRegistro(int cantRegistro) {
        this.cantRegistro = cantRegistro;
    }

    @Override
    public String toString() {
        return "Paginacion{" +
                "cantPaginas=" + cantPaginas +
                ", cantRegistro=" + cantRegistro +
                '}';
    }
}
