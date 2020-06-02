package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Configuracion;

@Dao
public interface ConfiguracionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long[]> setListConfiguracionOffLine(List<Configuracion> listConfig);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> setConfiguracionOffLine(Configuracion configuracion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidConfiguracionOffLine(Configuracion configuracion);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListConfiguracionOffLine(List<Configuracion> listConfig);


    @Query("DELETE FROM Configuracion")
    void deleteAllOffLine();

    @Query("SELECT * FROM Configuracion LIMIT 1")
    Maybe<Configuracion> getConfiguracionOffLine();
}