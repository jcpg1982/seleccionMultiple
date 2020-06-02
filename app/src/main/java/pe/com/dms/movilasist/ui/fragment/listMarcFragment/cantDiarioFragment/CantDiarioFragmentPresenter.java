package pe.com.dms.movilasist.ui.fragment.listMarcFragment.cantDiarioFragment;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.TypeGraphics;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.iterator.listMarcFragment.local.IListMarcFragmentIteratorLocal;
import pe.com.dms.movilasist.iterator.listMarcFragment.remote.IListMarcFragmentIteratorRemote;
import pe.com.dms.movilasist.iterator.loginFragment.local.ILoginFragmentInteractorLocal;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.model.request.RequestListMarcGraphics;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.util.DateUtils;

public class CantDiarioFragmentPresenter extends BasePresenter<ICantDiarioFragmentContract.View>
        implements ICantDiarioFragmentContract.Presenter {

    String TAG = CantDiarioFragmentPresenter.class.getSimpleName();

    @Inject
    IListMarcFragmentIteratorLocal iteratorLocal;
    @Inject
    IListMarcFragmentIteratorRemote iteratorRemote;

    @Inject
    ILoginFragmentInteractorLocal interactor;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    Context context;
    @Inject
    PreferenceManager preferenceManager;

    private RequestListMarcGraphics mRequestListMarcGraphics;
    private Calendar calendar;

    @Inject
    public CantDiarioFragmentPresenter() {
        mRequestListMarcGraphics = new RequestListMarcGraphics();
        calendar = Calendar.getInstance(Locale.getDefault());
    }

    @Override
    public void setModelListMarc(ResultListMarc resultListMarc) {

        mRequestListMarcGraphics.setIntIntegracionVAWEB(preferenceManager.getConfig().getBitIntegracionVAWEB());
        mRequestListMarcGraphics.setIntMostrarMarca(preferenceManager.getConfig().getIntMostrarMarca());
        mRequestListMarcGraphics.setVchCodPersonal(preferenceManager.getUser().getVchCodigoPersonal());
        mRequestListMarcGraphics.setIntTipo(TypeGraphics.DIARIO);

        mRequestListMarcGraphics.setDtmFechaInicio(resultListMarc.getDateIni());
        mRequestListMarcGraphics.setDtmFechaFin(resultListMarc.getDateFin());
        validateConnection();
    }

/*    @Override
    public String dayWeek(String date) {
        Log.e(TAG, "dayWeek date: " + date);
        try {
            String tempDate = DateUtils.setDateFormat(date,
                    DateUtils.PATTERN_LECTURA_1,
                    DateUtils.PATTERN_DATE_RESULTADO);
            Log.e(TAG, "dayWeek tempDate: " + tempDate);
            Date dateTime = DateUtils.fromStringToDate(tempDate,
                    new SimpleDateFormat(DateUtils.PATTERN_DATE_RESULTADO));
            Log.e(TAG, "dayWeek dateTime: " + dateTime);
            calendar.setTime(dateTime);
            Log.e(TAG, "dayWeek calendar: " + calendar.getTime());
            return DateUtils.getDisplayDay(calendar.getTimeInMillis(), context);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    @Override
    public void validateConnection() {
        Log.e(TAG, "validateConnection");
        getCompositeDisposable().add(iteratorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "validateConnection error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "validateConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            obtainMarcGraphicsOnLine();
                        }
                    }
                }, error -> {
                    Log.e(TAG, "validateConnection error : " + error);
                    Log.e(TAG, "validateConnection error : " + error.toString());
                    Log.e(TAG, "validateConnection error : " + error.getMessage());
                    Log.e(TAG, "validateConnection error : " + error.getLocalizedMessage());
                    obtainMarcGraphicsOffLine();
                }));
    }

    @Override
    public void obtainMarcGraphicsOffLine() {

    }

    @Override
    public void obtainMarcGraphicsOnLine() {
        Log.e(TAG, "obtainMarcGraphicsOnLine");
        getCompositeDisposable().add(iteratorRemote.getListMarcCantDiariaOnLine(mRequestListMarcGraphics)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    Log.e(TAG, "obtainMarcGraphicsOnLine error : ");
                })
                .subscribe(response -> {
                    Log.e(TAG, "obtainMarcGraphicsOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().getIntValor() == 1) {
                            if (response.getMarca() != null && response.getMarca().size() > 0) {
                                getView().viewListGraphic(response.getMarca());
                            } else {
                                getView().viewListGraphic(null);
                                getView().showErrorMessage(response.getMensaje().getVchMensaje(), response.getMensaje().getVchMensaje());
                            }
                        }
                    }
                }, error -> {
                    Log.e(TAG, "obtainMarcGraphicsOnLine error : " + error);
                    Log.e(TAG, "obtainMarcGraphicsOnLine error : " + error.toString());
                    Log.e(TAG, "obtainMarcGraphicsOnLine error : " + error.getMessage());
                    Log.e(TAG, "obtainMarcGraphicsOnLine error : " + error.getLocalizedMessage());
                }));
    }
}
