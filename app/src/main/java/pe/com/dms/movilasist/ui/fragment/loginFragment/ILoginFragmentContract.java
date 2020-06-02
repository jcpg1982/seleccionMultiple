package pe.com.dms.movilasist.ui.fragment.loginFragment;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ILoginFragmentContract {

    interface View extends IBaseContract.View {

        void moveToMain();
    }

    interface Presenter extends IBaseContract.Presenter<ILoginFragmentContract.View> {

        void getConfigOffLine();

        void getConfigOnLine();

        void setUser(String user);

        void setPassword(String password);

        void validaConnection();

        void loginOnLine();

        void loginOffLine();

        void syncTablas();
    }
}
