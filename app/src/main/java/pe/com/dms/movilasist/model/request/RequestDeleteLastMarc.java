package pe.com.dms.movilasist.model.request;

import java.io.Serializable;

public class RequestDeleteLastMarc implements Serializable {
    private String vchCodPersona;
    private int intIdmMarca;
    private int IntIntegracionVAWEB;

    public RequestDeleteLastMarc(String vchCodPersona, int intIdmMarca, int intIntegracionVAWEB) {
        this.vchCodPersona = vchCodPersona;
        this.intIdmMarca = intIdmMarca;
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    public String getVchCodPersona() {
        return vchCodPersona;
    }

    public void setVchCodPersona(String vchCodPersona) {
        this.vchCodPersona = vchCodPersona;
    }

    public int getIntIdmMarca() {
        return intIdmMarca;
    }

    public void setIntIdmMarca(int intIdmMarca) {
        this.intIdmMarca = intIdmMarca;
    }

    public int getIntIntegracionVAWEB() {
        return IntIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        IntIntegracionVAWEB = intIntegracionVAWEB;
    }

    @Override
    public String toString() {
        return "RequestDeleteLastMarc{" +
                "vchCodPersona='" + vchCodPersona + '\'' +
                ", intIdmMarca=" + intIdmMarca +
                ", IntIntegracionVAWEB=" + IntIntegracionVAWEB +
                '}';
    }
}
