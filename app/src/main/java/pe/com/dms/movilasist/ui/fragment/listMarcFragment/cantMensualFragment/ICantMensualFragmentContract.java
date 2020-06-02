package pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantMensualFragment;

import java.util.List;

import pe.com.dms.movilasist.model.MarcaGraphics;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface ICantMensualFragmentContract {

    interface View extends IBaseContract.View {

        void setModelListMarc(ResultListMarc resultListMarc);

        void viewListGraphic(List<MarcaGraphics> listMarcGraphics);

    }

    interface Presenter extends IBaseContract.Presenter<ICantMensualFragmentContract.View> {

        void setModelListMarc(ResultListMarc resultListMarc);

        void validateConnection();

        void obtainMarcGraphicsOffLine();

        void obtainMarcGraphicsOnLine();
    }
}
