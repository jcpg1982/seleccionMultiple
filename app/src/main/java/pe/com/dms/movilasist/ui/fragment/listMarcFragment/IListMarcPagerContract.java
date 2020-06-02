package pe.com.dms.movilasist.ui.fragment.listMarcFragment;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IListMarcPagerContract {

    interface View extends IBaseContract.View {

        boolean setHasModifiedIp();

        boolean setHasModifiedSystem();
    }

    interface Presenter extends IBaseContract.Presenter<IListMarcPagerContract.View> {

    }
}
