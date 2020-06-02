package pe.com.dms.movilasist.model.response;

import java.io.Serializable;
import java.util.List;

import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.model.Message;

public class ResponseListConcepto implements Serializable {
    private List<Conceptos> Concepto;
    private Message Mensaje;

    public List<Conceptos> getConcepto() {
        return Concepto;
    }

    public void setConcepto(List<Conceptos> concepto) {
        Concepto = concepto;
    }

    public Message getMensaje() {
        return Mensaje;
    }

    public void setMensaje(Message mensaje) {
        Mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "RequestListConceptos{" +
                "Concepto=" + Concepto +
                ", Mensaje=" + Mensaje +
                '}';
    }
}
