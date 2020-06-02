package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Configuracion")
public class Configuracion implements Serializable {
    @PrimaryKey()
    @NonNull
    private int bitConLocalizacion;
    private int bitMarcacionGrupal;
    private int bitIdentificacionMarca;
    private int bitIntegracionVAWEB;
    private int intTiempoEntreMarca;
    private int intMostrarMarca;
    private int intMostrarPermisos;
    private int bitMarcaPersonalNoExis;
    private int bitLecturaPorCamara;
    private int BitColocarFotoMarca;
    private String vchServidorAndroid;

    public int getBitConLocalizacion() {
        return bitConLocalizacion;
    }

    public void setBitConLocalizacion(int bitConLocalizacion) {
        this.bitConLocalizacion = bitConLocalizacion;
    }

    public int getBitMarcacionGrupal() {
        return bitMarcacionGrupal;
    }

    public void setBitMarcacionGrupal(int bitMarcacionGrupal) {
        this.bitMarcacionGrupal = bitMarcacionGrupal;
    }

    public int getBitIdentificacionMarca() {
        return bitIdentificacionMarca;
    }

    public void setBitIdentificacionMarca(int bitIdentificacionMarca) {
        this.bitIdentificacionMarca = bitIdentificacionMarca;
    }

    public int getBitIntegracionVAWEB() {
        return bitIntegracionVAWEB;
    }

    public void setBitIntegracionVAWEB(int bitIntegracionVAWEB) {
        this.bitIntegracionVAWEB = bitIntegracionVAWEB;
    }

    public int getIntTiempoEntreMarca() {
        return intTiempoEntreMarca;
    }

    public void setIntTiempoEntreMarca(int intTiempoEntreMarca) {
        this.intTiempoEntreMarca = intTiempoEntreMarca;
    }

    public int getIntMostrarMarca() {
        return intMostrarMarca;
    }

    public void setIntMostrarMarca(int intMostrarMarca) {
        this.intMostrarMarca = intMostrarMarca;
    }

    public int getIntMostrarPermisos() {
        return intMostrarPermisos;
    }

    public void setIntMostrarPermisos(int intMostrarPermisos) {
        this.intMostrarPermisos = intMostrarPermisos;
    }

    public int getBitMarcaPersonalNoExis() {
        return bitMarcaPersonalNoExis;
    }

    public void setBitMarcaPersonalNoExis(int bitMarcaPersonalNoExis) {
        this.bitMarcaPersonalNoExis = bitMarcaPersonalNoExis;
    }

    public int getBitLecturaPorCamara() {
        return bitLecturaPorCamara;
    }

    public void setBitLecturaPorCamara(int bitLecturaPorCamara) {
        this.bitLecturaPorCamara = bitLecturaPorCamara;
    }

    public int getBitColocarFotoMarca() {
        return BitColocarFotoMarca;
    }

    public void setBitColocarFotoMarca(int bitColocarFotoMarca) {
        BitColocarFotoMarca = bitColocarFotoMarca;
    }

    public String getVchServidorAndroid() {
        return vchServidorAndroid;
    }

    public void setVchServidorAndroid(String vchServidorAndroid) {
        this.vchServidorAndroid = vchServidorAndroid;
    }


    @Override
    public String toString() {
        return "Configuracion{" +
                "bitConLocalizacion=" + bitConLocalizacion +
                ", bitMarcacionGrupal=" + bitMarcacionGrupal +
                ", bitIdentificacionMarca=" + bitIdentificacionMarca +
                ", bitIntegracionVAWEB=" + bitIntegracionVAWEB +
                ", intTiempoEntreMarca=" + intTiempoEntreMarca +
                ", intMostrarMarca=" + intMostrarMarca +
                ", intMostrarPermisos=" + intMostrarPermisos +
                ", bitMarcaPersonalNoExis=" + bitMarcaPersonalNoExis +
                ", bitLecturaPorCamara=" + bitLecturaPorCamara +
                ", BitColocarFotoMarca=" + BitColocarFotoMarca +
                ", vchServidorAndroid='" + vchServidorAndroid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Configuracion) {
            Configuracion tmpConfig = (Configuracion) obj;
            if (this.bitConLocalizacion == (tmpConfig.bitConLocalizacion) &&
                    this.bitMarcacionGrupal == (tmpConfig.bitMarcacionGrupal) &&
                    this.bitIdentificacionMarca == (tmpConfig.bitIdentificacionMarca) &&
                    this.bitIntegracionVAWEB == (tmpConfig.bitIntegracionVAWEB) &&
                    this.intTiempoEntreMarca == (tmpConfig.intTiempoEntreMarca) &&
                    this.intMostrarMarca == (tmpConfig.intMostrarMarca) &&
                    this.intMostrarPermisos == (tmpConfig.intMostrarPermisos) &&
                    this.bitMarcaPersonalNoExis == (tmpConfig.bitMarcaPersonalNoExis) &&
                    this.bitLecturaPorCamara == (tmpConfig.bitLecturaPorCamara) &&
                    this.BitColocarFotoMarca == (tmpConfig.BitColocarFotoMarca)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
