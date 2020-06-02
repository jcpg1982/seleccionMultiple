package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Motivo")
public class Motivo implements Serializable {

    @PrimaryKey()
    @NonNull
    private String vchCodMotivo;
    private String vchMotivo;

    public Motivo(@NonNull String vchCodMotivo, String vchMotivo) {
        this.vchCodMotivo = vchCodMotivo;
        this.vchMotivo = vchMotivo;
    }

    public String getVchCodMotivo() {
        return vchCodMotivo;
    }

    public void setVchCodMotivo(String vchCodMotivo) {
        this.vchCodMotivo = vchCodMotivo;
    }

    public String getVchMotivo() {
        return vchMotivo;
    }

    public void setVchMotivo(String vchMotivo) {
        this.vchMotivo = vchMotivo;
    }

    @Override
    public String toString() {
        return "Motivo{" +
                "vchCodMotivo='" + vchCodMotivo + '\'' +
                ", vchMotivo='" + vchMotivo + '\'' +
                '}';
    }
}
