package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.model.Message;

public class ResponseListMotivos implements Serializable {
    private List<Motivo> Motivo;
    private Message Mensaje;

    public List<Motivo> getMotivo() {
        return Motivo;
    }

    public void setMotivo(List<Motivo> motivo) {
        Motivo = motivo;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResponseListMotivos{" +
                "Motivo=" + Motivo +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
