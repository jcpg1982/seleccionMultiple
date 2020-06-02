package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Supervisor")
public class Supervisor implements Serializable {

    @PrimaryKey()
    @NonNull
    private String vchCodigoPersonal;
    private String vchNombreApellido;
    private String vchCorreo;

    public Supervisor(@NonNull String vchCodigoPersonal, String vchNombreApellido) {
        this.vchCodigoPersonal = vchCodigoPersonal;
        this.vchNombreApellido = vchNombreApellido;
    }

    @NonNull
    public String getVchCodigoPersonal() {
        return vchCodigoPersonal;
    }

    public void setVchCodigoPersonal(@NonNull String vchCodigoPersonal) {
        this.vchCodigoPersonal = vchCodigoPersonal;
    }

    public String getVchNombreApellido() {
        return vchNombreApellido;
    }

    public void setVchNombreApellido(String vchNombreApellido) {
        this.vchNombreApellido = vchNombreApellido;
    }

    public String getVchCorreo() {
        return vchCorreo;
    }

    public void setVchCorreo(String vchCorreo) {
        this.vchCorreo = vchCorreo;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "vchCodigoPersonal='" + vchCodigoPersonal + '\'' +
                ", vchNombreApellido='" + vchNombreApellido + '\'' +
                ", vchCorreo='" + vchCorreo + '\'' +
                '}';
    }
}
