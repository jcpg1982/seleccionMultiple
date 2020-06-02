package pe.com.dms.movilasist.helpers;

import pe.com.dms.movilasist.R;

public class Constants {

    //Extra [EXTRAS]
    public static final String MARKET_URL_X = "market://details?id=%s";
    public static final String GOOGLE_PLAY_URL_X = "https://play.google.com/store/apps/details?id=%s";

    public static final String URL_SECURITY = "http://";
    public static final String DEFAULT_URL = "172.16.3.15/ApiAsistenciaMovil/AsistenciaMovil/api/";

    //Preferences All [SHARED PREFERENCES]
    public static final String PREF_INIT = "init_user";
    public static final String PREF_INIT_USER = "init_user_super";
    public static final String PREF_INIT_USER_SYSTEM = "init_user_system";

    //TAG DIALOGS
    public static final String TAG_DIALOG_CONFIRM_LOGOUT = "pe.com.dms.extra.dialog.tag.DIALOG_CONFIRM_LOGOUT";
    public static final String TAG_DIALOG_EXIT_APP = "pe.com.dms.extra.dialog.tag.DIALOG_EXIT_APP";
    public static final String TAG_DIALOG_SHOW_ERROR = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_SHOW_ERROR";
    public static final String TAG_DIALOG_NOT_GOOGLE_PLAY_SERVICES = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_NOT_GOOGLE_PLAY_SERVICES";
    public static final String TAG_DIALOG_SAVE_CONFIG = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_SAVE_CONFIG";
    public static final String TAG_DIALOG_DELETE_MARC = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_DELETE_MARC";
    public static final String TAG_DIALOG_SAVE_SOLIC_PERM_EXITOSO = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_SAVE_SOLIC_PERM_EXITOSO";
    public static final String TAG_DIALOG_SHOW_TYPE_MARCACION = "pe.com.dms.extra.dialog.tag.TAG_DIALOG_SHOW_TYPE_MARCACION";

    //Directories [MAIN]
    public static final String PATH_FOLDER_APP = "/" + R.string.app_name;
    public static final String PATH_FOLDER_APP_MEDIA = "/Media";
    public static final String PATH_FOLDER_APP_MEDIA_TEXT = PATH_FOLDER_APP_MEDIA + "" + PATH_FOLDER_APP + " Text";
    public static final String PATH_FOLDER_APP_MEDIA_TEXT_SENT = PATH_FOLDER_APP_MEDIA_TEXT + "/Sent";
    public static final String PATH_FOLDER_APP_MEDIA_IMAGES = PATH_FOLDER_APP_MEDIA + "" + PATH_FOLDER_APP + " Images";
    public static final String PATH_FOLDER_APP_MEDIA_IMAGES_SENT = PATH_FOLDER_APP_MEDIA_IMAGES + "/Sent";
    public static final String PATH_FOLDER_APP_MEDIA_VIDEO = PATH_FOLDER_APP_MEDIA + "" + PATH_FOLDER_APP + " Video";
    public static final String PATH_FOLDER_APP_MEDIA_VIDEO_SENT = PATH_FOLDER_APP_MEDIA_VIDEO + "/Sent";
    public static final String PATH_FOLDER_APP_MEDIA_AUDIO = PATH_FOLDER_APP_MEDIA + "" + PATH_FOLDER_APP + " Audio";
    public static final String PATH_FOLDER_APP_MEDIA_AUDIO_SENT = PATH_FOLDER_APP_MEDIA_AUDIO + "/Sent";
    public static final String PATH_FOLDER_APP_MEDIA_DOCUMENTS = PATH_FOLDER_APP_MEDIA + "" + PATH_FOLDER_APP + " Documents";
    public static final String PATH_FOLDER_APP_MEDIA_DOCUMENTS_SENT = PATH_FOLDER_APP_MEDIA_DOCUMENTS + "/Sent";
    public static final String PATH_FOLDER_CACHE = "manager_disk_cache";

    /**
     * Atributos para preference
     */
    public static final class Preferences {
        public static final String PREF_GEN_WEBSERVICE = "GEN_WebService";
        public static final String PREF_TYPE_PROFILE = "pref_type_profile";
        public static final String PREF_USER = "pref_user";
        public static final String PREF_CONFIG = "pref_config";
        public static final String PREF_DOCUMENT_USER = "pref_document_user";
        public static final String PREF_ID_TERMINAL = "pref_id_terminal";
        public static final String PREF_ID_CORRELATIVO = "pref_id_correlativo";
        public static final String PREF_HAVE_DATA_SEND = "pref_have_data_send";
    }

    /**
     * Atributos para Intent
     */
    public static final class Intent {
        //Actions for Intents [EXTRA INTENT]
        public static final String EXTRA_CONNECTED = "pe.com.dms.extra.EXTRA_CONNECTED";
        public static final String EXTRA_POSITION_DRAWER = "pe.com.dms.extra.EXTRA_POSITION_DRAWER";
        public static final String EXTRA_FILE_PICKER = "pe.com.dms.extra.EXTRA_FILE_PICKER";
        public static final String EXTRA_SELECTOR_TYPE = "pe.com.dms.extra.EXTRA_SELECTOR_TYPE";
        public static final String EXTRA_TITLE = "pe.com.dms.extra.EXTRA_TITLE";
        public static final String EXTRA_SUB_TITLE = "pe.com.dms.extra.EXTRA_SUB_TITLE";
        public static final String EXTRA_SELECTOR_FILTER = "pe.com.dms.extra.EXTRA_SELECTOR_FILTER";
        public static final String EXTRA_CODE_TYPE = "pe.com.dms.extra.EXTRA_CODE_TYPE";
        public static final String EXTRA_INT_ACTIVITY = "pe.com.dms.extra.EXTRA_INT_ACTIVITY";
        public static final String EXTRA_SOLICITUD_PERMISO = "pe.com.dms.extra.EXTRA_SOLICITUD_PERMISO";
        public static final String EXTRA_OPTION_TYPE_VIEW = "pe.com.dms.extra.EXTRA_OPTION_TYPE_VIEW";


        //REQUEST CODE FOR ACTIVITYFORRESULT
        public static final int REQUEST_CODE_LIST_MARC = 100;
        public static final int REQUEST_CODE_LIST_MARC_PERS = 101;
        public static final int REQUEST_CODE_LIST_SOLIC_PERM = 102;
        public static final int REQUEST_CODE_LIST_APROB_SOLIC = 103;
        public static final int REQUEST_CODE_IMAGE_CAMERA = 104;
        public static final int REQUEST_CODE_ENABLED_GPS = 105;

    }
}
