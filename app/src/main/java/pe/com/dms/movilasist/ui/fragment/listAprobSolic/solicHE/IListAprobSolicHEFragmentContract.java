package pe.com.dms.movilasist.ui.fragment.listAprobSolic.solicHE;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IListAprobSolicHEFragmentContract {

    interface View extends IBaseContract.View {

        void setResultListApobSolic(ResultListApobSolic resultListApobSolic);

        void viewListSolicPerm(List<SolicitudesPermiso> listSolicPerm);

    }

    interface Presenter extends IBaseContract.Presenter<IListAprobSolicHEFragmentContract.View> {

        List<SolicitudesPermiso> getDataDummyListPerm();

        void setResultListApobSolic(ResultListApobSolic resultListApobSolic);

        int allPages();

        int currentPage();

        int PAGE_SIZE();

        boolean islastPage();

        void validaConnection();

        void getListAprobSolicPermOnLine();

        void getMoreListSolicPermOnLine(int page);

        void deleteSolicitudOnLine(SolicitudesPermiso solicitudesPermiso);

        void aprobeSolicitudOnLine(SolicitudesPermiso solicitudesPermiso);
    }
}
