package pe.com.dms.movilasist.ui.activities.loginActivity;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ILoginActivityContract {

    interface View extends IBaseContract.View {

        void closed();
    }

    interface Presenter extends IBaseContract.Presenter<ILoginActivityContract.View> {

    }
}
