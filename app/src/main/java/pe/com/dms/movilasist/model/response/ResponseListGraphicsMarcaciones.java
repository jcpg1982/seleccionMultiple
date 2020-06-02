package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.model.MarcaGraphics;
import pe.com.dms.movilasist.model.Message;

public class ResponseListGraphicsMarcaciones implements Serializable {
    private List<MarcaGraphics> Marca;
    private Message Mensaje;

    public List<MarcaGraphics> getMarca() {
        return Marca;
    }

    public void setMarca(List<MarcaGraphics> marca) {
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
        return "ResponseListGraphicsMarcaciones{" +
                "Marca=" + Marca +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
