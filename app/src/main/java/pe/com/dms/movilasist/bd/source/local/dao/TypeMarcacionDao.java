package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;

@Dao
public interface TypeMarcacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long[]> insertAll(List<TypeMarcacion> listTypeMarcacion);

    @Query("SELECT * FROM TipoMarcacion")
    Maybe<List<TypeMarcacion>> getListTypeMarcacionOffLine();
}