package pe.com.dms.movilasist.ui.fragment.configFragment.systemFragment;

import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ISystemContract {

    interface View extends IBaseContract.View {

        void mostrarIdTerminal(String currentId);

        void cargarConfiguraciones(Configuracion configuracion);

        void setHasModifiedIdTerminal(boolean hasModifiedIdTerminal);

        boolean getHasModifiedConfig();

        String getModifiedIdTerminal();

        Configuracion getConfiguracion();
    }

    interface Presenter extends IBaseContract.Presenter<ISystemContract.View> {

        void currentIdTerminal();

        void setNewIdTerminal(String newIdTerminal);

        void validaConnection();

        void obtainConfigOnLine();

        void obtainConfigOffLine();

        void setBitConGPS(int bitConLocalización);

        void setBitMarcacionGrupal(int bitMarcacionGrupal);

        void setBitIdentificacionMarca(int bitIdentificacionMarca);

        void setBitIntegracionVAWEB(int bitIntegraciónVAWEB);

        void setBitMarcaPersonalNoExis(int bitMarcaPersonalNoExis);

        void setBitLecturaPorCamara(int bitLecturaPorCamara);

        void setBitColocarFotoMarca(int BitColocarFotoMarca);

        void setIntTiempoEntreMarca(int intTiempoEntreMarca);

        void setIntMostrarMarca(int intMostrarMarca);

        void setIntMostrarPermisos(int intMostrarPermisos);

        boolean getModifiedConfig();

        Configuracion getConfiguracion();
    }
}
