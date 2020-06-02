package pe.com.dms.movilasist.ui.fragment.lastPermFragment;

import java.util.List;

import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ILastPermFragmentContract {

    interface View extends IBaseContract.View {

        void closed();

        void mostrarLastMarc(List<SolicitudesPermiso> listLastMarc);

        void loadData();

    }

    interface Presenter extends IBaseContract.Presenter<ILastPermFragmentContract.View> {

        void validateConnection(@TypeOption int typeOption,
                                SolicitudesPermiso marcacion);

        void obtainLastMarcOffLine();

        void obtainLastMarcOnLine();

        void deleteLastMarcOffLine(SolicitudesPermiso marcacion);

        void deleteLastMarcOnLine(SolicitudesPermiso marcacion);

        List<SolicitudesPermiso> getDataDummyListPerm();
    }
}
