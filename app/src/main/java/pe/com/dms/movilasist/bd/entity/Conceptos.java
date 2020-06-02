package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Conceptos")
public class Conceptos implements Serializable {

    @PrimaryKey()
    @NonNull
    private String vchCodConcepto;
    private String vchConcepto;
    private int intTipoUnidad;
    private int intTipoUso;

    public Conceptos(@NonNull String vchCodConcepto, String vchConcepto) {
        this.vchCodConcepto = vchCodConcepto;
        this.vchConcepto = vchConcepto;
    }

    public String getVchCodConcepto() {
        return vchCodConcepto;
    }

    public void setVchCodConcepto(String vchCodConcepto) {
        this.vchCodConcepto = vchCodConcepto;
    }

    public String getVchConcepto() {
        return vchConcepto;
    }

    public void setVchConcepto(String vchConcepto) {
        this.vchConcepto = vchConcepto;
    }

    public int getIntTipoUnidad() {
        return intTipoUnidad;
    }

    public void setIntTipoUnidad(int intTipoUnidad) {
        this.intTipoUnidad = intTipoUnidad;
    }

    public int getIntTipoUso() {
        return intTipoUso;
    }

    public void setIntTipoUso(int intTipoUso) {
        this.intTipoUso = intTipoUso;
    }

    @Override
    public String toString() {
        return "Conceptos{" +
                "vchCodConcepto='" + vchCodConcepto + '\'' +
                ", vchConcepto='" + vchConcepto + '\'' +
                ", intTipoUnidad=" + intTipoUnidad +
                ", intTipoUso=" + intTipoUso +
                '}';
    }
}
