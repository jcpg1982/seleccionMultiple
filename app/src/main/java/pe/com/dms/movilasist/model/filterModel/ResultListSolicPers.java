package pe.com.dms.movilasist.model.filterModel;

import java.io.Serializable;

public class ResultListSolicPers implements Serializable {
    private String dateIni;
    private String dateFin;
    private int status;

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
        return "FilterListSolicPers{" +
                "dateIni='" + dateIni + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
