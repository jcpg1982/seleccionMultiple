package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Supervisor;

@Dao
public interface SupervisorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListSupervisorOffLine(List<Supervisor> listSuper);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidSupervisorOffLine(Supervisor supervisor);


    @Query("DELETE FROM Supervisor")
    void deleteAll();

    @Query("SELECT * FROM Supervisor")
    Maybe<List<Supervisor>> getListSupervisoresOffLine();

    @Query("SELECT * FROM Supervisor")
    Maybe<Supervisor> supervisor();
}