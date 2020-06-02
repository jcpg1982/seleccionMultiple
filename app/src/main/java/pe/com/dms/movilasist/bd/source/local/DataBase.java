package pe.com.dms.movilasist.bd.source.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.Personal;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.bd.source.local.dao.ConceptoDao;
import pe.com.dms.movilasist.bd.source.local.dao.ConfiguracionDao;
import pe.com.dms.movilasist.bd.source.local.dao.MarcacionDao;
import pe.com.dms.movilasist.bd.source.local.dao.MotivoDao;
import pe.com.dms.movilasist.bd.source.local.dao.PersonalDao;
import pe.com.dms.movilasist.bd.source.local.dao.SolicitudPermisoDao;
import pe.com.dms.movilasist.bd.source.local.dao.StatusSolicitudDao;
import pe.com.dms.movilasist.bd.source.local.dao.SupervisorDao;
import pe.com.dms.movilasist.bd.source.local.dao.TypeMarcacionDao;
import pe.com.dms.movilasist.bd.source.local.dao.UsuarioDao;

@Database(entities = {Usuario.class,
        Configuracion.class,
        Supervisor.class,
        Motivo.class,
        Personal.class,
        Conceptos.class,
        Marcacion.class,
        SolicitudPermiso.class,
        TypeMarcacion.class,
        StatusSolicitud.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static volatile DataBase INSTANCE;

    public abstract UsuarioDao usuarioDao();

    public abstract ConfiguracionDao configuracionDao();

    public abstract SupervisorDao supervisorDao();

    public abstract MotivoDao motivoDao();

    public abstract PersonalDao personalDao();

    public abstract ConceptoDao conceptoDao();

    public abstract MarcacionDao marcacionDao();

    public abstract SolicitudPermisoDao solicitudPermisoDao();

    public abstract TypeMarcacionDao typeMarcacionDao();

    public abstract StatusSolicitudDao statusSolicitudDao();

    public static DataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataBase.class, "DataBase.db")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                }
            };
}
