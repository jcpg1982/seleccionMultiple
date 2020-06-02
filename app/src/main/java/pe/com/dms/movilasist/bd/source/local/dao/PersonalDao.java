package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.Usuario;

@Dao
public interface PersonalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListPersonalOffLine(List<Personal> listPersonal);

    @Query("DELETE FROM Personal")
    void deleteAll();
}