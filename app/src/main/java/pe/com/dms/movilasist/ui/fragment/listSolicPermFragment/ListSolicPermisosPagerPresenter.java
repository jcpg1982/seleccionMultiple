package pe.com.dms.movilasist.ui.fragment.listSolicPermFragment;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ListSolicPermisosPagerPresenter extends BasePresenter<IListSolicPermisosPagerContract.View>
        implements IListSolicPermisosPagerContract.Presenter {

    String TAG = ListSolicPermisosPagerPresenter.class.getSimpleName();

    /*@Inject
    ILastMarcFragmentInteractorLocal interactorLocal;
    @Inject
    ILastMarcFragmentInteractorRemote interactorRemote;*/
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    @Inject
    public ListSolicPermisosPagerPresenter() {
    }
}
