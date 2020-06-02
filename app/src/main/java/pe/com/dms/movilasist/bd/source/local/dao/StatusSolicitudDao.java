package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;

@Dao
public interface StatusSolicitudDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long[]> insertAll(List<StatusSolicitud> listStatusSolicitud);

    @Query("SELECT * FROM EstadoSolicitud")
    Maybe<List<StatusSolicitud>> getListStatusSolicitudOffLine();

    @Query("SELECT descripcion as estado FROM EstadoSolicitud " +
            "WHERE id = :codMotivo")
    Maybe<String> getStatusSolicitudById(int codMotivo);
}