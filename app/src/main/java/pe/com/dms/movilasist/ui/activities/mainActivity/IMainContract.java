package pe.com.dms.movilasist.ui.activities.mainActivity;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IMainContract {
    interface View extends IBaseContract.View {

        void closedMain();

        void goToHome();
    }

    interface Presenter extends IBaseContract.Presenter<IMainContract.View> {

        boolean haveDataPendingSend();
    }
}
