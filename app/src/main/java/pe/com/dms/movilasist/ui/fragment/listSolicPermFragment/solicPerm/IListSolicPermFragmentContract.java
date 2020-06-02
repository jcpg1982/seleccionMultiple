package pe.com.dms.movilasist.ui.fragment.listSolicPermFragment.solicPerm;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.model.SolicitudesPermiso;
import pe.com.dms.movilasist.model.filterModel.ResultListSolicPers;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IListSolicPermFragmentContract {

    interface View extends IBaseContract.View {

        void setResultListSolicPers(ResultListSolicPers resultListSolicPers);

        void viewListSolicPerm(List<SolicitudesPermiso> ListSolicPerm);

        void mostrarMensaje(String message);
    }

    interface Presenter extends IBaseContract.Presenter<IListSolicPermFragmentContract.View> {

        void setResultListSolicPers(ResultListSolicPers resultListSolicPers);

        List<SolicitudesPermiso> getDataDummyListPerm();

        int allPages();

        int currentPage();

        int PAGE_SIZE();

        boolean islastPage();

        void validaConnection();

        void getListSolicPermOnLine();

        void getMoreListSolicPermOnLine(int page);

        void setRequestDeleteSolicitud(SolicitudesPermiso solicitud);

        void deleteSolicitudOnLine();
    }
}
