package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.model.Message;

public class ResponseListSupervisores implements Serializable {
    private List<Supervisor> Supervisor;
    private Message Mensaje;

    public List<pe.com.dms.movilasist.bd.entity.Supervisor> getSupervisor() {
        return Supervisor;
    }

    public void setSupervisor(List<pe.com.dms.movilasist.bd.entity.Supervisor> supervisor) {
        Supervisor = supervisor;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResponseListSupervisores{" +
                "Supervisor=" + Supervisor +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
