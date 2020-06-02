package pe.com.dms.movilasist.ui.fragment.recoveryPasword;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IRecoveryPasswordFragmentContract {

    interface View extends IBaseContract.View {

        void closed();
    }

    interface Presenter extends IBaseContract.Presenter<IRecoveryPasswordFragmentContract.View> {

        void setCorreo(String correo);

        boolean validateCorreo();

        void validaConnection();

        void recoveryPassword();
    }
}
