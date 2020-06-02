package pe.com.dms.movilasist.bd.source.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import pe.com.dms.movilasist.bd.entity.Usuario;

@Dao
public interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long[]> setListUsuarioOffLine(List<Usuario> listUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> setUsuarioOffLine(Usuario user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidListUsuarioOffLine(List<Usuario> listUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void voidUsuarioOffLine(Usuario user);


    @Query("DELETE FROM Usuario")
    void deleteAll();

    @Query("SELECT * FROM Usuario " +
            "WHERE  vchDocumento = :username and  vchPassword = :password")
    Maybe<Usuario> validarUsuario(String username, String password);


    @Query("SELECT * FROM Usuario " +
            "WHERE  vchDocumento = :document")
    Maybe<Usuario> obtainUsuarioWithDocument(String document);


    @Query("SELECT * FROM Usuario ")
    Maybe<Usuario> obtainUser();

}