package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroAproSolic;

import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IFiltroAprobSolicFragmentContract {

    interface View extends IBaseContract.View {
        void llenarSpinnerSolicitud(List<StatusSolicitud> listStatusSolicitud);
    }

    interface Presenter extends IBaseContract.Presenter<IFiltroAprobSolicFragmentContract.View> {

        void showDatePickerDialog(FragmentManager fragment, final EditText editText, Long dateMillis);

        void setDateIni(String dateIni);

        void setDateFin(String dateFin);

        void setCodPersonal(String codPers);

        void setNameLastName(String nameLastName);

        void setStatusSolicitud(int statusSolicitud);

        ResultListApobSolic filtroListAprobSolic();

        void getListStatusSolicitud();

        List<StatusSolicitud> listStatusSolicitud();
    }
}
