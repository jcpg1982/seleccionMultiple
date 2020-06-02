package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.Message;

public class ResponseConfiguracion implements Serializable {

    private List<Configuracion> Configuracion;
    private Message Mensaje;

    public List<pe.com.dms.movilasist.bd.entity.Configuracion> getConfiguracion() {
        return Configuracion;
    }

    public void setConfiguracion(List<pe.com.dms.movilasist.bd.entity.Configuracion> configuracion) {
        Configuracion = configuracion;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResponseConfiguracion{" +
                "Configuracion=" + Configuracion +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
