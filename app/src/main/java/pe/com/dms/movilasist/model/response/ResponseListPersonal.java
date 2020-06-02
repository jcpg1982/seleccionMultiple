package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.model.Message;

public class ResponseListPersonal implements Serializable {
    private List<Personal> Personal;
    private Message Mensaje;

    public List<Personal> getPersonal() {
        return Personal;
    }

    public void setPersonal(List<Personal> personal) {
        Personal = personal;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "RequestListPersonal{" +
                "Personal=" + Personal +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
