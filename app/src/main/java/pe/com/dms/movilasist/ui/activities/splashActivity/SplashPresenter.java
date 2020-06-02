package pe.com.dms.movilasist.ui.activities.splashActivity;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.annotacion.TypePerfil;
import pe.com.dms.movilasist.bd.entity.StatusSolicitud;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.iterator.splashActivity.local.ISplashInteractor;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;

public class SplashPresenter extends BasePresenter<ISplashContract.View>
        implements ISplashContract.Presenter {

    String TAG = SplashPresenter.class.getSimpleName();

    @Inject
    ISplashInteractor iteratorLocal;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;


    private CountDownTimer countDownTimer;
    private final long startTime = 2 * 1000;
    private final long interval = 1 * 1000;

    private List<Usuario> mListUser;
    private List<TypeMarcacion> mListTypeMarcacion;
    private List<StatusSolicitud> mListStatusSolicitud;

    @Inject
    public SplashPresenter() {
        if (mListUser == null)
            mListUser = new ArrayList<>();
        if (mListTypeMarcacion == null)
            mListTypeMarcacion = new ArrayList<>();
        if (mListStatusSolicitud == null)
            mListStatusSolicitud = new ArrayList<>();
    }

    public void createTypeMarcacion() {
        TypeMarcacion t1 = new TypeMarcacion(1, "Entrada");
        TypeMarcacion t2 = new TypeMarcacion(2, "Salida");
        TypeMarcacion t3 = new TypeMarcacion(3, "Salida de refrigerio");
        TypeMarcacion t4 = new TypeMarcacion(4, "Retorno de refrigerio");
        mListTypeMarcacion.add(t1);
        mListTypeMarcacion.add(t2);
        mListTypeMarcacion.add(t3);
        mListTypeMarcacion.add(t4);

        setListTypeMarcacionOffLine();
        createStatusPermiso();
    }

    private void setListTypeMarcacionOffLine() {
        getCompositeDisposable().add(iteratorLocal.insertTypeMarcacion(mListTypeMarcacion)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(resultado -> {
                    Log.e(TAG, "setListTypeMarcacionOffLine subscribe : " + resultado);
                }, error -> {
                    Log.e(TAG, "setListTypeMarcacionOffLine error : " + error);
                    Log.e(TAG, "setListTypeMarcacionOffLine error : " + error.toString());
                    Log.e(TAG, "setListTypeMarcacionOffLine error : " + error.getMessage());
                    Log.e(TAG, "setListTypeMarcacionOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta.", error.getMessage());
                }));
    }

    private void createStatusPermiso() {
        StatusSolicitud t1 = new StatusSolicitud(0, "Registrado");
        StatusSolicitud t2 = new StatusSolicitud(1, "Aprobado Parcial");
        StatusSolicitud t3 = new StatusSolicitud(2, "Aprobado");
        StatusSolicitud t4 = new StatusSolicitud(3, "Desaprobado");
        mListStatusSolicitud.add(t1);
        mListStatusSolicitud.add(t2);
        mListStatusSolicitud.add(t3);
        mListStatusSolicitud.add(t4);

        setListStatusPermisoOffLine();
    }

    private void setListStatusPermisoOffLine() {
        getCompositeDisposable().add(iteratorLocal.insertStatusPermiso(mListStatusSolicitud)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(resultado -> {
                    Log.e(TAG, "setListStatusPermisoOffLine subscribe : " + resultado);
                    iniciarContador();
                }, error -> {
                    Log.e(TAG, "setListStatusPermisoOffLine error : " + error);
                    Log.e(TAG, "setListStatusPermisoOffLine error : " + error.toString());
                    Log.e(TAG, "setListStatusPermisoOffLine error : " + error.getMessage());
                    Log.e(TAG, "setListStatusPermisoOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("Error al realizar la consulta.", error.getMessage());
                }));
    }

    public void iniciarContador() {
        countDownTimer = new CountDownTimerActivity(startTime, interval);
        countDownTimer.start();
    }

    public class CountDownTimerActivity extends CountDownTimer {
        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //Toast.makeText(Publicidad.this, ""+millisUntilFinished/1000+"",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFinish() {
            getView().moveToLogin();
        }
    }
}