package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "EstadoSolicitud")
public class StatusSolicitud implements Serializable {
    @PrimaryKey()
    @NonNull
    private int id;
    private String descripcion;

    public StatusSolicitud(@NonNull int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "StatusSolicitud{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
