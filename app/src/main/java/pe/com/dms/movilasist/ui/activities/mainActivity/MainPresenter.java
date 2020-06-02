package pe.com.dms.movilasist.ui.activities.mainActivity;

import javax.inject.Inject;

import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class MainPresenter extends BasePresenter<IMainContract.View>
        implements IMainContract.Presenter {

    @Inject
    PreferenceManager preferenceManager;

    @Inject
    public MainPresenter() {

    }

    @Override
    public boolean haveDataPendingSend() {
        return preferenceManager.getHaveDataSend();
    }
}
