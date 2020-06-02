package pe.com.dms.movilasist.ui.fragment.solicPermFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.annotacion.SwitchActivo;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Conceptos;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.entity.Supervisor;
import pe.com.dms.movilasist.iterator.solicPermFragment.local.ISolicPermFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.solicPermFragment.remote.ISolicPermFragmentIteratorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.ui.dialog.DatePickerFragment;
import pe.com.dms.movilasist.ui.dialog.TimePickerFragment;
import pe.com.dms.movilasist.util.DateUtils;

public class SolicPermisoFragmentPresenter extends BasePresenter<SolicPermisoFragmentContract.View>
        implements SolicPermisoFragmentContract.Presenter {

    String TAG = SolicPermisoFragmentPresenter.class.getSimpleName();

    @Inject
    ISolicPermFragmentIteratorLocal iteratorLocal;
    @Inject
    ISolicPermFragmentIteratorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;

    private SolicitudPermiso mSolicitudPermiso;
    private Calendar calendar;
    private List<Conceptos> mListConceptos;
    private List<Supervisor> mListSupervisor;

    @Inject
    public SolicPermisoFragmentPresenter() {
        mSolicitudPermiso = new SolicitudPermiso();
        mSolicitudPermiso.setStatusServer(StatusServer.PENDIENTE);
        calendar = Calendar.getInstance(Locale.getDefault());
        if (mListConceptos == null)
            mListConceptos = new ArrayList<>();
        if (mListSupervisor == null)
            mListSupervisor = new ArrayList<>();
    }

    @Override
    public void obtainUser() {
        getView().viewInfoUser(preferenceManager.getUser());
        mSolicitudPermiso.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
    }

    @Override
    public void obtainConfig() {
        if (preferenceManager.getConfig() != null) {
            mSolicitudPermiso.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        }
    }

    @Override
    public void obtainListSolicitud() {
        Log.e(TAG, "obtainListSolicitud");
        getCompositeDisposable().add(iteratorLocal.getListConceptosOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    if (mListConceptos != null)
                        mListConceptos.clear();
                    Log.e(TAG, "obtainListSolicitud subscribe : " + list);
                    if (list != null && list.size() > 0) {
                        if (list.size() == 1) {
                            mListConceptos = list;
                            getView().llenarSpinerSolicitud(mListConceptos);
                        } else {
                            mListConceptos.add(new Conceptos("0", "Seleccione"));
                            for (Conceptos item : list) {
                                mListConceptos.add(item);
                            }
                            getView().llenarSpinerSolicitud(mListConceptos);
                        }
                    } else {
                        getView().llenarSpinerSolicitud(null);
                    }
                }, error -> {
                    Log.e(TAG, "obtainListSolicitud error : " + error);
                    Log.e(TAG, "obtainListSolicitud error : " + error.toString());
                    Log.e(TAG, "obtainListSolicitud error : " + error.getMessage());
                    Log.e(TAG, "obtainListSolicitud error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public List<Conceptos> getListConceptos() {
        return mListConceptos;
    }

    @Override
    public void setConcepto(Conceptos concepto) {
        mSolicitudPermiso.setVchCodConcepto(concepto.getVchCodConcepto());
        mSolicitudPermiso.setIntTipoUso(concepto.getIntTipoUso());
    }

    @Override
    public void obtainListsupervisor() {
        Log.e(TAG, "obtainListsupervisor");
        getCompositeDisposable().add(iteratorLocal.getListSupervisoresOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "obtainListsupervisor subscribe : " + list);
                    if (mListSupervisor != null)
                        mListSupervisor.clear();
                    if (list != null && list.size() > 0) {
                        if (list.size() == 1) {
                            mListSupervisor = list;
                            getView().llenarSpinnerSupervisor(mListSupervisor);
                        } else {
                            mListSupervisor.add(new Supervisor("0", "Seleccione"));
                            for (Supervisor item : list) {
                                mListSupervisor.add(item);
                            }
                            getView().llenarSpinnerSupervisor(mListSupervisor);
                        }
                    } else {
                        getView().llenarSpinnerSupervisor(null);
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "obtainListsupervisor error : " + error);
                    Log.e(TAG, "obtainListsupervisor error : " + error.toString());
                    Log.e(TAG, "obtainListsupervisor error : " + error.getMessage());
                    Log.e(TAG, "obtainListsupervisor error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public List<Supervisor> getListSupervisor() {
        return mListSupervisor;
    }

    @Override
    public void setSupervisor(Supervisor supervisor) {
        mSolicitudPermiso.setVchCodSupervisor(supervisor.getVchCodigoPersonal());
        mSolicitudPermiso.setVchEmailSupervisor(supervisor.getVchCorreo());
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
    public void showTimePickerDialog(FragmentManager fragment, final EditText editText) {
        TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e(TAG, "hourOfDay: " + hourOfDay + ", minute: " + minute);
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String time = DateUtils.dateToStringFormat(calendar.getTime(),
                                DateUtils.PATTERN_TIME_TAREO);
                        Log.e(TAG, "date: " + time);
                        editText.setText(time);
                    }
                });
        timePickerFragment.show(fragment, DatePickerFragment.TAG);
    }

    @Override
    public void setDtmFechaInicio(String dtmFechaInicio) {
        mSolicitudPermiso.setDtmFechaInicio(dtmFechaInicio);
    }

    @Override
    public void setDtmFechaFin(String dtmFechaFin) {
        mSolicitudPermiso.setDtmFechaFin(dtmFechaFin);
    }

    @Override
    public void setVchHoraInicio(String vchHoraInicio) {
        mSolicitudPermiso.setVchHoraInicio(vchHoraInicio);
    }

    @Override
    public void setVchHoraFin(String vchHoraFin) {
        mSolicitudPermiso.setVchHoraFin(vchHoraFin);
    }

    @Override
    public void setIntPertenenciaInicio(int intPertenenciaInicio) {
        mSolicitudPermiso.setIntPertenenciaInicio(intPertenenciaInicio);
    }

    @Override
    public void setIntPertenenciaFin(int intPertenenciaFin) {
        mSolicitudPermiso.setIntPertenenciaFin(intPertenenciaFin);
    }

    @Override
    public void setVchObservacion(String vchObservacion) {
        mSolicitudPermiso.setVchObservacion(vchObservacion);
    }

    @Override
    public void setIntEstadoSolicitud(int intEstadoSolicitud) {
        mSolicitudPermiso.setIntEstadoSolicitud(intEstadoSolicitud);
    }

    @Override
    public void validatedConnection() {
        getView().showProgressbar("Espere...", "Verificando la conexion a la base de datos");
        Log.e(TAG, "validaConnection");
        getCompositeDisposable().add(iteratorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            sendSolicPermOnLine();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                            sendSolicPermOffLine();
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : " + error);
                    Log.e(TAG, "validaConnection error : " + error.toString());
                    Log.e(TAG, "validaConnection error : " + error.getMessage());
                    Log.e(TAG, "validaConnection error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                    sendSolicPermOffLine();
                }));
    }

    @Override
    public void sendSolicPermOffLine() {
        getView().showProgressbar("Espere...", "Enviando su solicitud al dispositivo");
        Log.e(TAG, "sendSolicPermOffLine");
        getCompositeDisposable().add(iteratorLocal.sendSolicPermOffLine(mSolicitudPermiso)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOffLine subscribe : " + response);
                    getView().viewMessageExitoso();

                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOffLine error : " + error);
                    Log.e(TAG, "sendSolicPermOffLine error : " + error.toString());
                    Log.e(TAG, "sendSolicPermOffLine error : " + error.getMessage());
                    Log.e(TAG, "sendSolicPermOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No se pudo enviar la solicitud.", error.getMessage());
                }));
    }

    @Override
    public void sendSolicPermOnLine() {
        getView().showProgressbar("Espere...", "Enviando su solicitud al servidor");
        Log.e(TAG, "sendSolicPermOnLine");
        getCompositeDisposable().add(iteratorRemote.sendSolicPermOnLine(mSolicitudPermiso)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getView().viewMessageExitoso();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOnLine error : " + error);
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No se pudo enviar la solicitud.", error.getMessage());
                    sendSolicPermOffLine();
                }));
    }

    @Override
    public boolean validarHora(String fechaIni, String fechaFin) {
        Log.e(TAG, "validarHora fechaIni: " + fechaIni + ", fechaFin: " + fechaFin);
        return DateUtils.ComprobarFechas(
                DateUtils.setDateFormat(fechaIni,
                        DateUtils.PATTERN_LECTURA,
                        DateUtils.PATTERN_DATE_RESULTADO),
                DateUtils.setDateFormat(fechaFin,
                        DateUtils.PATTERN_LECTURA,
                        DateUtils.PATTERN_DATE_RESULTADO));
    }

    @Override
    public int integracionVAWEB() {
        return preferenceManager.getConfig().getBitIntegracionVAWEB();
    }

    @Override
    public void setEditSolicPermOnLine(SolicitudPermiso solicitudPermiso) {
        getView().showProgressbar("Espere...", "Enviando su solicitud al servidor");
        Log.e(TAG, "sendSolicPermOnLine");
        getCompositeDisposable().add(iteratorRemote.sendSolicPermOnLine(solicitudPermiso)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getView().viewMessageExitoso();
                        } else {
                            getView().showErrorMessage(response.getVchMensaje(), response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendSolicPermOnLine error : " + error);
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.toString());
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.getMessage());
                    Log.e(TAG, "sendSolicPermOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No se pudo enviar la solicitud.", error.getMessage());
                    sendSolicPermOffLine();
                }));
    }

    @Override
    public int positionSuper(Spinner spinner, String codSuper) {
        int posicion = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            for (Supervisor data : mListSupervisor) {
                if (codSuper.equalsIgnoreCase(data.getVchCodigoPersonal())) {
                    if (spinner.getItemAtPosition(i).toString().
                            equalsIgnoreCase(data.getVchNombreApellido())) {
                        posicion = i;
                        break;
                    }
                }
            }
        }
        return posicion;
    }

    @Override
    public int positionSolicitud(Spinner spinner, String codSolicitud) {
        int posicion = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            for (Conceptos data : mListConceptos) {
                if (codSolicitud.equalsIgnoreCase(data.getVchCodConcepto())) {
                    if (spinner.getItemAtPosition(i).toString().
                            equalsIgnoreCase(data.getVchConcepto())) {
                        posicion = i;
                        break;
                    }
                }
            }
        }
        return posicion;
    }
}
