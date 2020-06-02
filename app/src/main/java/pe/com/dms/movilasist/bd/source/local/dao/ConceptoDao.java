package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import pe.com.dms.movilasist.bd.entity.Conceptos;

@Dao
public interface ConceptoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListConceptosOffLine(List<Conceptos> listConcepto);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidConceptosOffLine(Conceptos conceptos);


    @Query("DELETE FROM Conceptos")
    void deleteAll();

    @Query("SELECT * FROM Conceptos")
    Maybe<Conceptos> conceptos();

    @Query("SELECT * FROM Conceptos")
    Maybe<List<Conceptos>> getListConceptosOffLine();
}