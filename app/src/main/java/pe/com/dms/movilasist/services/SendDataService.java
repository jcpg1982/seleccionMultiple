package pe.com.dms.movilasist.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import pe.com.dms.movilasist.Application;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.SolicitudPermiso;
import pe.com.dms.movilasist.bd.repository.DataSourceRepository;
import pe.com.dms.movilasist.model.ListResultadoMarcacion;
import pe.com.dms.movilasist.model.ListResultadoPermisos;

public class SendDataService extends Service {

    String TAG = SendDataService.class.getSimpleName();

    @Inject
    DataSourceRepository dataSourceRepository;

    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Context context;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;

    private CompositeDisposable disposables = new CompositeDisposable();

    private List<SolicitudPermiso> listSolicitudSend;
    private List<Marcacion> listMarcacionSend;


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        ((Application) getApplication()).getAppComponent().inject(this);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.e(TAG, "Se logro iniciar el servicio");
        sendMessageToActivity(10);
        verifyIfHaveMarcaciones();
        verifyIfHavePermisos();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        sendMessageToActivity(12);
        disposables.clear();
        Intent intent = new Intent("pe.com.dms.movilasist");
        sendBroadcast(intent);
    }

    public void verifyIfHaveMarcaciones() {
        Log.e(TAG, "verifyIfHaveMarcaciones : ");
        disposables.add(dataSourceRepository.getAllMarcacionesOffLine(StatusServer.PENDIENTE)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "verifyIfHaveMarcaciones subscribe : " + list);
                    if (list != null && list.size() > 0) {
                        sendListAllMarcacionesOnLine(list);
                    }
                }, error -> {
                    Log.e(TAG, "verifyIfHaveMarcaciones error : " + error);
                    Log.e(TAG, "verifyIfHaveMarcaciones error : " + error.toString());
                    Log.e(TAG, "verifyIfHaveMarcaciones error : " + error.getMessage());
                    Log.e(TAG, "verifyIfHaveMarcaciones error : " + error.getLocalizedMessage());
                }));
    }

    private void sendListAllMarcacionesOnLine(List<Marcacion> listMarc) {
        Log.e(TAG, "sendListAllMarcacionesOnLine : ");
        disposables.add(dataSourceRepository.cargaSetMarcOnLine(listMarc)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "sendListAllMarcacionesOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().equals("1")) {
                            List<Integer> listEnviados = new ArrayList<>();
                            for (ListResultadoMarcacion item : response.getLstResultado()) {
                                if (item.getIntValor() == 1) {
                                    listEnviados.add(item.getIntIdmMarcaInt());
                                }
                            }
                            if (listEnviados.size() > 0)
                                deleteMarcacionesEnviadas(listEnviados);
                        }
                    }
                }, error -> {
                    Log.e(TAG, "sendListAllMarcacionesOnLine error : " + error);
                    Log.e(TAG, "sendListAllMarcacionesOnLine error : " + error.toString());
                    Log.e(TAG, "sendListAllMarcacionesOnLine error : " + error.getMessage());
                    Log.e(TAG, "sendListAllMarcacionesOnLine error : " + error.getLocalizedMessage());
                }));
    }

    private void deleteMarcacionesEnviadas(List<Integer> listCodMarcacionesEnviadas) {
        Log.e(TAG, "deleteMarcacionesEnviadas : ");
        disposables.add(dataSourceRepository.deleteMarcacionesEnviadas(listCodMarcacionesEnviadas)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "deleteMarcacionesEnviadas subscribe : " + list);
                }, error -> {
                    Log.e(TAG, "deleteMarcacionesEnviadas error : " + error);
                    Log.e(TAG, "deleteMarcacionesEnviadas error : " + error.toString());
                    Log.e(TAG, "deleteMarcacionesEnviadas error : " + error.getMessage());
                    Log.e(TAG, "deleteMarcacionesEnviadas error : " + error.getLocalizedMessage());
                }));
    }

    public void verifyIfHavePermisos() {
        Log.e(TAG, "verifyIfHavePermisos : ");
        disposables.add(dataSourceRepository.getAllSolicitudPermisoOffLine(StatusServer.PENDIENTE)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "verifyIfHavePermisos subscribe : " + list);
                    if (list != null && list.size() > 0) {
                        sendListAllPermisosOnLine(list);
                    }
                }, error -> {
                    Log.e(TAG, "verifyIfHavePermisos error : " + error);
                    Log.e(TAG, "verifyIfHavePermisos error : " + error.toString());
                    Log.e(TAG, "verifyIfHavePermisos error : " + error.getMessage());
                    Log.e(TAG, "verifyIfHavePermisos error : " + error.getLocalizedMessage());
                }));
    }

    private void sendListAllPermisosOnLine(List<SolicitudPermiso> listPerm) {
        Log.e(TAG, "sendListAllPermisosOnLine : ");
        disposables.add(dataSourceRepository.cargaSetSolicPermOnLine(listPerm)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "sendListAllMarcacionesOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getMensaje().equals("1")) {
                            List<Integer> listEnviados = new ArrayList<>();
                            for (ListResultadoPermisos item : response.getLstResultado()) {
                                if (item.getIntValor() == 1) {
                                    listEnviados.add(item.getIntIdmSolicitudInt());
                                }
                            }
                            if (listEnviados.size() > 0)
                                deleteSolicitudesEnviadas(listEnviados);
                        }
                    }
                }, error -> {
                    Log.e(TAG, "sendListAllPermisosOnLine error : " + error);
                    Log.e(TAG, "sendListAllPermisosOnLine error : " + error.toString());
                    Log.e(TAG, "sendListAllPermisosOnLine error : " + error.getMessage());
                    Log.e(TAG, "sendListAllPermisosOnLine error : " + error.getLocalizedMessage());
                }));
    }

    private void deleteSolicitudesEnviadas(List<Integer> listCodSolicitudesEnviadas) {
        Log.e(TAG, "deleteSolicitudesEnviadas : ");
        disposables.add(dataSourceRepository.deletePermisosEnviados(listCodSolicitudesEnviadas)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(list -> {
                    Log.e(TAG, "deleteSolicitudesEnviadas subscribe : " + list);
                }, error -> {
                    Log.e(TAG, "deleteSolicitudesEnviadas error : " + error);
                    Log.e(TAG, "deleteSolicitudesEnviadas error : " + error.toString());
                    Log.e(TAG, "deleteSolicitudesEnviadas error : " + error.getMessage());
                    Log.e(TAG, "deleteSolicitudesEnviadas error : " + error.getLocalizedMessage());
                }));
    }


    /**
     * Envía un aviso cuando el servicio ha finalizado
     *
     * @param action es la acción a ejecutar
     */
    private void sendMessageToActivity(int action) {
        Intent intent = new Intent("sentKey");
        // You can also include some extra data.
        intent.putExtra("action", action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
