package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.model.ListResultadoPermisos;

public class ResponseCargaPermisos implements Serializable {
    private String Mensaje;
    private List<ListResultadoPermisos> lstResultado;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public List<ListResultadoPermisos> getLstResultado() {
        return lstResultado;
    }

    public void setLstResultado(List<ListResultadoPermisos> lstResultado) {
        this.lstResultado = lstResultado;
    }

    @Override
    public String toString() {
        return "ResponseCargaMarcacion{" +
                "Mensaje='" + Mensaje + '\'' +
                ", lstResultado=" + lstResultado +
                '}';
    }
}
