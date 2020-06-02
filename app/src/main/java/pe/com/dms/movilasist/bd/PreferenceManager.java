package pe.com.dms.movilasist.bd;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import pe.com.dms.movilasist.annotacion.TypePerfil;
import pe.com.dms.movilasist.bd.entity.Configuracion;
import pe.com.dms.movilasist.bd.entity.Usuario;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.injection.scope.ApplicationScope;

@ApplicationScope
public class PreferenceManager {

    String TAG = PreferenceManager.class.getSimpleName();

    public SharedPreferences mPreferences;
    public SharedPreferences.Editor mEditor;

    @Inject
    public PreferenceManager(@ApplicationScope Context context) {
        mPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public String getWebService() {
        Log.e(TAG, "getWebService: " + mPreferences.getString(Constants.Preferences.PREF_GEN_WEBSERVICE, Constants.DEFAULT_URL));
        return mPreferences.getString(Constants.Preferences.PREF_GEN_WEBSERVICE, Constants.DEFAULT_URL);
    }

    public void setWebService(String url) {
        Log.e(TAG, "setWebService url: " + url);
        if (!url.substring(url.length() - 1).equals("/")) {
            url += "/";
        }
        mEditor.putString(Constants.Preferences.PREF_GEN_WEBSERVICE, url).commit();
    }

    public String getidTerminal() {
        Log.e(TAG, "getidTerminal: " + mPreferences.getString(Constants.Preferences.PREF_ID_TERMINAL, null));
        return mPreferences.getString(Constants.Preferences.PREF_ID_TERMINAL, null);
    }

    public void setIdTerminal(String idTerminal) {
        Log.e(TAG, "setIdTerminal idTerminal: " + idTerminal);
        mEditor.putString(Constants.Preferences.PREF_ID_TERMINAL, idTerminal).commit();
    }

    public int getTypeUser() {
        Log.e(TAG, "getTypeUser: " + mPreferences.getInt(Constants.Preferences.PREF_TYPE_PROFILE, 0));
        return mPreferences.getInt(Constants.Preferences.PREF_TYPE_PROFILE, 0);
    }

    public void setTypeUser(@TypePerfil int value) {
        Log.e(TAG, "setTypeUser value: " + value);
        mEditor.putInt(Constants.Preferences.PREF_TYPE_PROFILE, value).commit();
    }

    public String getDocumentUser() {
        Log.e(TAG, "getTypeUser: " + mPreferences.getString(Constants.Preferences.PREF_DOCUMENT_USER, ""));
        return mPreferences.getString(Constants.Preferences.PREF_DOCUMENT_USER, "");
    }

    public void setDocumentUser(String value) {
        Log.e(TAG, "setTypeUser value: " + value);
        mEditor.putString(Constants.Preferences.PREF_DOCUMENT_USER, value).commit();
    }

    public int getCorrelativo() {
        Log.e(TAG, "getCorrelativo: " + mPreferences.getInt(Constants.Preferences.PREF_ID_CORRELATIVO, 0));
        return mPreferences.getInt(Constants.Preferences.PREF_ID_CORRELATIVO, 0);
    }

    public void setCorrelativo(int value) {
        Log.e(TAG, "setCorrelativo value: " + value);
        mEditor.putInt(Constants.Preferences.PREF_ID_CORRELATIVO, value).commit();
    }

    public Usuario getUser() {
        Gson gson = new Gson();
        String json = mPreferences.getString(Constants.Preferences.PREF_USER, "");
        if (json.equals("")) {
            setUser(null);
            json = mPreferences.getString(Constants.Preferences.PREF_USER, null);
        }
        return gson.fromJson(json, Usuario.class);
    }

    public void setUser(Usuario user) {
        Log.e(TAG, "setUser value: " + user);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Preferences.PREF_USER, json);
        mEditor.apply();
    }

