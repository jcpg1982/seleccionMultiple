package pe.com.dms.movilasist.model.response;

import java.io.Serializable;

import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.model.Message;

public class ResponseInfoUser implements Serializable {

    private Usuario Usuario;
    private Message Mensaje;

    public pe.com.dms.movilasist.bd.entity.Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(pe.com.dms.movilasist.bd.entity.Usuario usuario) {
        Usuario = usuario;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "Usuario=" + Usuario +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
