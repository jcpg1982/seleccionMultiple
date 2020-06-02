package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import pe.com.dms.movilasist.annotacion.TypePerfil;
import pe.com.dms.movilasist.util.TextUtils;

@Entity(tableName = "Usuario")
public class Usuario {

    @PrimaryKey()
    @NonNull
    private String vchCodigoPersonal;
    private String vchUsuario;
    private String vchPassword;
    private String vchNombre;
    private String vchApellidos;
    @TypePerfil
    private int intTipoPerfil;
    private String vchPerfil;
    private String vchDocumento;
    private String vchCorreo;

    @NonNull
    public String getVchCodigoPersonal() {
        return vchCodigoPersonal;
    }

    public void setVchCodigoPersonal(@NonNull String vchCodigoPersonal) {
        this.vchCodigoPersonal = vchCodigoPersonal;
    }

    public String getVchUsuario() {
        return vchUsuario;
    }

    public void setVchUsuario(String vchUsuario) {
        this.vchUsuario = vchUsuario;
    }

    public String getVchPassword() {
        return vchPassword;
    }

    public void setVchPassword(String vchPassword) {
        this.vchPassword = vchPassword;
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

    public int getIntTipoPerfil() {
        return intTipoPerfil;
    }

    public void setIntTipoPerfil(int intTipoPerfil) {
        this.intTipoPerfil = intTipoPerfil;
    }

    public String getVchPerfil() {
        return vchPerfil;
    }

    public void setVchPerfil(String vchPerfil) {
        this.vchPerfil = vchPerfil;
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
        return "Usuario{" +
                "vchCodigoPersonal='" + vchCodigoPersonal + '\'' +
                ", vchUsuario='" + vchUsuario + '\'' +
                ", vchPassword='" + vchPassword + '\'' +
                ", vchNombre='" + vchNombre + '\'' +
                ", vchApellidos='" + vchApellidos + '\'' +
                ", intTipoPerfil=" + intTipoPerfil +
                ", vchPerfil='" + vchPerfil + '\'' +
                ", vchDocumento='" + vchDocumento + '\'' +
                ", vchCorreo='" + vchCorreo + '\'' +
                '}';
    }
}