    public Configuracion getConfig() {
        Gson gson = new Gson();
        String json = mPreferences.getString(Constants.Preferences.PREF_CONFIG, "");
        if (json.equals("")) {
            setUser(null);
            json = mPreferences.getString(Constants.Preferences.PREF_CONFIG, null);
        }
        Log.e(TAG, "getConfig: " + gson.fromJson(json, Configuracion.class));
        return gson.fromJson(json, Configuracion.class);
    }

    public void setconfig(Configuracion configuracion) {
        Log.e(TAG, "setConfig value: " + configuracion);
        Gson gson = new Gson();
        String json = gson.toJson(configuracion);
        mEditor = mPreferences.edit();
        mEditor.putString(Constants.Preferences.PREF_CONFIG, json);
        mEditor.apply();
    }

    public boolean getHaveDataSend() {
        Log.e(TAG, "getHaveDataSend: " + mPreferences.getBoolean(Constants.Preferences.PREF_HAVE_DATA_SEND, false));
        return mPreferences.getBoolean(Constants.Preferences.PREF_HAVE_DATA_SEND, false);
    }

    public void setHaveDataSend(boolean value) {
        Log.e(TAG, "setHaveDataSend value: " + value);
        mEditor.putBoolean(Constants.Preferences.PREF_HAVE_DATA_SEND, value).commit();
    }

    /*public String getDiasVigencia() {
        String diasVigencia = "";
        if (prefs.contains(Constants.PREF_GEN_DIAS_VIGENCIA))
            diasVigencia = prefs.getString(Constants.PREF_GEN_DIAS_VIGENCIA, "");
        return diasVigencia;
    }*/

    /*public void setDiasVigencia(String diasVigencia) {
        editor.putString(Constants.PREF_GEN_DIAS_VIGENCIA, diasVigencia).commit();
    }*/

    /*public String getFechaHoraServidor() {
        return prefs.getString(Constants.PREF_GEN_FECHA_SERVIDOR, Constants.sinFecha);
    }

    public void setFechaHoraServidor(String fechaHora) {
        editor.putString(Constants.PREF_GEN_FECHA_SERVIDOR, fechaHora).commit();
    }*/

    /*  Datos de usuario */
    /*public int getUsuario() {
        return prefs.getInt(Constants.PREF_COD_USUARIO, 0);
    }

    public void setUsuario(int value) {
        editor.putInt(Constants.PREF_COD_USUARIO, value).commit();
    }

    public String getNombreUsuario() {
        return prefs.getString(Constants.PREF_NOMBRE_USUARIO, "");
    }

    public void setNombreUsuario(String value) {
        editor.putString(Constants.PREF_NOMBRE_USUARIO, value.toUpperCase()).commit();
    }

    public String getNombrePerfil() {
        return prefs.getString(Constants.PREF_PERFIL_USUARIO, "Sin Perfil");
    }

    public void setNombrePerfil(String value) {
        editor.putString(Constants.PREF_PERFIL_USUARIO, value).commit();
    }

    /* Configuracion: General*/

