package pe.com.dms.movilasist.bd.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "TipoMarcacion")
public class TypeMarcacion implements Serializable {
    @PrimaryKey()
    @NonNull
    private int id;
    private String descripcion;

    public TypeMarcacion(@NonNull int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
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
        return "TypeMarcacion{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
