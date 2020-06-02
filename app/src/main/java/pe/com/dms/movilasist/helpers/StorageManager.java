package pe.com.dms.movilasist.helpers;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.util.FileUtils;

public class StorageManager {

    static String TAG = StorageManager.class.getSimpleName();

    @IntDef({FolderMediaType.TEXT, FolderMediaType.TEXT_SENT,
            FolderMediaType.IMAGE, FolderMediaType.IMAGE_SENT,
            FolderMediaType.VIDEO, FolderMediaType.VIDEO_SENT,
            FolderMediaType.AUDIO, FolderMediaType.AUDIO_SENT,
            FolderMediaType.DOCUMENT, FolderMediaType.DOCUMENT_SENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FolderMediaType {
        int TEXT = 1;
        int TEXT_SENT = 2;
        int IMAGE = 3;
        int IMAGE_SENT = 4;
        int VIDEO = 5;
        int VIDEO_SENT = 6;
        int AUDIO = 7;
        int AUDIO_SENT = 8;
        int DOCUMENT = 9;
        int DOCUMENT_SENT = 10;
    }

    @StringDef({StoragePrefix.TEXT, StoragePrefix.IMAGE, StoragePrefix.VIDEO,
            StoragePrefix.AUDIO, StoragePrefix.DOCUMENT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StoragePrefix {
        String TEXT = "TXT";
        String IMAGE = "IMG";
        String VIDEO = "VID";
        String AUDIO = "AUD";
        String DOCUMENT = "DOC";
    }

    public static File getFolderMedia(@NonNull Context context, @FolderMediaType int folderMediaType) {
        switch (folderMediaType) {
            case FolderMediaType.TEXT:
                return Folder.getAppMediaTextFolder(context);
            case FolderMediaType.TEXT_SENT:
                return Folder.getAppMediaTextSentFolder(context);
            case FolderMediaType.IMAGE:
                return Folder.getAppMediaImagesFolder(context);
            case FolderMediaType.IMAGE_SENT:
                return Folder.getAppMediaImagesSentFolder(context);
            case FolderMediaType.VIDEO:
                return Folder.getAppMediaVideoFolder(context);
            case FolderMediaType.VIDEO_SENT:
                return Folder.getAppMediaVideoSentFolder(context);
            case FolderMediaType.AUDIO:
                return Folder.getAppMediaAudioFolder(context);
            case FolderMediaType.AUDIO_SENT:
                return Folder.getAppMediaAudioSentFolder(context);
            case FolderMediaType.DOCUMENT:
                return Folder.getAppMediaDocumentsFolder(context);
            case FolderMediaType.DOCUMENT_SENT:
                return Folder.getAppMediaDocumentsSentFolder(context);
        }
        return null;
    }

    public static File createAppMediaFile(@NonNull final Context context,
                                          @FolderMediaType final int folderMediaType,
                                          @StoragePrefix final String prefix,
                                          final String extension) {
        File folder = null;
        switch (folderMediaType) {
            case FolderMediaType.TEXT:
                folder = Folder.getAppMediaTextFolder(context);
                break;
            case FolderMediaType.TEXT_SENT:
                folder = Folder.getAppMediaTextSentFolder(context);
                break;
            case FolderMediaType.IMAGE:
                folder = Folder.getAppMediaImagesFolder(context);
                break;
            case FolderMediaType.IMAGE_SENT:
                folder = Folder.getAppMediaImagesSentFolder(context);
                break;
            case FolderMediaType.VIDEO:
                folder = Folder.getAppMediaVideoFolder(context);
                break;
            case FolderMediaType.VIDEO_SENT:
                folder = Folder.getAppMediaVideoSentFolder(context);
                break;
            case FolderMediaType.AUDIO:
                folder = Folder.getAppMediaAudioFolder(context);
                break;
            case FolderMediaType.AUDIO_SENT:
                folder = Folder.getAppMediaAudioSentFolder(context);
                break;
            case FolderMediaType.DOCUMENT:
                folder = Folder.getAppMediaDocumentsFolder(context);
                break;
            case FolderMediaType.DOCUMENT_SENT:
                folder = Folder.getAppMediaDocumentsSentFolder(context);
                break;
        }
        return new File(folder, generateFilenameMedia(prefix, extension));
    }

    /**
     * Genera el nombre seguro para el nombre de un fichero.
     * Fórmula: [PREFIX] + [DATE_TIME]
     *
     * @param prefix    Prefijo
     * @param extension Extension
     * @return Filename
     */
    public static String generateFilenameMedia(@StoragePrefix final String prefix,
                                               final String extension) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        format.setTimeZone(TimeZone.getDefault());
        String timeStampDate = format.format(new Date());
        return String.format("%s_%s.%s",
                prefix, timeStampDate.replace(" ", "-"), extension);
    }

    /**
     * Genera el nombre seguro para el nombre de un fichero.
     * Fórmula: [PREFIX] + [DATE] + [LA00 + USERID + NUMBER_RANDOM] + [TIME]
     * <p>
     * Note: La zona horaria para la generación de la fecha se consideró en UTC.
     *
     * @param prefix    Prefijo
     * @param extension Extension
     * @return Filename
     */
    public static String generateFilenameId(@StoragePrefix final String prefix,
                                            final String extension) {
        int userId = 1;// new SessionManager().getUserLogged().getId();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss", Locale.getDefault());
        formatTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStampDate = formatDate.format(new Date());
        String timeStampTime = formatTime.format(new Date());
        String numRandom = String.valueOf(new Random().nextInt(9));
        return String.format("%s-%s-LA00%s%s-%s.%s",
                prefix,
                timeStampDate.replace(" ", "-"),
                String.valueOf(userId).replace(" ", "-"),
                numRandom,
                timeStampTime.replace(" ", "-"),
                extension);
    }

    /**
     * Genera el nombre seguro para el nombre de un fichero.
     * Fórmula: [PREFIX] + [DATE] + [MESSAGE_ID]
     * <p>
     * Note: La zona horaria para la generación de la fecha se consideró en UTC.
     *
     * @param prefix    Prefijo
     * @param extension Extension
     * @return Filename
     */
    public static String generateFilenameId(@StoragePrefix final String prefix,
                                            final int messageId, final String extension) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss", Locale.getDefault());
        formatTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStampDate = formatDate.format(new Date()) + "" + formatTime.format(new Date());
        return String.format(Locale.getDefault(), "%s-%s-%d.%s",
                prefix,
                timeStampDate.replace(" ", "-"),
                messageId,
                extension);
    }

    public static String generateNameFile(@StoragePrefix final String prefix,
                                          final String extension) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss", Locale.getDefault());
        formatTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStampDate = formatDate.format(new Date())/* + "" + formatTime.format(new Date())*/;
        return String.format(Locale.getDefault(), "%s-%s.%s",
                prefix,
                timeStampDate.replace(" ", "-"),
                extension);
    }

    public static String generateNamePhoto(String namePhoto,
                                          final String extension) {
        return String.format(Locale.getDefault(), "%s.%s",
                namePhoto,
                extension);
    }


    public static String generateNameUIID() {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatTime = new SimpleDateFormat("HHmmss", Locale.getDefault());
        formatTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStampDate = formatDate.format(new Date()) + "" + formatTime.format(new Date());
        return String.format(Locale.getDefault(), "%s",
                timeStampDate.replace(" ", ""));
    }

    public static class Folder {

        /**
         * Folder de la caché.
         *
         * @param context : Contexto
         * @return {@link File} del folder que se ubica en la caché.
         */
        public static File getRequestCacheFolder(@NonNull Context context) {
            File folder = new File(String.valueOf(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    ? context.getCacheDir() + "api_disk_cache"
                    : context.getExternalCacheDir() + "api_disk_cache"));
            //noinspection ResultOfMethodCallIgnored
            folder.mkdir();
            return folder;
        }

        /**
         * Folder de la caché.
         *
         * @param context : Contexto
         * @return {@link File} del folder que se ubica en la caché.
         */
        public static File getCacheFolder(@NonNull Context context) {
            File folder = new File(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    ? context.getCacheDir() + Constants.PATH_FOLDER_CACHE
                    : context.getExternalCacheDir() + Constants.PATH_FOLDER_CACHE);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdir();
            return folder;
        }

        /**
         * Folder de la cámara.
         *
         * @return {@link File} del folder que se ubica en el directorio publico de fotos obtenidas por la
         * cámara.
         */
        public static File getCameraFolder(Context context) {
            String folderMain = context.getResources().getString(R.string.app_name);
            String dirMain = "Camera";
            String path = folderMain + File.separator + dirMain;
            File folder = new File(Environment.getExternalStorageDirectory(), path);
            boolean isExist = folder.exists();
            /*String cameraFolder = "Camera";
            File folder = new File(Environment.getExternalStoragePublicDirectory(
                    context.getResources().getString(R.string.app_name)) + File.separator + cameraFolder);*/
            //noinspection ResultOfMethodCallIgnored
            if (!isExist)
                folder.mkdirs();
            Log.e(TAG, "folder: " + folder);
            return folder;
        }

        /**
         * Folder base específico para App.
         *
         * @param context para obtener el nombre de la app desde res.
         * @return {@link File} creado como directorio.
         */
        public static File getAppBaseFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name));
            //noinspection ResultOfMethodCallIgnored
            folder.mkdir();
            return folder;
        }

        public static File getAppMediaTextFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_TEXT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            return folder;
        }

        public static File getAppMediaTextSentFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_TEXT_SENT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            FileUtils.createNoMedia(folder);
            return folder;
        }


        public static File getAppMediaImagesFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_IMAGES);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            return folder;
        }

        public static File getAppMediaImagesSentFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_IMAGES_SENT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            FileUtils.createNoMedia(folder);
            return folder;
        }

        public static File getAppMediaVideoFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_VIDEO);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            return folder;
        }

        public static File getAppMediaVideoSentFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_VIDEO_SENT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            FileUtils.createNoMedia(folder);
            return folder;
        }

        public static File getAppMediaAudioFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_AUDIO);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            return folder;
        }

        public static File getAppMediaAudioSentFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_AUDIO_SENT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            FileUtils.createNoMedia(folder);
            return folder;
        }

        public static File getAppMediaDocumentsFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_DOCUMENTS);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            return folder;
        }

        public static File getAppMediaDocumentsSentFolder(@NonNull Context context) {
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator
                    + context.getString(R.string.app_name) + Constants.PATH_FOLDER_APP_MEDIA_DOCUMENTS_SENT);
            //noinspection ResultOfMethodCallIgnored
            folder.mkdirs();
            FileUtils.createNoMedia(folder);
            return folder;
        }
    }
}