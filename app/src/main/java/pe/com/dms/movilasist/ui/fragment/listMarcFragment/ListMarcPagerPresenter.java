package pe.com.dms.movilasist.ui.fragment.listMarcFragment;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.listMarcFragment.local.IListMarcFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcFragment.remote.IListMarcFragmentIteratorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class ListMarcPagerPresenter extends BasePresenter<IListMarcPagerContract.View>
        implements IListMarcPagerContract.Presenter {

    String TAG = ListMarcPagerPresenter.class.getSimpleName();

    @Inject
    IListMarcFragmentIteratorLocal iteratorLocal;
    @Inject
    IListMarcFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    @Inject
    public ListMarcPagerPresenter() {
    }
}
