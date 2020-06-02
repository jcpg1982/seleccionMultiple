package pe.com.dms.movilasist.injection.modules;

import dagger.Module;
import dagger.Provides;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.repository.DataSourceLocal;
import pe.com.dms.movilasist.bd.repository.DataSourceRemote;
import pe.com.dms.movilasist.injection.scope.ActivityScope;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.local.FiltroListAprobSolicPermFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.local.IFiltroListAprobSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.remote.FiltroListAprobSolicPermFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.remote.IFiltroListAprobSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.local.FiltroListSolicPermFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.local.IFiltroListSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.remote.FiltroListSolicPermFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.FiltroListSolicPermFragment.remote.IFiltroListSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.configFragment.local.ConfigFragmentInteractorLocalImpl;
import pe.com.dms.movilasist.iterator.configFragment.local.IConfigFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.configFragment.remote.ConfigFragmentInteractorRemoteImpl;
import pe.com.dms.movilasist.iterator.configFragment.remote.IConfigFragmentInteractorRemote;
import pe.com.dms.movilasist.iterator.lastMarcFragment.local.ILastMarcFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.lastMarcFragment.local.LastMarcFragmentInteractorLocalImpl;
import pe.com.dms.movilasist.iterator.lastMarcFragment.remote.ILastMarcFragmentInteractorRemote;
import pe.com.dms.movilasist.iterator.lastMarcFragment.remote.LastMarcFragmentInteractorRemoteImpl;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.local.IListAprobSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.local.ListAprobSolicPermFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.remote.IListAprobSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.listAprobSolicPermFragment.remote.ListAprobSolicPermFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.listMarcFragment.local.IListMarcFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcFragment.local.ListMarcFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.listMarcFragment.remote.IListMarcFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.listMarcFragment.remote.ListMarcFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.local.IListMarcPersFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.local.ListMarcPersFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.remote.IListMarcPersFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.listMarcPersFragment.remote.ListMarcPersFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.local.IListSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.local.ListSolicPermFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.remote.IListSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.listSolicPermFragment.remote.ListSolicPermFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.loginFragment.local.LoginFragmentInteractorLocalImpl;
import pe.com.dms.movilasist.iterator.loginFragment.remote.ILoginFragmentInteractorRemote;
import pe.com.dms.movilasist.iterator.loginFragment.remote.LoginFragmentInteractorRemoteImpl;
import pe.com.dms.movilasist.iterator.navigationDrawer.local.INavigationDrawerFragmentInteractor;
import pe.com.dms.movilasist.iterator.navigationDrawer.local.NavigationDrawerFragmentInteractorImpl;
import pe.com.dms.movilasist.iterator.recoveryPassword.IRecoveryPasswordFragmentInteractorRemote;
import pe.com.dms.movilasist.iterator.recoveryPassword.RecoveryPasswordFragmentInteractorRemoteImpl;
import pe.com.dms.movilasist.iterator.regAsistFragment.local.IRegAsistFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.regAsistFragment.local.RegAsistFragmentInteractorLocalImpl;
import pe.com.dms.movilasist.iterator.regAsistFragment.remote.IRegAsistFragmentInteractorRemote;
import pe.com.dms.movilasist.iterator.regAsistFragment.remote.RegAsistFragmentInteractorRemoteImpl;
import pe.com.dms.movilasist.iterator.solicPermFragment.local.ISolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.solicPermFragment.local.SolicPermFragmentIteratorLocalImpl;
import pe.com.dms.movilasist.iterator.solicPermFragment.remote.ISolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.solicPermFragment.remote.SolicPermFragmentIteratorRemoteImpl;
import pe.com.dms.movilasist.iterator.splashActivity.local.ISplashInteractor;
import pe.com.dms.movilasist.iterator.splashActivity.local.SplashInteractorImpl;

@Module
public class PresentationModule {

