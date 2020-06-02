package pe.com.dms.movilasist.injection.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pe.com.dms.movilasist.injection.scope.ApplicationScope;

@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public Context provideContext() {
        return this.context;
    }
}