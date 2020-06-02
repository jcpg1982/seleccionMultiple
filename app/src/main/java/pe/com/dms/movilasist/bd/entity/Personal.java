package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Personal")
public class Personal implements Serializable {

    @PrimaryKey()
    @NonNull
    private String vchCodigoPersonal;
    private String vchNombre;
    private String vchApellidos;
    private String vchDocumento;
    private String vchCorreo;

    @NonNull
    public String getVchCodigoPersonal() {
        return vchCodigoPersonal;
    }

    public void setVchCodigoPersonal(@NonNull String vchCodigoPersonal) {
        this.vchCodigoPersonal = vchCodigoPersonal;
    }

    public String getVchNombre() {
        return vchNombre;
    }

    public void setVchNombre(String vchNombre) {
        this.vchNombre = vchNombre;
    }

    public String getVchApellidos() {
        return vchApellidos;
    }

    public void setVchApellidos(String vchApellidos) {
        this.vchApellidos = vchApellidos;
    }

    public String getVchDocumento() {
        return vchDocumento;
    }

    public void setVchDocumento(String vchDocumento) {
        this.vchDocumento = vchDocumento;
    }

    public String getVchCorreo() {
        return vchCorreo;
    }

    public void setVchCorreo(String vchCorreo) {
        this.vchCorreo = vchCorreo;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "vchCodigoPersonal='" + vchCodigoPersonal + '\'' +
                ", vchNombre='" + vchNombre + '\'' +
                ", vchApellidos='" + vchApellidos + '\'' +
                ", vchDocumento='" + vchDocumento + '\'' +
                ", vchCorreo='" + vchCorreo + '\'' +
                '}';
    }
}
