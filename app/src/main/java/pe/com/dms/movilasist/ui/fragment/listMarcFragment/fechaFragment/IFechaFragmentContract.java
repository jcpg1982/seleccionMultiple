package pe.com.dms.movilasist.ui.fragment.listMarcFragment.fechaFragment;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;
import pe.com.dms.movilasist.ui.adapter.fragments.ListMarcAdapter.ListMarcAdapter;

public interface IFechaFragmentContract {

    interface View extends IBaseContract.View {

        void setModelListMarc(ResultListMarc resultListMarc);

        void viewListMarc(List<Marcaciones> marcacionList);

        ListMarcAdapter mAdapter();
    }

    interface Presenter extends IBaseContract.Presenter<IFechaFragmentContract.View> {

        void setModelListMarc(ResultListMarc resultListMarc);

        void validateConnection();

        void obtainlistMarcOffLine();

        int allPages();

        int currentPage();

        int PAGE_SIZE();

        boolean islastPage();

        void getListMarcOnLine();

        void getMoreListMarcOnLine(int page);
    }
}
