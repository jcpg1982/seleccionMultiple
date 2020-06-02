package pe.com.dms.movilasist.bd.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import pe.com.dms.movilasist.annotacion.StatusServer;

@Entity(tableName = "Marcacion")
public class Marcacion implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int intIdmMarcaInt;
    private String dtmFechaMarca;
    private String vchCodPersonal;
    private int intTipoMotivo;
    private int intTipoMarca;
    private String vchCoordenadasLoc;
    private String imgFoto;
    private String vchLugarReferencia;
    private int intMarcacion;
    private int intIntegracionVAWEB;
    @StatusServer
    private String statusServer;

    public Marcacion() {
    }

    public Marcacion(String dtmFechaMarca, String vchCodPersonal, int intTipoMotivo,
                     int intTipoMarca, String vchCoordenadasLoc, String imgFoto,
                     String vchLugarReferencia, int intMarcacion, int intIntegracionVAWEB,
                     String statusServer) {
        this.dtmFechaMarca = dtmFechaMarca;
        this.vchCodPersonal = vchCodPersonal;
        this.intTipoMotivo = intTipoMotivo;
        this.intTipoMarca = intTipoMarca;
        this.vchCoordenadasLoc = vchCoordenadasLoc;
        this.imgFoto = imgFoto;
        this.vchLugarReferencia = vchLugarReferencia;
        this.intMarcacion = intMarcacion;
        this.intIntegracionVAWEB = intIntegracionVAWEB;
        this.statusServer = statusServer;
    }

    public int getIntIdmMarcaInt() {
        return intIdmMarcaInt;
    }

    public void setIntIdmMarcaInt(int intIdmMarcaInt) {
        this.intIdmMarcaInt = intIdmMarcaInt;
    }

    public String getDtmFechaMarca() {
        return dtmFechaMarca;
    }

    public void setDtmFechaMarca(String dtmFechaMarca) {
        this.dtmFechaMarca = dtmFechaMarca;
    }

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public int getIntTipoMotivo() {
        return intTipoMotivo;
    }

    public void setIntTipoMotivo(int intTipoMotivo) {
        this.intTipoMotivo = intTipoMotivo;
    }

    public int getIntTipoMarca() {
        return intTipoMarca;
    }

    public void setIntTipoMarca(int intTipoMarca) {
        this.intTipoMarca = intTipoMarca;
    }

    public String getVchCoordenadasLoc() {
        return vchCoordenadasLoc;
    }

    public void setVchCoordenadasLoc(String vchCoordenadasLoc) {
        this.vchCoordenadasLoc = vchCoordenadasLoc;
    }

    public String getImgFoto() {
        return imgFoto;
    }

    public void setImgFoto(String imgFoto) {
        this.imgFoto = imgFoto;
    }

    public String getVchLugarReferencia() {
        return vchLugarReferencia;
    }

    public void setVchLugarReferencia(String vchLugarReferencia) {
        this.vchLugarReferencia = vchLugarReferencia;
    }

    public int getIntMarcacion() {
        return intMarcacion;
    }

    public void setIntMarcacion(int intMarcacion) {
        this.intMarcacion = intMarcacion;
    }

    public int getIntIntegracionVAWEB() {
        return intIntegracionVAWEB;
    }

    public void setIntIntegracionVAWEB(int intIntegracionVAWEB) {
        this.intIntegracionVAWEB = intIntegracionVAWEB;
    }

    public String getStatusServer() {
        return statusServer;
    }

    public void setStatusServer(String statusServer) {
        this.statusServer = statusServer;
    }

    @Override
    public String toString() {
        return "Marcacion{" +
                "intIdmMarcaInt=" + intIdmMarcaInt +
                ", dtmFechaMarca='" + dtmFechaMarca + '\'' +
                ", vchCodPersonal='" + vchCodPersonal + '\'' +
                ", intTipoMotivo=" + intTipoMotivo +
                ", intTipoMarca=" + intTipoMarca +
                ", vchCoordenadasLoc='" + vchCoordenadasLoc + '\'' +
                ", imgFoto='" + imgFoto + '\'' +
                ", vchLugarReferencia='" + vchLugarReferencia + '\'' +
                ", intMarcacion=" + intMarcacion +
                ", intIntegracionVAWEB=" + intIntegracionVAWEB +
                ", statusServer='" + statusServer + '\'' +
                '}';
    }
}
