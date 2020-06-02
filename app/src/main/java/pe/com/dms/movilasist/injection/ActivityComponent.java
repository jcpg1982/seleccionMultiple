package pe.com.dms.movilasist.injection;

import dagger.Component;
import pe.com.dms.movilasist.injection.modules.PresentationModule;
import pe.com.dms.movilasist.injection.scope.ActivityScope;
import pe.com.dms.movilasist.ui.activities.configActivity.ConfigActivity;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;
import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;
import pe.com.dms.movilasist.ui.activities.searchActivity.SearchActivity;
import pe.com.dms.movilasist.ui.activities.splashActivity.SplashActivity;
import pe.com.dms.movilasist.ui.fragment.configFragment.ConfigPagerFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment.ConfigIpFragment;
import pe.com.dms.movilasist.ui.fragment.configFragment.systemFragment.SystemFragment;
import pe.com.dms.movilasist.ui.fragment.drawer.NavigationDrawerFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroAproSolic.FiltroAprobSolicFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarc.FiltroListMarcFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarcPers.FiltroListMarcPersFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroSolicPerm.FiltroListSolicPermisoFragment;
import pe.com.dms.movilasist.ui.fragment.lastPermFragment.LastPermFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.ListAprobSolicPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicHE.ListAprobSolicHEFragment;
import pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicPerm.ListAprobSolicPermFragment;
import pe.com.dms.movilasist.ui.fragment.listLastMarcFragment.ListLastMarcFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.ListMarcPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantDiarioFragment.CantDiarioFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantMensualFragment.CantMensualFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantSemanalFragment.CantSemanalFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcFragment.fechaFragment.FechaFragment;
import pe.com.dms.movilasist.ui.fragment.listMarcPersFragment.ListMarcPersFragment;
import pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.ListSolicPermisosPagerFragment;
import pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.solicHE.ListSolicHEFragment;
import pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.solicPerm.ListSolicPermFragment;
import pe.com.dms.movilasist.ui.fragment.loginFragment.LoginFragment;
import pe.com.dms.movilasist.ui.fragment.recoveryPasword.RecoveryPasswordFragment;
import pe.com.dms.movilasist.ui.fragment.regAsistFragment.RegistroAsistenciaFragment;
import pe.com.dms.movilasist.ui.fragment.solicPermFragment.SolicPermisoFragment;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = PresentationModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(SearchActivity activity);

    void inject(ConfigActivity activity);


    void inject(ConfigPagerFragment fragment);

    void inject(LoginFragment fragment);

    void inject(NavigationDrawerFragment fragment);

    void inject(RegistroAsistenciaFragment fragment);

    void inject(ConfigIpFragment fragment);

    void inject(SystemFragment fragment);

    void inject(ListMarcPagerFragment fragment);

    void inject(FechaFragment fragment);

    void inject(CantDiarioFragment fragment);

    void inject(CantSemanalFragment fragment);

    void inject(CantMensualFragment fragment);

    void inject(ListMarcPersFragment fragment);

    void inject(SolicPermisoFragment fragment);

    void inject(ListSolicPermisosPagerFragment fragment);

    void inject(FiltroListMarcFragment fragment);

    void inject(FiltroListMarcPersFragment fragment);

    void inject(FiltroListSolicPermisoFragment fragment);

    void inject(ListSolicPermFragment fragment);

    void inject(ListSolicHEFragment fragment);

    void inject(FiltroAprobSolicFragment fragment);

    void inject(ListAprobSolicPagerFragment fragment);

    void inject(ListAprobSolicHEFragment fragment);

    void inject(ListAprobSolicPermFragment fragment);

    void inject(RecoveryPasswordFragment fragment);

    void inject(ListLastMarcFragment fragment);

    void inject(LastPermFragment fragment);
}
