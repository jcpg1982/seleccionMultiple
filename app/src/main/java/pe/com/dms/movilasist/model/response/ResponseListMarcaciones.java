package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.Paginacion;

public class ResponseListMarcaciones implements Serializable {
    private List<Marcaciones> Marca;
    private Message Mensaje;
    private Paginacion Paginacion;

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

    public pe.com.dms.movilasist.model.Paginacion getPaginacion() {
        return Paginacion;
    }

    public void setPaginacion(pe.com.dms.movilasist.model.Paginacion paginacion) {
        Paginacion = paginacion;
    }

    @Override
    public String toString() {
        return "ResponseListMarcaciones{" +
                "Marca=" + Marca +
                ", Mensaje=" + Mensaje +
                ", Paginacion=" + Paginacion +
                '}';
    }
}
