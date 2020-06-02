package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Supervisor;

@Dao
public interface MotivoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListMotivoOffLine(List<Motivo> listMotiv);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidMotivoOffLine(Motivo listMotiv);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Motivo motivo);


    @Query("DELETE FROM Motivo")
    void deleteAll();

    @Query("SELECT * FROM Motivo")
    Maybe<List<Motivo>> getListMotivoOffLine();

    @Query("SELECT vchMotivo as motivo FROM Motivo " +
            "WHERE vchCodMotivo = :codMotivo")
    Maybe<String> getMotivoById(String codMotivo);
}