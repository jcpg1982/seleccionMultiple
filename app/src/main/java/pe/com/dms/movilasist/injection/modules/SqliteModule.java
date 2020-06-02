package pe.com.dms.movilasist.injection.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import pe.com.dms.movilasist.bd.source.local.DataBase;
import pe.com.dms.movilasist.injection.scope.ApplicationScope;

@Module
public class SqliteModule {

    @Provides
    @ApplicationScope
    public DataBase provideDBApplication(Context context){
        return DataBase.getDatabase(context);
    }

}