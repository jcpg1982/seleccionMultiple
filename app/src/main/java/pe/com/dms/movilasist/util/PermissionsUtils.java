package pe.com.dms.movilasist.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionsUtils {
    public static final int REQUEST_PERMISSION_STORAGE = 1;
    public static final int REQUEST_PERMISSION_CAMERA = 2;
    public static final int REQUEST_PERMISSION_LOCATION = 3;
    public static final int REQUEST_PERMISSION_INTERNET = 4;

    public static void requestPermissions(@NonNull Context context, String[] permissions,
                                          int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, permissions, requestCode);
        }
    }

    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permission != null) {
            return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void requestPermission(@NonNull Context context, String permission,
                                         int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (AppCompatActivity) context, permission)) {
                ActivityCompat.requestPermissions((AppCompatActivity) context,
                        new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions((AppCompatActivity) context,
                        new String[]{permission}, requestCode);
            }
        }
    }
}