    /*public boolean getFlgErrores() {
        return prefs.getBoolean(Constants.PREFS_GEN_ERRORES, false);
    }

    public void setFlgErrores(boolean value) {
        editor.putBoolean(Constants.PREFS_GEN_ERRORES, value).commit();
    }

    public int getModoTrabajo() {
        return prefs.getInt(Constants.PREFS_GEN_MODO_TRABAJO, ModoTrabajo.BATCH);
    }

    public void setModoTrabajo(@ModoTrabajo int value) {
        editor.putInt(Constants.PREFS_GEN_MODO_TRABAJO, value).commit();
    }

    public int getCantDiasFechaIniTareo() {
        return prefs.getInt(Constants.PREFS_CANT_DIAS_INICIO, 1);
    }

    public int getMaximoMargen() {
        return prefs.getInt(Constants.PREFS_GEN_MAXIMA_DIFERENCIA, 15);
    }

    public void setMaximoMargen(int value) {
        editor.putInt(Constants.PREFS_GEN_MAXIMA_DIFERENCIA, value).commit();
    }

    public int getTimeOut() {
        return prefs.getInt(Constants.PREFS_GEN_TIMEOUT, 5);
    }

    public void setTimeOut(int value) {
        editor.putInt(Constants.PREFS_GEN_TIMEOUT, value).commit();
    }

    *//* Configuracion: Tareo*//*
    public int getTareoUnidadMedida() {
        return prefs.getInt(Constants.PREFS_UNIDAD_MEDIDA, 0);
    }

    public void setTareoUnidadMedida(int value) {
        editor.putInt(Constants.PREFS_UNIDAD_MEDIDA, value).commit();
    }

    public int getTurno() {
        return prefs.getInt(Constants.PREFS_TURNO, 0);
    }

    public void setTurno(int value) {
        editor.putInt(Constants.PREFS_TURNO, value).commit();
    }

    public int getClaseTareo() {
        return prefs.getInt(Constants.PREFS_CLASE_TAREO, 0);
    }

    public void setClaseTareo(int value) {
        editor.putInt(Constants.PREFS_CLASE_TAREO, value).commit();
    }

    *//* Configuracion: Ajustes*//*
    public boolean getAjustesUnidadMedida() {
        return prefs.getBoolean(Constants.PREFS_AJUSTES_UM, false);
    }

    public void setAjustesUnidadMedida(boolean value) {
        editor.putBoolean(Constants.PREFS_AJUSTES_UM, value).commit();
    }

    public boolean getFechaHoraInicioManual() {
        return prefs.getBoolean(Constants.PREFS_FECHA_INICIO, false);
    }

    public void setFechaHoraInicioManual(boolean value) {
        editor.putBoolean(Constants.PREFS_FECHA_INICIO, value).commit();
    }

    public boolean getFechaHoraFinManual() {
        return prefs.getBoolean(Constants.PREFS_FECHA_FIN, false);
    }

    public void setFechaHoraFinManual(boolean value) {
        editor.putBoolean(Constants.PREFS_FECHA_FIN, value).commit();
    }

    public boolean getVigenciaDescarga() {
        return prefs.getBoolean(Constants.PREFS_VIGENCIA, false);
    }

    public void setVigenciaDescarga(boolean value) {
        editor.putBoolean(Constants.PREFS_VIGENCIA, value).commit();
    }

    public boolean getRegistrarPersona() {
        return prefs.getBoolean(Constants.PREFS_REGISTRAR_PERSONA, false);
    }

    public void setRegistrarPersona(boolean value) {
        editor.putBoolean(Constants.PREFS_REGISTRAR_PERSONA, value).commit();
    }

   *//* public int getPerfilAdmin() {
        return prefs.getInt(Constants.PREF_COD_ADMIN, 1);
    }*//*


     *//*  Flags Validaciones Internas *//*
    public Boolean getNuevoServicio() {
        return prefs.getBoolean(Constants.PREFS_FLG_NUEVAIP, false);
    }

    public void setNuevoServicio(boolean value) {
        editor.putBoolean(Constants.PREFS_FLG_NUEVAIP, value).commit();
    }

    public Boolean getServicioValidado() {
        return prefs.getBoolean(Constants.PREFS_FLG_VALIDARWS, false);
    }

    public void setServicioValidado(boolean value) {
        editor.putBoolean(Constants.PREFS_FLG_VALIDARWS, value).commit();
    }

    public Boolean getSonidoLectura() {
        return prefs.getBoolean(Constants.PREFS_GEN_SOUND, false);
    }

    public void setSonidoLectura(boolean value) {
        editor.putBoolean(Constants.PREFS_GEN_SOUND, value).commit();
    }

    public int getDuracionRefrigerio() {
        return prefs.getInt(Constants.PREF_DURACION_REFRIGERIO, getMinimoRefrigerio());
    }

    public void setDuracionRefrigerio(int duracioRefrigerio) {
        editor.putInt(Constants.PREF_DURACION_REFRIGERIO, duracioRefrigerio).commit();
    }

    *//* Otros *//*
    public ContenidoAjustes getContenidoAjustes() {
        ContenidoAjustes campos = new ContenidoAjustes();
        campos.setUnidadMedida(getAjustesUnidadMedida());
        campos.setFechaHoraInicio(getFechaHoraInicioManual());
        campos.setFechaHoraFin(getFechaHoraFinManual());
        campos.setVigenciaDescarga(getVigenciaDescarga());
        campos.setRegistrarEmpleado(getRegistrarPersona());
        return campos;
    }

    public ContenidoGeneral getContenidoGeneral() {
        ContenidoGeneral campos = new ContenidoGeneral();
        campos.setErroresDetallados(getFlgErrores());
        campos.setUltimaFechaHora(getFechaHoraServidor());
        campos.setUrlServicioWeb(getWebService());
        campos.setMargenDiferencia(getMaximoMargen());
        campos.setTimeOut(getTimeOut());
        campos.setModoTrabajo(getModoTrabajo());
        campos.setSound(getSonidoLectura());
        campos.setDuracionRefrigerio(getDuracionRefrigerio());
        return campos;
    }

    public ContenidoTareo getContenidoTareo() {
        ContenidoTareo campos = new ContenidoTareo();
        campos.setUnidadMedida(getTareoUnidadMedida());
        campos.setUnidadMedida(getClaseTareo());
        campos.setUnidadMedida(getTurno());
        return campos;
    }

    public int getLimiteControles() {
        return 5;// por defecto
    }

    public int getMinimoRefrigerio() { //Minutos
        return 45;// por defecto
    }

    public int getMaximoRefrigerio() { //Minutos
        return 120;// 2h por defecto
    }

    *//* TIME MANAGER*//*

    public Long getMargenTiempo() {
        return prefs.getLong(Constants.PREFS_TM_MARGEN_TIEMPO, 0L);
    }

    public void setMargenTiempo(long value) {
        editor.putLong(Constants.PREFS_TM_MARGEN_TIEMPO, value).commit();
    }

    public void setCambioManualFechaHora(boolean value) {
        editor.putBoolean(Constants.PREFS_TM_CAMBIO_MANUAL, value).commit();
    }

    public Boolean getCambioManualFechaHora() {
        return prefs.getBoolean(Constants.PREFS_TM_CAMBIO_MANUAL, false);
    }

    public void setFechaDesfasada(boolean value) {
        editor.putBoolean(Constants.PREFS_TM_FECHA_DESFASADA, value).commit();
    }

    public Boolean getFechaDesfasada() {
        return prefs.getBoolean(Constants.PREFS_TM_FECHA_DESFASADA, false);
    }

    public void setTiempoValido(boolean value) {
        editor.putBoolean(Constants.PREFS_TM_TIEMPO_VALIDADO, value).commit();
    }

    public Boolean getTiempoValido() {
        return prefs.getBoolean(Constants.PREFS_TM_TIEMPO_VALIDADO, false);
    }

    public void setFechaUltimaValidacion(String value) {
        editor.putString(Constants.PREFS_TM_ULIIMO_INTENTO, value).commit();
    }

    public String getFechaUltimaValidacion() {
        return prefs.getString(Constants.PREFS_TM_ULIIMO_INTENTO, "");
    }

    // UtilsMethods
    public void removeUser() {
        setPerfilUsuario(0);
        setUsuario(0);
        setNombrePerfil("");
        setNombreUsuario("");
    }

    public int getCodigoEnvioUsuario() {
        int usuario = getUsuario();
        //Si el usuario es admin puede ver todo_ los tareos
        if (isAdmin()) {
            usuario = 0; // 0 es comodin en la ws para listar todos
        }
        return usuario;
    }

    public boolean isAdmin() {
        Log.e(TAG, "isAdmin: " + getPerfilUsuario());
        *//*switch (getPerfilUsuario()) {
            case TypeUser.ADMIN:
                return true;
            case TypeUser.COMUN:
            default:
                return false;
        }*//*
        return false;
    }*/
}
