package pe.com.dms.movilasist.ui.activities.configActivity;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IConfigActivityContract {

    interface View extends IBaseContract.View {

        void closed();
    }

    interface Presenter extends IBaseContract.Presenter<IConfigActivityContract.View> {

    }
}
