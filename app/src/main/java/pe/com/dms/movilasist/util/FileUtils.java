package pe.com.dms.movilasist.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pe.com.dms.movilasist.Application;
import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.helpers.StorageManager;
import pe.com.dms.movilasist.model.pojo.FilePicker;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * Obtiene la extension de un fichero.
     *
     * @param str String de entrada.
     * @return Extension del string.
     */
    public static String getExtension(String str) {
        if (str == null)
            return null;

        int dot = str.lastIndexOf(".");
        if (dot >= 0) {
            // A file extension without the leading '.'
            return str.substring(dot).substring(1);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * Remueve la extension de un fichero.
     *
     * @param str String de entrada.
     * @return String sin extension.
     */
    public static String removeExtension(String str) {
        if (str.startsWith(".")) return str;
        int dot = str.lastIndexOf('.');
        if (dot == -1) return str;
        return str.substring(0, dot);
    }

    /**
     * Obtiene el filename de una url.
     *
     * @param str              Url.
     * @param withoutExtension Determina si devolverá el filename con o sin extension.
     * @return Filename de la Url.
     */
    public static String getFileName(String str, boolean withoutExtension) {
        int slashIndex = str.lastIndexOf('/');
        String fileName = str.substring(slashIndex + 1, str.length());
        if (withoutExtension)
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        return fileName;
    }

    /**
     * Formatea un {@link File#length()} en un String con formato
     * "bytes", "KB", "MB", "GB", "TB", "PB"
     *
     * @param size {@link File#length()}
     * @return Size en format String.
     */
    public static String formatFileSize(long size) {
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.0");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

    public static long getFolderSize(File folderPath) {
        long totalSize = 0;

        if (folderPath == null) {
            return 0;
        }

        if (!folderPath.isDirectory()) {
            return 0;
        }

        File[] files = folderPath.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                totalSize += file.length();
            } else if (file.isDirectory()) {
                totalSize += file.length();
                totalSize += getFolderSize(file);
            }
        }
        return totalSize;
    }

    /**
     * Checks if external storage is available for read and write.
     *
     * @return true or false
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read.
     *
     * @return true or false
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(path);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * Scanea los ficheros para que se visualice en la galeria u otros medios.
     *
     * @param context Contexto
     * @param path    Un ruta de un file en String o un array de String con muchas rutas.
     */
    public static void scanFile(Context context, String... path) {
        try {
            MediaScannerConnection.scanFile(context,
                    path, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("FileScan", "Scanned: " + path + ":");
                            Log.i("FileScan", "-> uri = " + uri);
                        }
                    });
        } catch (NullPointerException e) {
            Log.e(TAG, "scanFile " + e.getMessage());
        }
    }

    public static File createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                dir.mkdirs();
            }
        }
        return dir;
    }

    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDir(path);
        }
        dir.delete();
    }

    public static void deleteDirContent(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDirContent(file.getPath());
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String item : children) {
                    deletedAll = deleteFile(new File(file, item)) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }
        return deletedAll;
    }

    public static boolean fileIsExists(File file) {
        return fileIsExists(file.getAbsolutePath());
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean copyFile(File source, File dest) {
        boolean copied = false;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(source));
            bos = new BufferedOutputStream(new FileOutputStream(dest, false));

            byte[] buf = new byte[1024];
            bis.read(buf);

            do {
                bos.write(buf);
            } while (bis.read(buf) != -1);
            copied = true;
            Log.i(TAG, "method: copyFile(), message: Archivo copiado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "method: copyFile(), message: IOException: No se pudo copiar el archivo");
        } finally {
            try {
                if (bis != null) bis.close();
                if (bos != null) bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "method: copyFile(), message: IOException: No se pudo copiar el archivo");
            }
        }
        return copied;
    }

    public static void saveBitmapFile(Bitmap bitmap, String path) {
        File file = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static String createZip(@NonNull List<String> files, File file) {
        try {
            final int BUFFER = 2048;
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(file);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    dest));

            byte data[] = new byte[BUFFER];
            for (int i = 0; i < files.size(); i++) {
                FileInputStream fi = new FileInputStream(files.get(i));
                origin = new BufferedInputStream(fi, BUFFER);

                ZipEntry entry = new ZipEntry(files.get(i).substring(
                        files.get(i).lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;

                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }

            out.close();
            return file.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                Log.e(TAG, "getPath isExternalStorageDocument jak: " + uri);
                final String docId = DocumentsContract.getDocumentId(uri);
                Log.e(TAG, "getPath isExternalStorageDocument docId jak: " + docId);
                final String[] split = docId.split(":");
                Log.e(TAG, "getPath isExternalStorageDocument split jak: " + split.toString().toLowerCase());
                final String type = split[0];
                Log.e(TAG, "getPath isExternalStorageDocument type jak: " + type);
                final String type1 = split[1];
                Log.e(TAG, "getPath isExternalStorageDocument type1 jak: " + type1);
                if ("primary".equalsIgnoreCase(type)) {
                    Log.e(TAG, "getPath isExternalStorageDocument prymary jak: " + Environment.getExternalStorageDirectory() + "/" + split[1]);
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else if ("home".equalsIgnoreCase(type)) {
                    Log.e(TAG, "getPath isExternalStorageDocument home jak: " + Environment.getExternalStorageDirectory() + "/"
                            + Environment.DIRECTORY_DOCUMENTS + "/" + split[1]);
                    return Environment.getExternalStorageDirectory() + "/"
                            + Environment.DIRECTORY_DOCUMENTS + "/" + split[1];
                } else {
                    Log.e(TAG, "getPath isExternalStorageDocument else jak: " + "storage" + "/" + type + "/" + split[1]);
                    return "storage" + "/" + type + "/" + split[1];
                }
                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                Log.e(TAG, "getPath isDownloadsDocument jak: " + uri);
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                Log.e(TAG, "getPath isMediaDocument jak: " + uri);
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                Log.e(TAG, "jak: " + getDataColumn(context, contentUri, selection, selectionArgs));
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if (ContentResolver.SCHEME_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                InputStream inputStream = null;
                try {
                    inputStream = context.getContentResolver().openInputStream(uri);
                    File photoFile = createFileTempFrom(context, inputStream);
                    return photoFile.getPath();
                } catch (Exception e) {
                    e.printStackTrace();
                    return uri.getLastPathSegment();
                } finally {
                    try {
                        if (inputStream != null)
                            inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if (ContentResolver.SCHEME_FILE.equalsIgnoreCase(uri.getScheme())) {
            Log.e(TAG, "getPath SCHEME_FILE jak: " + uri.getPath());
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(final Context context,
                                       final Uri uri,
                                       final String selection,
                                       final String[] selectionArgs) {

        Cursor cursor = null;
        final String column = MediaStore.MediaColumns.DATA;
        final String[] projection = {
                column
        };
        ContentResolver resolver = context.getContentResolver();
        try {
            cursor = resolver.query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority())
                || "com.google.android.apps.photos.contentprovider".equals(uri.getAuthority());
    }

    /**
     * Convierte una duracion en milli a string con un formato de tiempo.
     * {@link <a href='https://stackoverflow.com/questions/36425670/videoview-time-duration'></a>}
     *
     * @param millis long
     * @return String
     */
    public static String getDisplayDurationTime(long millis) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        long totalSeconds = millis / 1000;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        int hours = (int) (totalSeconds / 3600);
        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String formatTime(int millis) {
        return String.format(Locale.ENGLISH, "%d:%02d", millis / 60000, Math.min(Math.round((millis % 60000) / 1000f), 59));
    }

    public static class IntentF {

        private static Uri getMediaTempUri(Context context, String prefix, String extension) {
            Uri uriTemp;
            String timeStamp = new SimpleDateFormat(DateUtils.PATTERN_DATE_TAREO,
                    Locale.getDefault()).format(new Date());
            String name = String.format("%s_%s.%s",
                    prefix, timeStamp.replace(" ", "-"), extension);
            File fileTemp = new File(StorageManager.Folder.getCameraFolder(context), name);
            Log.e(TAG, "fileTemp: " + fileTemp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uriTemp = FileProvider.getUriForFile(context,
                        "pe.com.dms.movilasist", fileTemp);
                Log.e(TAG, "uriTemp mayor a N: " + uriTemp);
            } else {
                uriTemp = Uri.fromFile(fileTemp);
                Log.e(TAG, "uriTemp menor a N: " + uriTemp);
            }
            return uriTemp;
        }

        public static Uri pickImageCamera(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            Uri uriTemp = getMediaTempUri(fragment.getContext(),
                    "IMG", "jpg");

            try {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTemp);
                intent.putExtra("return-data", true);
                //intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                //intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 5 * 1024 * 1024);
                try {
                    fragment.startActivityForResult(intent, requestCode);
                } catch (ActivityNotFoundException e) {
                    fragment.startActivityForResult(Intent.createChooser(intent, null),
                            requestCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return uriTemp;
        }

        public static Uri pickVideoCamera(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            Uri uriTemp = getMediaTempUri(fragment.getContext(),
                    "VID", "mp4");

            try {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTemp);
                intent.putExtra("return-data", true);
                //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                try {
                    fragment.startActivityForResult(intent, requestCode);
                } catch (ActivityNotFoundException e) {
                    fragment.startActivityForResult(Intent.createChooser(intent, null),
                            requestCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return uriTemp;
        }

        public static void pickImageGallery(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            try {
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                fragment.startActivityForResult(Intent.createChooser(intent, null),
                        requestCode);
            }
        }

        public static void pickImageManage(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            try {
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                fragment.startActivityForResult(Intent.createChooser(intent, null),
                        requestCode);
            }
        }

        public static void pickVideoStorage(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            try {
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("video/*");
                } else {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("video/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                fragment.startActivityForResult(Intent.createChooser(intent, null),
                        requestCode);
            }
        }

        public static void pickAudioRecorder(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            try {
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                fragment.startActivityForResult(Intent.createChooser(intent, null),
                        requestCode);
            }
        }

        public static void pickAudioStorage(final Fragment fragment, final int requestCode) {
            Intent intent = new Intent();
            try {
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fragment.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("audio/*");
                } else {
                    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("audio/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                fragment.startActivityForResult(Intent.createChooser(intent, null),
                        requestCode);
            }
        }

        /**
         * https://stackoverflow.com/a/46074075
         *
         * @param requestCode
         */
        public static void pickDocument(final Fragment fragment, final int requestCode) {
            String[] mimeTypes = {
                    "application/msword",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                    "application/vnd.ms-powerpoint",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                    "application/vnd.ms-excel",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                    "text/plain",
                    "application/pdf",
                    "application/zip",
                    "image/*",
                    "video/*",
                    "audio/*"
            };

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            } else {
                StringBuilder mimeTypesStr = new StringBuilder();
                for (String mimeType : mimeTypes) {
                    mimeTypesStr.append(mimeType).append("|");
                }
                intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
            }
            fragment.startActivityForResult(Intent.createChooser(intent, null), requestCode);
        }

        public static void openWith(final Fragment fragment, final Uri uri, final String mimeType) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            try {
                fragment.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                fragment.startActivity(Intent.createChooser(intent,
                        fragment.getResources().getString(R.string.open_with)));
            }
        }

        public static void openWith(final Activity activity, final Uri uri, final String mimeType) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            try {
                activity.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                activity.startActivity(Intent.createChooser(intent,
                        activity.getResources().getString(R.string.open_with)));
            }
        }

        public static void openWithChooser(final Fragment fragment, final Uri uri, final String mimeType) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            fragment.startActivity(Intent.createChooser(intent,
                    fragment.getResources().getString(R.string.open_with)));
        }

        public static void openWithChooser(final Activity activity, final Uri uri, final String mimeType) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(Intent.createChooser(intent,
                    activity.getResources().getString(R.string.open_with)));
        }

        /*public static void openMediaViewerActivity(final Context context, final Uri uri, final String mimeType,
                                                   FilePicker extra, View view) {
            Intent intent = new Intent(context, MediaViewerActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            Bundle extras = new Bundle(FilePicker.class.getClassLoader());
            extras.putParcelable(Constants.Intent.EXTRA_FILE_PICKER, extra);
            intent.putExtras(extras);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeScaleUpAnimation(view,
                            (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
                } catch (Exception e) {
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e2) {
                        context.startActivity(Intent.createChooser(intent,
                                context.getResources().getString(R.string.open_with)));
                    }
                }
            } else {
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(Intent.createChooser(intent,
                            context.getResources().getString(R.string.open_with)));
                }
            }
        }*/

        /*public static void openPlayerActivity(final Context context, final Uri uri, final String mimeType,
                                              FilePicker extra, View view) {
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, mimeType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            Bundle extras = new Bundle(FilePicker.class.getClassLoader());
            extras.putParcelable(Constants.Intent.EXTRA_FILE_PICKER, extra);
            intent.putExtras(extras);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                try {
                    ActivityCompat.startActivity(context, intent, ActivityOptionsCompat.makeScaleUpAnimation(view,
                            (int) view.getX(), (int) view.getY(), view.getWidth(), view.getHeight()).toBundle());
                } catch (Exception e) {
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e2) {
                        context.startActivity(Intent.createChooser(intent,
                                context.getResources().getString(R.string.open_with)));
                    }
                }
            } else {
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    context.startActivity(Intent.createChooser(intent,
                            context.getResources().getString(R.string.open_with)));
                }
            }
        }*/
    }

    public static Uri getUriFromFile(final Context context, final File file) {
        if (file == null) return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context,
                    "pe.com.dms.movilasist", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static boolean hasMicrophone(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
    }

    public static long getDuration(File file) {
        if (!FileUtils.fileIsExists(file))
            return 0L;
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file.getAbsolutePath());
            return mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static void createNoMedia(final File directory) {
        final File noMedia = new File(directory, ".nomedia");
        if (!noMedia.exists()) {
            try {
                if (!noMedia.createNewFile()) {
                    Log.d(TAG, "created nomedia file " + noMedia.getAbsolutePath());
                }
            } catch (Exception e) {
                Log.d(TAG, "could not create nomedia file");
            }
        }
    }

    private static File createFileTempFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createFileTemp(context, "TEMP", "png");
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createFileTemp(Context context, String prefix, String extension) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String name = String.format("%s_%s.%s",
                prefix, timeStamp.replace(" ", "-"), extension);
        return new File(StorageManager.Folder.getCacheFolder(Application.get(context)).getAbsolutePath(), name);
    }

    public static String encodedPath(File file) {
        String encodedFile = "";
        try {
            byte[] buffer = new byte[(int) file.length()];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            encodedFile = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedFile;
    }

    private static File toFile(Uri uri) {
        if (uri == null) return null;
        Log.e(TAG, "" + uri.getPath());
        Log.e(TAG, "" + uri.toString());
        return new File(uri.getPath());
    }

    public static boolean isSDCardAvailable(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;
    }
}