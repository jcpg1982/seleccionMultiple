package pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment;

import android.widget.EditText;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IConfigIpContract {

    interface View extends IBaseContract.View {

        void mostrarIp(String currentip);

        void setHasModifiedIp(boolean hasModifiedIp);

        boolean getHasModifiedIp();

        String getModifiedIp();

        void reiniciar();

        void closed();
    }

    interface Presenter extends IBaseContract.Presenter<IConfigIpContract.View> {

        void currentIp();

        void validateConnection();

        void setNewIp(String newIp);

        void setEditable(EditText editText, boolean editable);

        boolean hasModified();

        void saveIp();
    }
}
