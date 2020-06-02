package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.model.Marcaciones;

@Dao
public interface MarcacionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insert(Marcacion marcacion);


    @Query("DELETE FROM Marcacion")
    void deleteAll();

    @Query("SELECT m.vchCodPersonal as vchCodPersonal, " +
            "u.vchNombre|| ' '||u.vchApellidos as vchNombreApe, " +
            "m.intTipoMarca as TipoMarca, " +
            "m.dtmFechaMarca as dtmFechaMarca, " +
            "m.vchCoordenadasLoc as vchCoordenadasLoc, " +
            "m.imgFoto as imgFoto, " +
            "m.vchLugarReferencia as vchLugarReferencia, " +
            "m.intTipoMarca as intTipoMarca " +
            "FROM Marcacion m " +
            "INNER JOIN Usuario u ON m.vchCodPersonal = u.vchCodigoPersonal")
    Maybe<List<Marcaciones>> setListMarcaciones();

    @Query("SELECT m.vchCodPersonal as vchCodPersonal, " +
            "u.vchNombre|| ' '||u.vchApellidos as vchNombreApe, " +
            "m.intTipoMarca as TipoMarca, " +
            "m.dtmFechaMarca as dtmFechaMarca, " +
            "m.vchCoordenadasLoc as vchCoordenadasLoc, " +
            "m.imgFoto as imgFoto, " +
            "m.vchLugarReferencia as vchLugarReferencia, " +
            "m.intTipoMarca as intTipoMarca " +
            "FROM Marcacion m " +
            "INNER JOIN Usuario u ON m.vchCodPersonal = u.vchCodigoPersonal " +
            "limit 5")
    Maybe<List<Marcaciones>> setListLastMarcaciones();

    @Query("DELETE FROM Marcacion WHERE dtmFechaMarca = :fecha")
    void deleteLastMarcOffLine(String fecha);

    @Query("SELECT m.vchCodPersonal as vchCodPersonal, " +
            "u.vchNombre|| ' '||u.vchApellidos as vchNombreApe, " +
            "m.intTipoMarca as TipoMarca, " +
            "m.dtmFechaMarca as dtmFechaMarca, " +
            "m.vchCoordenadasLoc as vchCoordenadasLoc, " +
            "m.imgFoto as imgFoto, " +
            "m.vchLugarReferencia as vchLugarReferencia, " +
            "m.intTipoMarca as intTipoMarca " +
            "FROM Marcacion m " +
            "INNER JOIN Usuario u ON m.vchCodPersonal = u.vchCodigoPersonal " +
            "WHERE dtmFechaMarca  BETWEEN :fechaIni AND :fechaFin " +
            "AND vchCodPersonal = :codPersonal AND intIntegracionVAWEB = :intWeb ")
    Maybe<List<Marcaciones>> obtainListMarcWithDate(String fechaIni, String fechaFin,
                                                    String codPersonal, int intWeb/*, int viewMarc*/);

    @Query("DELETE  FROM Marcacion " +
            "WHERE intIdmMarcaInt in (:lisCodEnviados)")
    Single<Integer> deleteMarcacionesEnviadas(List<Integer> lisCodEnviados);


    @Query("SELECT * FROM Marcacion " +
            "WHERE statusServer = :statusServer")
    Maybe<List<Marcacion>> allMarcaciones(@StatusServer String statusServer);

}