package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroSolicPerm;

import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IFiltroListSolicPermisoFragmentContract {

    interface View extends IBaseContract.View {

        void llenarSpinnerSolicitud(List<StatusSolicitud> listStatusSolicitud);

    }

    interface Presenter extends IBaseContract.Presenter<IFiltroListSolicPermisoFragmentContract.View> {

        void showDatePickerDialog(FragmentManager fragment, final EditText editText, Long dateMillis);

        void getListStatusSolicitud();

        List<StatusSolicitud> listStatusSolicitud();

    }
}
