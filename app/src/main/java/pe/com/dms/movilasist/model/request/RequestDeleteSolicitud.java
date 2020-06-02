package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestDeleteSolicitud implements Serializable {
    private int intIdmSolicitud;
    private String vchCodPersonal;
    private int IntIntegracionVAWEB;

    public RequestDeleteSolicitud() {
    }

    public RequestDeleteSolicitud(int intIdmSolicitud, String vchCodPersonal, int intIntegracionVAWEB) {
        this.intIdmSolicitud = intIdmSolicitud;
        this.vchCodPersonal = vchCodPersonal;
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    public int getIntIdmSolicitud() {
        return intIdmSolicitud;
    }

    public void setIntIdmSolicitud(int intIdmSolicitud) {
        this.intIdmSolicitud = intIdmSolicitud;
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
        return "RequestDeleteSolicitud{" +
                "intIdmSolicitud=" + intIdmSolicitud +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                '}';
    }
}
