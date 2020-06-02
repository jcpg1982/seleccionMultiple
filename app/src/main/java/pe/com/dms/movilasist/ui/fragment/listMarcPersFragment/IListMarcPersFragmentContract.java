package pe.com.dms.movilasist.ui.fragment.listMarcPersFragment;

import java.util.List;

import pe.com.dms.movilasist.model.Marcaciones;
import pe.com.dms.movilasist.model.filterModel.ResultListMarcPers;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;
import pe.com.dms.movilasist.ui.adapter.fragments.ListMarcPersAdapter.ListMarcPersAdapter;

public interface IListMarcPersFragmentContract {

    interface View extends IBaseContract.View {

        void viewListMarcPers(List<Marcaciones> listMarcPers);

        ListMarcPersAdapter mAdapter();
    }

    interface Presenter extends IBaseContract.Presenter<IListMarcPersFragmentContract.View> {

        void obtainDataUserAndConfig();

        void validateConnection();

        void setResultListMarcPers(ResultListMarcPers resultListMarcPers);

        int allPages();

        int currentPage();

        int PAGE_SIZE();

        boolean islastPage();

        List<Marcaciones> getDataDummyListMarcPers();

        void getListMarcPersOnLine();

        void getMoreListMarcPersOnLine(int page);
    }
}
