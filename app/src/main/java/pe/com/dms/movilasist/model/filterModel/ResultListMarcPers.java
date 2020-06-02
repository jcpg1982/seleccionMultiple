package pe.com.dms.movilasist.model.filterModel;

import java.io.Serializable;

public class ResultListMarcPers implements Serializable {
    private String codPersonal;
    private String nameLastName;
    private String dateIni;
    private String dateFin;

    public String getCodPersonal() {
        return codPersonal;
    }

    public void setCodPersonal(String codPersonal) {
        this.codPersonal = codPersonal;
    }

    public String getNameLastName() {
        return nameLastName;
    }

    public void setNameLastName(String nameLastName) {
        this.nameLastName = nameLastName;
    }

    public String getDateIni() {
        return dateIni;
    }

    public void setDateIni(String dateIni) {
        this.dateIni = dateIni;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "FilterListMarcPers{" +
                "codPersonal='" + codPersonal + '\'' +
                ", nameLastName='" + nameLastName + '\'' +
                ", dateIni='" + dateIni + '\'' +
                ", dateFin='" + dateFin + '\'' +
                '}';
    }
}
