package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestLogin implements Serializable {
    private String vchDocumento;
    private String vchPassword;

    public String getVchDocumento() {
        return vchDocumento;
    }

    public void setVchDocumento(String vchDocumento) {
        this.vchDocumento = vchDocumento;
    }

    public String getVchPassword() {
        return vchPassword;
    }

    public void setVchPassword(String vchPassword) {
        this.vchPassword = vchPassword;
    }

    @Override
    public String toString() {
        return "RequestLogin{" +
                "vchDocumento='" + vchDocumento + '\'' +
                ", vchPassword='" + vchPassword + '\'' +
                '}';
    }
}
