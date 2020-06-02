package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.Usuario;

@Dao
public interface SolicitudPermisoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long[]> insertAll(List<SolicitudPermiso> listSolictudPermiso);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(SolicitudPermiso solicitudPermiso);

    @Query("SELECT * FROM SolicitudPermiso " +
            "WHERE statusServer = :statusServer")
    Maybe<List<SolicitudPermiso>> allSolicitudPermiso(@StatusServer String statusServer);

    @Query("DELETE  FROM SolicitudPermiso " +
            "WHERE intIdmSolicitudInt in (:lisCodEnviados)")
    Single<Integer> deletePermisosEnviados(List<Integer> lisCodEnviados);
}