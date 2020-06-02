package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.model.ListResultadoMarcacion;
import pe.com.dms.movilasist.model.Message;

public class ResponseCargaMarcacion implements Serializable {
    private String Mensaje;
    private List<ListResultadoMarcacion> lstResultado;

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public List<ListResultadoMarcacion> getLstResultado() {
        return lstResultado;
    }

    public void setLstResultado(List<ListResultadoMarcacion> lstResultado) {
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
