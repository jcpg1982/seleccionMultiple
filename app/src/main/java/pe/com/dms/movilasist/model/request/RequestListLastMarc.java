package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestListLastMarc implements Serializable {
    private int intLectura;
    private String vchCodPersonal;
    private int IntIntegracionVAWEB;

    public int getIntLectura() {
        return intLectura;
    }

    public void setIntLectura(int intLectura) {
        this.intLectura = intLectura;
    }

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public int getIntIntegracionVAWEB() {
        return IntIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    @Override
    public String toString() {
        return "RequestListLastMarc{" +
                "intLectura=" + intLectura +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                '}';
    }
}
