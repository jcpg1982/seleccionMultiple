package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarcPers;

import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pe.com.dms.movilasist.ui.activities.base.IBaseContract;

public interface IFiltroListMarcPersFragmentContract {

    interface View extends IBaseContract.View {

    }

    interface Presenter extends IBaseContract.Presenter<IFiltroListMarcPersFragmentContract.View> {

        void showDatePickerDialog(FragmentManager fragment, final EditText editText, Long dateMillis);

    }
}
