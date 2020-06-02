package pe.com.dms.movilasist;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import pe.com.dms.movilasist.injection.ApplicationComponent;
import pe.com.dms.movilasist.injection.DaggerApplicationComponent;
import pe.com.dms.movilasist.injection.modules.ContextModule;

public class Application extends MultiDexApplication {

    public static ApplicationComponent appComponent;
    public static Application sApplicationInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        buildDependecyInjection();
    }

    private void buildDependecyInjection() {
        appComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
    }

    public ApplicationComponent getAppComponent() {
        if (appComponent == null) {
            buildDependecyInjection();
        }
        return appComponent;
    }

    public void clearDaggerComponent() {
        appComponent = null;
    }

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    public static Application getInstance() {
        return sApplicationInstance;
    }
}