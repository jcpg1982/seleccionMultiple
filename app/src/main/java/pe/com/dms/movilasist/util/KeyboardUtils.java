package pe.com.dms.movilasist.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {

    private static String TAG = KeyboardUtils.class.getSimpleName();

    public static void hideKeyboard(final Activity activity) {
        if (activity == null) return;
        View view = activity.getCurrentFocus();
        hideKeyboard(activity, view);
    }

    public static void hideKeyboard(final Activity activity, final View focus) {
        if (activity == null) return;
        InputMethodManager inputManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (focus != null) {
            inputManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(final Activity activity) {
        if (activity == null) return;
        View view = activity.getCurrentFocus();
        hideKeyboard(activity, view);
    }

    public static void showKeyboard(final Activity activity, final View focus) {
        if (activity == null) return;
        InputMethodManager inputManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (focus != null) {
            inputManager.showSoftInput(
                    focus, InputMethodManager.SHOW_FORCED);
        }
    }

    public static boolean showingKeyboard(final Activity activity) {
        if (activity == null) return false;
        InputMethodManager inputManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        return view != null && inputManager.isActive();
    }

    public static void hideKeyboard(final Dialog dialog) {
        if (dialog == null) return;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void showKeyboard(final Dialog dialog) {
        if (dialog == null) return;
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /* Other methods, See: http://www.jianshu.com/p/e3de0656b356 */

    public static void hide(final Activity activity) {
        if (activity == null) return;
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void show(final Activity activity) {
        if (activity == null) return;
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = inputManager.isActive();
            if (!isOpen) {
                inputManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    public static void toggle(final Activity activity) {
        if (activity == null) return;
        View view = activity.getWindow().peekDecorView();

        if (view != null) {
            InputMethodManager inputManager =
                    (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void toggleVisanet(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            hideVisaNet(activity);
        } else {
            showVisaNet(activity);
        }
    }

    public static void showVisaNet(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY); // show
    }

    public static void hideVisaNet(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
    }
}
