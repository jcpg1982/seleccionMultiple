package pe.com.dms.movilasist.model;

import java.io.Serializable;

public class Marcaciones implements Serializable {
    private String vchCodPersonal;
    private String vchNombreApe;
    private String TipoMarca;
    private String dtmFechaMarca;
    private String vchCoordenadasLoc;
    private String imgFoto;
    private String vchLugarReferencia;
    private int intTipoMarca;

    public Marcaciones() {
    }

    public Marcaciones(String vchCodPersonal, String vchNombreApe, String tipoMarca,
                       String dtmFechaMarca, String vchCoordenadasLoc, String imgFoto,
                       String vchLugarReferencia, int intTipoMarca) {
        this.vchCodPersonal = vchCodPersonal;
        this.vchNombreApe = vchNombreApe;
        TipoMarca = tipoMarca;
        this.dtmFechaMarca = dtmFechaMarca;
        this.vchCoordenadasLoc = vchCoordenadasLoc;
        this.imgFoto = imgFoto;
        this.vchLugarReferencia = vchLugarReferencia;
        this.intTipoMarca = intTipoMarca;
    }

    public String getVchCodPersonal() {
        return vchCodPersonal;
    }

    public void setVchCodPersonal(String vchCodPersonal) {
        this.vchCodPersonal = vchCodPersonal;
    }

    public String getVchNombreApe() {
        return vchNombreApe;
    }

    public void setVchNombreApe(String vchNombreApe) {
        this.vchNombreApe = vchNombreApe;
    }

    public String getTipoMarca() {
        return TipoMarca;
    }

    public void setTipoMarca(String tipoMarca) {
        TipoMarca = tipoMarca;
    }

    public String getDtmFechaMarca() {
        return dtmFechaMarca;
    }

    public void setDtmFechaMarca(String dtmFechaMarca) {
        this.dtmFechaMarca = dtmFechaMarca;
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

    public int getIntTipoMarca() {
        return intTipoMarca;
    }

    public void setIntTipoMarca(int intTipoMarca) {
        this.intTipoMarca = intTipoMarca;
    }

    @Override
    public String toString() {
        return "Marcaciones{" +
                "vchCodPersonal='" + vchCodPersonal + '\'' +
                ", vchNombreApe='" + vchNombreApe + '\'' +
                ", TipoMarca='" + TipoMarca + '\'' +
                ", dtmFechaMarca='" + dtmFechaMarca + '\'' +
                ", vchCoordenadasLoc='" + vchCoordenadasLoc + '\'' +
                ", imgFoto='" + imgFoto + '\'' +
                ", vchLugarReferencia='" + vchLugarReferencia + '\'' +
                ", intTipoMarca=" + intTipoMarca +
                '}';
    }
}
