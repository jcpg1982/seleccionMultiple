package pe.com.dms.movilasist.model.filterModel;

import java.io.Serializable;

public class ResultListMarc implements Serializable {

    private String dateIni;
    private String dateFin;

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
        return "FilterListMarc{" +
                "dateIni='" + dateIni + '\'' +
                ", dateFin='" + dateFin + '\'' +
                '}';
    }
}
