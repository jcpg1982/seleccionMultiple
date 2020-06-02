package pe.com.dms.movilasist.ui.fragment.configFragment;

import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IConfigPagerContract {

    interface View extends IBaseContract.View {

        void reiniciar();

        void moveToLogin();
    }

    interface Presenter extends IBaseContract.Presenter<IConfigPagerContract.View> {

        void setTypeActivity(@TypeActivity int typeActivity);

        void setModifiedIp(String modifiedIp);

        void setModifiedIdTerminal(String modifiedIdTerminal);

        void setConfiguracion(Configuracion configuracion);

        void saveConfigOffLine();

        void saveIp();

        void saveConfig();
    }
}
