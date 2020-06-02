package pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroAproSolic;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.local.IFiltroListAprobSolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.FiltroListAprobSolicPermFragment.remote.IFiltroListAprobSolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.ui.dialog.DatePickerFragment;
import pe.com.dms.movilasist.util.DateUtils;

public class FiltroAprobSolicFragmentPresenter extends BasePresenter<IFiltroAprobSolicFragmentContract.View>
        implements IFiltroAprobSolicFragmentContract.Presenter {

    String TAG = FiltroAprobSolicFragmentPresenter.class.getSimpleName();

    @Inject
    IFiltroListAprobSolicPermFragmentIteratorLocal iteratorLocal;
    @Inject
    IFiltroListAprobSolicPermFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private String mDateIni, mDateFin, mcodPers, mNameLastName;
    private int mStatusSolicitud;
    private Calendar calendar;
    private List<StatusSolicitud> mListStatusSolicitud;

    @Inject
    public FiltroAprobSolicFragmentPresenter() {
        calendar = Calendar.getInstance(Locale.getDefault());
        if (mListStatusSolicitud == null)
            mListStatusSolicitud = new ArrayList<>();
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

    @Override
    public void setDateIni(String dateIni) {
        mDateIni = dateIni;
    }

    @Override
    public void setDateFin(String dateFin) {
        mDateFin = dateFin;
    }

    @Override
    public void setCodPersonal(String codPers) {
        mcodPers = codPers;
    }

    @Override
    public void setNameLastName(String nameLastName) {
        mNameLastName = nameLastName;
    }

    @Override
    public void setStatusSolicitud(int statusSolicitud) {
        mStatusSolicitud = statusSolicitud;
    }

    @Override
    public ResultListApobSolic filtroListAprobSolic() {
        ResultListApobSolic listApobSolic = new ResultListApobSolic();
        listApobSolic.setCodPersonal(mcodPers);
        listApobSolic.setNameLastName(mNameLastName);
        listApobSolic.setDateIni(mDateIni);
        listApobSolic.setDateFin(mDateFin);
        listApobSolic.setStatus(mStatusSolicitud);
        Log.e(TAG, "saveDataInModel: " + listApobSolic);
        return listApobSolic;
    }

    @Override
    public void getListStatusSolicitud() {
        Log.e(TAG, "obtainMotivoOffLine");
        getCompositeDisposable().add(iteratorLocal.getListStatusSolicitudOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(statusSolicitudList -> {
                    Log.e(TAG, "obtainMotivoOffLine subscribe : " + statusSolicitudList);
                    if (statusSolicitudList != null && statusSolicitudList.size() > 0) {
                        mListStatusSolicitud.clear();
                        mListStatusSolicitud.add(new StatusSolicitud(-1, "Seleccione"));
                        for (StatusSolicitud item : statusSolicitudList) {
                            mListStatusSolicitud.add(item);
                        }
                        getView().llenarSpinnerSolicitud(mListStatusSolicitud);
                    }
                }, error -> {
                    Log.e(TAG, "obtainMotivoOffLine error : " + error);
                    Log.e(TAG, "obtainMotivoOffLine error : " + error.toString());
                    Log.e(TAG, "obtainMotivoOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainMotivoOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public List<StatusSolicitud> listStatusSolicitud() {
        return mListStatusSolicitud;
    }
}