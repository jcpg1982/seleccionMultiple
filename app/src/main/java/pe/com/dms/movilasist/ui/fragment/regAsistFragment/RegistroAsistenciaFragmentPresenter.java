package pe.com.dms.movilasist.ui.fragment.regAsistFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.StatusServer;
import pe.com.dms.movilasist.annotacion.TypeOption;
import pe.com.dms.movilasist.bd.PreferenceManager;
import pe.com.dms.movilasist.bd.entity.Marcacion;
import pe.com.dms.movilasist.bd.entity.Motivo;
import pe.com.dms.movilasist.bd.entity.TypeMarcacion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.iterator.regAsistFragment.local.IRegAsistFragmentInteractorLocal;
import pe.com.dms.movilasist.iterator.regAsistFragment.remote.IRegAsistFragmentInteractorRemote;
import pe.com.dms.movilasist.ui.activities.base.BasePresenter;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.util.DateUtils;
import pe.com.dms.movilasist.util.FileUtils;
import pe.com.dms.movilasist.util.ImageConverterUtils;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.Utils;

public class RegistroAsistenciaFragmentPresenter extends BasePresenter<IRegistroAsistenciaFragmentContract.View>
        implements IRegistroAsistenciaFragmentContract.Presenter {

    String TAG = RegistroAsistenciaFragmentPresenter.class.getSimpleName();

    @Inject
    IRegAsistFragmentInteractorLocal iteratorLocal;
    @Inject
    IRegAsistFragmentInteractorRemote iteratorRemote;
    @Inject
    @Named("executor_thread")
    Scheduler ExecutorThread;
    @Inject
    @Named("ui_thread")
    Scheduler UiThread;
    @Inject
    PreferenceManager preferenceManager;
    @Inject
    Context mContext;

    private Usuario mUser;
    private int mTypeMarc;
    private Marcacion mMarcacion;
    private Calendar calendar;
    private List<Motivo> mListMotivo;

    private List<TypeMarcacion> mListTypeMarc;

    @Inject
    public RegistroAsistenciaFragmentPresenter() {
        calendar = Calendar.getInstance(Locale.getDefault());
        mMarcacion = new Marcacion();
        mMarcacion.setStatusServer(StatusServer.PENDIENTE);
        mMarcacion.setDtmFechaMarca(DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_LECTURA));
        mMarcacion.setImgFoto("");
        if (mListMotivo == null)
            mListMotivo = new ArrayList<>();
        if (mListTypeMarc == null)
            mListTypeMarc = new ArrayList<>();
    }

    @Override
    public void validatedConnection(@TypeOption int typeOption) {
        switch (typeOption) {
            case TypeOption.SEND_MARC:
                getView().showProgressbar("Espere...", "Verificando la base de datos en el servidor");
                break;
        }
        Log.e(TAG, "validaConnection");
        getCompositeDisposable().add(iteratorRemote.validarConexion()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .doOnComplete(() -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : ");
                    getView().showErrorMessage("No hay conexion con la base de datos.", "");
                })
                .subscribe(response -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            switch (typeOption) {
                                /*case TypeOption.OBTAIN_CONFIG:
                                    obtainConfigOnLine();
                                    break;
                                case TypeOption.OBTAIN_USER:
                                    obtainUserOnLine();
                                    break;*/
                                case TypeOption.SEND_MARC:
                                    sendMarcOnLine();
                                    break;
                                /*case TypeOption.OBTAIN_MOTIVO:
                                    obtainMotivoOnLine();
                                    break;
                                case TypeOption.OBTAIN_TYPE_MARC:
                                    obtainTypeMarcacionOnLine();
                                    break;*/
                            }
                        } else {
                            getView().showErrorMessage("No hay conexion con la base de datos.", response.getVchMensaje());
                        }
                    }
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "validaConnection error : " + error);
                    Log.e(TAG, "validaConnection error : " + error.toString());
                    Log.e(TAG, "validaConnection error : " + error.getMessage());
                    Log.e(TAG, "validaConnection error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                    switch (typeOption) {
                        /*case TypeOption.OBTAIN_CONFIG:
                            obtainConfigOffLine();
                            break;
                        case TypeOption.OBTAIN_USER:
                            obtainUserOffLine();
                            break;*/
                        case TypeOption.SEND_MARC:
                            sendMarcOffLine();
                            break;
                        /*case TypeOption.OBTAIN_MOTIVO:
                            obtainMotivoOffLine();
                            break;
                        case TypeOption.OBTAIN_TYPE_MARC:
                            obtainTypeMarcacionOffLine();
                            break;*/
                    }
                }));
    }

    @Override
    public void obtainConfigOffLine() {
        Log.e(TAG, "obtainConfigOffLine");
        getCompositeDisposable().add(iteratorLocal.getConfigOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                /*.doOnComplete(() -> {
                    Log.e(TAG, "obtainConfigOffLine doOnComplete : ");
                    getView().viewMessageSinConfig();
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", "");
                })*/
                .subscribe(configuracion -> {
                    Log.e(TAG, "obtainConfigOffLine subscribe : " + configuracion);
                    if (configuracion != null) {
                        setIntIntegracionVAWEB(configuracion.getBitIntegracionVAWEB());
                        preferenceManager.setconfig(configuracion);
                        getView().fillData(configuracion);
                    } else {
                        getView().viewMessageSinConfig();
                    }
                }, error -> {
                    Log.e(TAG, "obtainConfigOffLine error : " + error);
                    Log.e(TAG, "obtainConfigOffLine error : " + error.toString());
                    Log.e(TAG, "obtainConfigOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainConfigOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                    getView().viewMessageSinConfig();
                }));
    }

    @Override
    public void obtainUserOffLine() {
        Log.e(TAG, "obtainUserOffLine");
        getCompositeDisposable().add(iteratorLocal.getUserOffLine(preferenceManager.getDocumentUser())
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                /*.doOnComplete(() -> {
                    Log.e(TAG, "obtainUserOffLine error : ");
                    getView().showErrorMessage("Usuario incorrecto verifique sus datos.", "");
                })*/
                .subscribe(user -> {
                    Log.e(TAG, "obtainUserOffLine subscribe : " + user);
                    if (user != null) {
                        mUser = user;
                        getView().mostrarInfoUser(mUser);
                        setVchCodPersonal(mUser.getVchCodigoPersonal());
                        preferenceManager.setUser(mUser);
                    }
                }, error -> {
                    Log.e(TAG, "obtainUserOffLine error : " + error);
                    Log.e(TAG, "obtainUserOffLine error : " + error.toString());
                    Log.e(TAG, "obtainUserOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainUserOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public void obtainMotivoOffLine() {
        Log.e(TAG, "obtainMotivoOffLine");
        getCompositeDisposable().add(iteratorLocal.getListMotivoOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(motivoList -> {
                    Log.e(TAG, "obtainMotivoOffLine subscribe : " + motivoList);
                    if (motivoList != null && motivoList.size() > 0) {
                        mListMotivo.clear();
                        mListMotivo.add(new Motivo("0", "Seleccione su motivo"));
                        for (Motivo item : motivoList) {
                            mListMotivo.add(item);
                        }
                        getView().llenarSpinerMotivo(mListMotivo);
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
    public List<Motivo> getListMotivo() {
        return mListMotivo;
    }

    @Override
    public void obtainTypeMarcacionOffLine() {
        Log.e(TAG, "obtainTypeMarcacionOffLine");
        getCompositeDisposable().add(iteratorLocal.getListTypeMarcacionOffLine()
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(typeMarcacionList -> {
                    Log.e(TAG, "obtainTypeMarcacionOffLine subscribe : " + typeMarcacionList);
                    if (typeMarcacionList != null && typeMarcacionList.size() > 0) {
                        mListTypeMarc.clear();
                        mListTypeMarc.add(new TypeMarcacion(0, "Seleccione tipo marcacion"));
                        for (TypeMarcacion item : typeMarcacionList) {
                            mListTypeMarc.add(item);
                        }
                        getView().llenarSpinerTypeMarc(mListTypeMarc);
                    }
                }, error -> {
                    Log.e(TAG, "obtainTypeMarcacionOffLine error : " + error);
                    Log.e(TAG, "obtainTypeMarcacionOffLine error : " + error.toString());
                    Log.e(TAG, "obtainTypeMarcacionOffLine error : " + error.getMessage());
                    Log.e(TAG, "obtainTypeMarcacionOffLine error : " + error.getLocalizedMessage());
                }));
    }

    @Override
    public List<TypeMarcacion> getListTypeMarc() {
        return mListTypeMarc;
    }

    @Override
    public void viewMessageActivarGps(Context context) {
        Log.e(TAG, "viewMessageActivarGps");
        new DefaultDialog.Builder(context)
                .title(context.getResources().getString(R.string.attention))
                .message(context.getResources().getString(R.string.gps_actived))
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(context.getResources().getString(R.string.button_actived_gps))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(context.getResources().getString(R.string.button_salir))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        getView().verifyGpsActivate();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                        getView().closed();
                    }
                })
                .buildAndShow(((AppCompatActivity) context).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_EXIT_APP);
    }

    @Override
    public void viewMessageHabilitarLocation(Context context) {
        Log.e(TAG, "viewMessageHabilitarLocation");
        new DefaultDialog.Builder(context)
                .title(context.getResources().getString(R.string.attention))
                .message(context.getResources().getString(R.string.location_permission))
                .cancelable(false)
                .setIcon(R.drawable.ic_help_outline)
                .textPositiveButton(context.getResources().getString(R.string.button_permission_location))
                .textColorPositiveButton(R.color.success)
                .textNegativeButton(context.getResources().getString(R.string.button_salir))
                .textColorNegativeButton(R.color.danger)
                .dialogType(DefaultDialog.DialogType.CONFIRM)
                .onPositive(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        getView().checkPermissionLocation();
                    }
                })
                .onNegative(new DefaultDialog.OnSingleButtonListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog) {
                        dialog.cancel();
                        getView().closed();
                    }
                })
                .buildAndShow(((AppCompatActivity) context).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_EXIT_APP);
    }

    @Override
    public void setEditable(EditText editText, boolean editable) {
        if (editable) {
            editText.setKeyListener(new TextKeyListener(TextKeyListener.Capitalize.WORDS, false));
            editText.requestFocus();
        } else {
            editText.setKeyListener(null);
        }
    }

    @Override
    public void saveImageInVariable(String path, EditText editText) {
        int correlativo = preferenceManager.getCorrelativo();
        String namePhoto;
        if (!TextUtils.isEmpty(String.valueOf(mTypeMarc))) {
            namePhoto = String.format("%s_%s_%s_%s",
                    mUser.getVchCodigoPersonal(),
                    DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_TAREO),
                    String.valueOf(mTypeMarc),
                    String.valueOf(correlativo));
        } else {
            namePhoto = String.format("%s_%s_%s",
                    mUser.getVchCodigoPersonal(),
                    DateUtils.dateToStringFormat(calendar.getTime(), DateUtils.PATTERN_DATE_TAREO),
                    String.valueOf(correlativo));
        }
        File file = ImageConverterUtils.decodeFile(mContext,
                path,
                1024,
                1024,
                namePhoto);
        if (!TextUtils.isEmpty(file.getPath())) {
            correlativo++;
            preferenceManager.setCorrelativo(correlativo);
            String nameView = FileUtils.getFileName(file.getPath(), false);
            editText.setText(nameView);
            setImgFoto(nameView);
        } else {
            Utils.showToast(mContext, "Vuelva a intentarlo");
        }
    }

    @Override
    public void setVchCodPersonal(String vchCodPersonal) {
        mMarcacion.setVchCodPersonal(vchCodPersonal);
    }

    @Override
    public void setIntTipoMotivo(int intTipoMotivo) {
        mMarcacion.setIntTipoMotivo(intTipoMotivo);
    }

    @Override
    public void setIntTipoMarca(int intTipoMarca) {
        Log.e(TAG, "setIntTipoMarca: " + intTipoMarca);
        mTypeMarc = intTipoMarca;
        mMarcacion.setIntTipoMarca(mTypeMarc);

    }

    @Override
    public void setVchCoordenadasLoc(String vchCoordenadasLoc) {
        mMarcacion.setVchCoordenadasLoc(vchCoordenadasLoc);
    }

    @Override
    public void setImgFoto(String imgFoto) {
        mMarcacion.setImgFoto(imgFoto);
    }

    @Override
    public void setVchLugarReferencia(String vchLugarReferencia) {
        mMarcacion.setVchLugarReferencia(vchLugarReferencia);
    }

    @Override
    public void setIntMarcacion(int intMarcacion) {
        mMarcacion.setIntMarcacion(intMarcacion);
    }

    @Override
    public void setIntIntegracionVAWEB(int IntIntegracionVAWEB) {
        mMarcacion.setIntIntegracionVAWEB(IntIntegracionVAWEB);
    }

    @Override
    public void sendMarcOffLine() {
        getView().showProgressbar("Espere...", "Enviando su Marcacion al dispositivo");
        Log.e(TAG, "validaConnection");
        String json = new Gson().toJson(mMarcacion);
        Log.e(TAG, "sendMarcOffLine json:" + json);
        getCompositeDisposable().add(iteratorLocal.sendMarcOffLine(mMarcacion)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "sendMarcOffLine subscribe : " + response);
                    getView().hiddenProgressbar();
                    getView().cleanViews();
                    getView().viewMessageExitoso();
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendMarcOffLine error : " + error);
                    Log.e(TAG, "sendMarcOffLine error : " + error.toString());
                    Log.e(TAG, "sendMarcOffLine error : " + error.getMessage());
                    Log.e(TAG, "sendMarcOffLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }

    @Override
    public void sendMarcOnLine() {
        getView().showProgressbar("Espere...", "Enviando su Marcacion al servidor");
        Log.e(TAG, "sendMarcOnLine");
        String json = new Gson().toJson(mMarcacion);
        Log.e(TAG, "sendMarcOffLine json:" + json);
        getCompositeDisposable().add(iteratorRemote.sendMarcOnLine(mMarcacion)
                .subscribeOn(ExecutorThread)
                .observeOn(UiThread)
                .subscribe(response -> {
                    Log.e(TAG, "sendMarcOnLine subscribe : " + response);
                    if (response != null) {
                        if (response.getIntValor() == 1) {
                            getView().cleanViews();
                            getView().viewMessageExitoso();
                        } else {
                            getView().showErrorMessage("No hay conexion con la base de datos.", response.getVchMensaje());
                        }
                    }
                    getView().hiddenProgressbar();
                }, error -> {
                    getView().hiddenProgressbar();
                    Log.e(TAG, "sendMarcOnLine error : " + error);
                    Log.e(TAG, "sendMarcOnLine error : " + error.toString());
                    Log.e(TAG, "sendMarcOnLine error : " + error.getMessage());
                    Log.e(TAG, "sendMarcOnLine error : " + error.getLocalizedMessage());
                    getView().showErrorMessage("No hay conexion con la base de datos.", error.getMessage());
                }));
    }
}