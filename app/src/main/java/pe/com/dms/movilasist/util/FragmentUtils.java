package pe.com.dms.movilasist.util;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentUtils {

    /**
     * Se encarga de setear el método {@link Fragment#onRequestPermissionsResult} en todos los
     * fragmentos. Se implementa desde una actividad que contendrá fragmentos. Se implementa desde un
     * fragmento que contendrá fragmentos hijos.
     * <p>
     * <p>https://stackoverflow.com/a/35625975
     *
     * @param fragmentManager
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionsResultSupport(FragmentManager fragmentManager,
                                                         int requestCode,
                                                         @NonNull String[] permissions,
                                                         @NonNull int[] grantResults) {
        if (fragmentManager.getFragments() != null) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment != null && fragment.isVisible()) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    public static void onActivityResultSupport(FragmentManager fragmentManager,
                                               int requestCode,
                                               int resultCode,
                                               Intent data) {
        if (fragmentManager.getFragments() != null) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment != null && fragment.isVisible()) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    /**
     * Returns true if the fragment is attached to the activity, or false if it is not attached
     *
     * @param fragment fragment to be tested
     * @return true if the fragment is attached to the activity, or false if it is not attached
     */
    public static boolean isFragmentAttached(Fragment fragment) {
        return fragment.getActivity() != null && fragment.isAdded();
    }
}
