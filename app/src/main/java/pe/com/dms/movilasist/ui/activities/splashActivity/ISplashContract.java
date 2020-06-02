package pe.com.dms.movilasist.ui.activities.splashActivity;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ISplashContract {

    interface View extends IBaseContract.View {

        void moveToLogin();
    }

    interface Presenter extends IBaseContract.Presenter<ISplashContract.View> {

       /* void createUsers();

        void iniciarContador();*/

    }
}
