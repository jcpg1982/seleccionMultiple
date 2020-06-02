package pe.com.dms.movilasist.injection;

import android.content.Context;

import javax.inject.Named;

import dagger.Component;
import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.source.local.DataBase;
import pe.com.dms.movilasist.bd.source.remote.WebService;
import pe.com.dms.movilasist.injection.modules.ApplicationModule;
import pe.com.dms.movilasist.injection.modules.ContextModule;
import pe.com.dms.movilasist.injection.modules.NetworkModule;
import pe.com.dms.movilasist.injection.modules.SqliteModule;
import pe.com.dms.movilasist.injection.modules.WebServiceModule;
import pe.com.dms.movilasist.injection.scope.ApplicationScope;
import pe.com.dms.movilasist.services.SendDataService;


@ApplicationScope
@Component(modules = {ContextModule.class, SqliteModule.class, NetworkModule.class,
        WebServiceModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    Context context();

    PreferenceManager preferenceManager();

    DataBase dataBase();

    WebService webService();

    @Named("executor_thread")
    Scheduler executorThread();

    @Named("ui_thread")
    Scheduler uiThread();

    void inject(SendDataService sendDataService);
}
