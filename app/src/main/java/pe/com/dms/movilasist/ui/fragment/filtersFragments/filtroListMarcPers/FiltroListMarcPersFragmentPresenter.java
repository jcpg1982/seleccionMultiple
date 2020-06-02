package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarcPers;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.ui.dialog.DatePickerFragment;
import pe.com.dms.movilasist.util.DateUtils;

public class FiltroListMarcPersFragmentPresenter extends BasePresenter<IFiltroListMarcPersFragmentContract.View>
        implements IFiltroListMarcPersFragmentContract.Presenter {

    String TAG = FiltroListMarcPersFragmentPresenter.class.getSimpleName();

    @Inject
    ILoginFragmentInteractorLocal interactor;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;
    private Calendar calendar;

    @Inject
    public FiltroListMarcPersFragmentPresenter() {
        calendar = Calendar.getInstance(Locale.getDefault());
    }

    @Override
    public void showDatePickerDialog(FragmentManager fragment, final EditText editText, Long dateTime) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.e(TAG, "year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String date = DateUtils.dateToStringFormat(calendar.getTime(),
                                DateUtils.PATTERN_DATE_LECTURA);
                        Log.e(TAG, "date: " + date);
                        editText.setText(date);
                    }
                }, dateTime);
        newFragment.show(fragment, DatePickerFragment.TAG);
    }

}
