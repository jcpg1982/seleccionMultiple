package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.Paginacion;

public class ResponseListLastMarcaciones implements Serializable {
    private List<Marcaciones> Marca;
    private Message Mensaje;

    public List<Marcaciones> getMarca() {
        return Marca;
    }

    public void setMarca(List<Marcaciones> marca) {
        Marca = marca;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResponseListLastMarcaciones{" +
                "Marca=" + Marca +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