    @ActivityScope
    @Provides
    ISplashInteractor provideSplashInteractor(DataSourceLocal dataSourceLocal,
                                              PreferenceManager preferenceManager) {
        return new SplashInteractorImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    ILoginFragmentInteractorLocal provideLoginFragmentLocalInteractor(DataSourceLocal dataSourceLocal,
                                                                      PreferenceManager preferenceManager) {
        return new LoginFragmentInteractorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    ILoginFragmentInteractorRemote provideLoginFragmentRemoteInteractor(DataSourceRemote dataSourceRemote,
                                                                        DataSourceLocal dataSourceLocal,
                                                                        PreferenceManager preferenceManager) {
        return new LoginFragmentInteractorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    INavigationDrawerFragmentInteractor provideNavigationDrawerFragmentInteractor(DataSourceLocal dataSourceLocal,
                                                                                  PreferenceManager preferenceManager) {
        return new NavigationDrawerFragmentInteractorImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IConfigFragmentInteractorLocal provideConfigFragmentInteractorLocal(DataSourceLocal dataSourceLocal,
                                                                        PreferenceManager preferenceManager) {
        return new ConfigFragmentInteractorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IConfigFragmentInteractorRemote provideConfigFragmentInteractorRemote(DataSourceRemote dataSourceRemote,
                                                                          DataSourceLocal dataSourceLocal,
                                                                          PreferenceManager preferenceManager) {
        return new ConfigFragmentInteractorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IRecoveryPasswordFragmentInteractorRemote provideRecoveryPasswordFragmentInteractorRemote(DataSourceRemote dataSourceRemote,
                                                                                              PreferenceManager preferenceManager) {
        return new RecoveryPasswordFragmentInteractorRemoteImpl(dataSourceRemote, preferenceManager);
    }

    @ActivityScope
    @Provides
    IRegAsistFragmentInteractorRemote provideRegAsistFragmentInteractorRemote(DataSourceRemote dataSourceRemote,
                                                                              DataSourceLocal dataSourceLocal,
                                                                              PreferenceManager preferenceManager) {
        return new RegAsistFragmentInteractorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IRegAsistFragmentInteractorLocal provideRegAsistFragmentInteractorLocal(DataSourceLocal dataSourceLocal,
                                                                            PreferenceManager preferenceManager) {
        return new RegAsistFragmentInteractorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    ILastMarcFragmentInteractorRemote provideLastMarcFragmentInteractorRemote(DataSourceRemote dataSourceRemote,
                                                                              PreferenceManager preferenceManager) {
        return new LastMarcFragmentInteractorRemoteImpl(dataSourceRemote, preferenceManager);
    }

    @ActivityScope
    @Provides
    ILastMarcFragmentInteractorLocal provideLastMarcFragmentInteractorLocal(DataSourceLocal dataSourceLocal,
                                                                            PreferenceManager preferenceManager) {
        return new LastMarcFragmentInteractorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListMarcFragmentIteratorLocal provideListMarcFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                        PreferenceManager preferenceManager) {
        return new ListMarcFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListMarcFragmentIteratorRemote provideListMarcFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                          DataSourceLocal dataSourceLocal,
                                                                          PreferenceManager preferenceManager) {
        return new ListMarcFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    ISolicPermFragmentIteratorLocal provideSolicPermFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                          PreferenceManager preferenceManager) {
        return new SolicPermFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    ISolicPermFragmentIteratorRemote provideSolicPermFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                            DataSourceLocal dataSourceLocal,
                                                                            PreferenceManager preferenceManager) {
        return new SolicPermFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListMarcPersFragmentIteratorLocal provideListMarcPersFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                                PreferenceManager preferenceManager) {
        return new ListMarcPersFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListMarcPersFragmentIteratorRemote provideListMarcPersFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                                  DataSourceLocal dataSourceLocal,
                                                                                  PreferenceManager preferenceManager) {
        return new ListMarcPersFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListSolicPermFragmentIteratorLocal provideListSolicPermFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                                  PreferenceManager preferenceManager) {
        return new ListSolicPermFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListSolicPermFragmentIteratorRemote provideListSolicPermFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                                    DataSourceLocal dataSourceLocal,
                                                                                    PreferenceManager preferenceManager) {
        return new ListSolicPermFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IFiltroListSolicPermFragmentIteratorLocal provideFiltroListSolicPermFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                                              PreferenceManager preferenceManager) {
        return new FiltroListSolicPermFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IFiltroListSolicPermFragmentIteratorRemote provideFiltroListSolicPermFragmentInteractorRemote(DataSourceRemote dataSourceRemote,
                                                                                                  DataSourceLocal dataSourceLocal,
                                                                                                  PreferenceManager preferenceManager) {
        return new FiltroListSolicPermFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IFiltroListAprobSolicPermFragmentIteratorLocal provideFiltroListAprobSolicPermFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                                                        PreferenceManager preferenceManager) {
        return new FiltroListAprobSolicPermFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IFiltroListAprobSolicPermFragmentIteratorRemote provideFiltroListAprobSolicPermFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                                                          DataSourceLocal dataSourceLocal,
                                                                                                          PreferenceManager preferenceManager) {
        return new FiltroListAprobSolicPermFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListAprobSolicPermFragmentIteratorLocal provideListAprobSolicPermFragmentIteratorLocal(DataSourceLocal dataSourceLocal,
                                                                                            PreferenceManager preferenceManager) {
        return new ListAprobSolicPermFragmentIteratorLocalImpl(dataSourceLocal, preferenceManager);
    }

    @ActivityScope
    @Provides
    IListAprobSolicPermFragmentIteratorRemote provideListAprobSolicPermFragmentIteratorRemote(DataSourceRemote dataSourceRemote,
                                                                                              DataSourceLocal dataSourceLocal,
                                                                                              PreferenceManager preferenceManager) {
        return new ListAprobSolicPermFragmentIteratorRemoteImpl(dataSourceRemote, dataSourceLocal, preferenceManager);
    }
}
