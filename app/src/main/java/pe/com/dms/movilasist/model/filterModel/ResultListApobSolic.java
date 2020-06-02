package pe.com.dms.movilasist.model.filterModel;

import java.io.Serializable;

public class ResultListApobSolic implements Serializable {
    private String codPersonal;
    private String nameLastName;
    private String dateIni;
    private String dateFin;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FilterListApobSolic{" +
                "codPersonal='" + codPersonal + '\'' +
                ", nameLastName='" + nameLastName + '\'' +
                ", dateIni='" + dateIni + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
