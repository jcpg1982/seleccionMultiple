package pe.com.dms.movilasist.ui.fragment.listAprobSolic;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IListAprobSolicPagerContract {

    interface View extends IBaseContract.View {

        boolean setHasModifiedIp();

        boolean setHasModifiedSystem();
    }

    interface Presenter extends IBaseContract.Presenter<IListAprobSolicPagerContract.View> {

    }
}
