package pe.com.dms.movilasist.ui.fragment.listLastMarcFragment;

import java.util.ArrayList;

import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.request.RequestDeleteLastMarc;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IListLastMarcFragmentContract {

    interface View extends IBaseContract.View {

        void closed();

        void mostrarLastMarc(ArrayList<Marcaciones> listLastMarc);

        void loadData();

    }

    interface Presenter extends IBaseContract.Presenter<IListLastMarcFragmentContract.View> {

        void validateConnection(@TypeOption int typeOption,
                                Marcacion marcacion,
                                RequestDeleteLastMarc requestDeleteLastMarc);

        void obtainLastMarcOffLine();

        void obtainLastMarcOnLine();

        void deleteLastMarcOffLine(Marcacion marcacion);

        void deleteLastMarcOnLine(RequestDeleteLastMarc requestDeleteLastMarc);

    }
}
