package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.Message;
import pe.com.dms.movilasist.model.Paginacion;
import pe.com.dms.movilasist.model.SolicitudesPermiso;

public class ResponseListSolicPerm implements Serializable {
    private List<SolicitudesPermiso> Solicitud;
    private Message Mensaje;
    private Paginacion Paginacion;

    public List<SolicitudesPermiso> getSolicitud() {
        return Solicitud;
    }

    public void setSolicitud(List<SolicitudesPermiso> solicitud) {
        Solicitud = solicitud;
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
        return "ResponseListSolicPerm{" +
                "Solicitud=" + Solicitud +
                ", Mensaje=" + Mensaje +
                ", Paginacion=" + Paginacion +
                '}';
    }
}
