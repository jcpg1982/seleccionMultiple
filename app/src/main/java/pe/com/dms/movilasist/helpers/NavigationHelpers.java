package pe.com.dms.movilasist.helpers;

import android.app.Activity;
import android.content.Intent;

import pe.com.dms.movilasist.ui.activities.mainActivity.MainActivity;

public class NavigationHelpers {
    /**
     * Navega hasta {@link MainActivity}.
     * <p>
     * <p>Usado cuando se mueve a la actividad principal despues de pasar el login correctamente.
     *
     * @param activity : Actual activity.
     */
    public static void moveToMainActivity(final Activity activity) {
        if (activity == null) return;
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setAction(activity.getIntent().getAction());
        if (activity.getIntent().getExtras() != null) {
            intent.putExtras(activity.getIntent().getExtras());
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Navega hasta {@link LoginActivity}.
     * <p>
     * <p>Usado cuando la session del usuario no esta activa o cuando se valida que no existe.
     *
     * @param activity : Actual activity.
     */
    /*public static void moveToLoginActivity(final Activity activity) {
        if (activity == null) return;
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }*/
}
