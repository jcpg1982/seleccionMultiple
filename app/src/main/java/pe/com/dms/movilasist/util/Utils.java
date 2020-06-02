package pe.com.dms.movilasist.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnimRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static void showError(Context context, Throwable e) {
        showError(context, context.getResources().getString(R.string.error),
                e.getLocalizedMessage());
    }

    public static void showError(Context context, @StringRes int messageRes) {
        showError(context, context.getResources().getString(R.string.error),
                context.getResources().getString(messageRes));
    }

    public static void showError(Context context, String message) {
        showError(context, context.getResources().getString(R.string.error),
                message);
    }

    /**
     * Muestra un error en dialogo
     *
     * @param context construye el alertDialog en el contexto.
     * @param message Mensaje para mostrar el error en el dialogo.
     */
    public static void showError(Context context, String title, String message) {
        if (context == null) return;
        new DefaultDialog.Builder(context)
                .title(title)
                .message(message)
                .textPositiveButton("Aceptar")
                .textColorPositiveButton(R.color.colorAccent)
                .cancelable(false)
                .dialogType(DefaultDialog.DialogType.ALERT)
                .onPositive(
                        new DefaultDialog.OnSingleButtonListener() {
                            @Override
                            public void onClick(@NonNull DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                .buildAndShow(((AppCompatActivity) context).getSupportFragmentManager(),
                        Constants.TAG_DIALOG_SHOW_ERROR);
    }

    private static Toast mToast;

    public static void showToast(Context context, @StringRes int msg) {
        if (context == null) return;
        showToast(context, context.getString(msg));
    }

    public static void showToast(Context context, String msg) {
        if (context == null) return;
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showSnack(View view, String message) {
        showSnack(view, message, null, null);
    }

    public static void showSnack(View view, String message, String action,
                                 View.OnClickListener callback) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(action, callback);
        snackbar.setActionTextColor(view.getResources().getColor(R.color.danger));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorCardDark));
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * Muestra Toast como Tooltip en una view(POSICION ARRIBA DE LA VIEW).
     *
     * @param target  view
     * @param context activity
     * @param message mensaje a mostrar.
     */
    private static void showToolTip(View target, Activity context, String message) {

        int xOffset = 0;
        int yOffset = 0;
        Rect gvr = new Rect();

        View parent = (View) target.getParent();
        int parentHeight = parent.getHeight();

        if (target.getGlobalVisibleRect(gvr)) {
            View root = target.getRootView();

            int halfWidth = root.getRight() / 2;
            int halfHeight = root.getBottom() / 2;

            int parentCenterX = ((gvr.right - gvr.left) / 2) + gvr.left;

            int parentCenterY = ((gvr.bottom - gvr.top) / 2) + gvr.top;

            if (parentCenterY <= halfHeight) {
                yOffset = -(halfHeight - parentCenterY) - parentHeight;
            } else {
                yOffset = (parentCenterY - halfHeight) - parentHeight;
            }

            if (parentCenterX < halfWidth) {
                xOffset = -(halfWidth - parentCenterX);
            }

            if (parentCenterX >= halfWidth) {
                xOffset = parentCenterX - halfWidth;
            }
        }

        vibrate(context, 30);
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, xOffset, yOffset);
        mToast.show();
    }

    /**
     * Muestra Toast como Tooltip en una view (POSICION DEBAJO DE LA VIEW).
     *
     * @param target  view
     * @param context activity
     * @param message mensaje a mostrar.
     */
    public static void showToolTipInBottom(Activity context,
                                           View target,
                                           Window window,
                                           String message) {
        showToolTip(
                context,
                target,
                window,
                message,
                0,
                context.getResources().getDimensionPixelSize(R.dimen.content_inset_more));
    }

    public static void showToolTip(Activity context,
                                   View view,
                                   Window window,
                                   String message,
                                   int offsetX,
                                   int offsetY) {
        //https://stackoverflow.com/a/21026866
        vibrate(context, 30);
        if (mToast != null) mToast.cancel();

        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, offsetX, offsetY);
        mToast.show();

        // toasts are positioned relatively to decor view, views relatively to their parents, we have to gather additional data to have a common coordinate system
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        // covert anchor view absolute position to a position which is relative to decor view
        int[] viewLocation = new int[2];
        view.getLocationInWindow(viewLocation);
        int viewLeft = viewLocation[0] - rect.left;
        int viewTop = viewLocation[1] - rect.top;

        // measure toast to center it relatively to the anchor view
        DisplayMetrics metrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(metrics.heightPixels, View.MeasureSpec.UNSPECIFIED);
        mToast.getView().measure(widthMeasureSpec, heightMeasureSpec);
        int toastWidth = mToast.getView().getMeasuredWidth();

        // compute toast offsets
        int toastX = viewLeft + (view.getWidth() - toastWidth) / 2 + offsetX;
        int toastY = viewTop + view.getHeight() + offsetY;

        mToast.setGravity(Gravity.START | Gravity.TOP, toastX, toastY);
    }

    /**
     * Carga una animación.
     *
     * @param context Contexto
     * @param anim    Animacion int @AnimRes
     * @return Animation cargada.
     */
    public static Animation loadAnimationRes(Context context, @AnimRes int anim) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        animation.reset();
        return animation;
    }

    /**
     * Pone una borde sombreado a un drawable, especial para iconos con fondo blanco.
     *
     * @param context
     * @param drawableRes drawable para procesar
     * @return un drawable con sombra.
     */
    public static Drawable getDrawableWithShadow(Context context, @DrawableRes int drawableRes) {
        Bitmap bitmap = Utils.applyStyleBlurMaskImage(context,
                ImageConverterUtils.drawableToBitmap(context, drawableRes),
                BlurMaskFilter.Blur.NORMAL);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    /**
     * Obtiene la version actual de la aplicacion.
     *
     * @param context
     * @return en un {@link String} la version de la aplicacion.
     */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // this should never happen
            return "Unknown";
        }
    }

    /**
     * Obtiene la version de compilacion actual de la aplicacion.
     *
     * @param context
     * @return verionCode de la app.
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // this should never happen
            return -1;
        }
    }

    public static void openLink(Context context, String link) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            context.startActivity(Intent.createChooser(intent, null));
        }
    }


    public static void openMarket(Context context, String applicationID) {
        try {
            final Intent intent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.MARKET_URL_X, applicationID)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(Intent.createChooser(intent, null));
        } catch (ActivityNotFoundException e) {
            openPlayStore(context, applicationID);
        }
    }

    public static void openPlayStore(Context context, String applicationID) {
        Utils.openLink(context, String.format(Constants.GOOGLE_PLAY_URL_X, applicationID));
    }

    public static void sentEmail(Activity activity,
                                 String email,
                                 @Nullable String emailCC,
                                 @Nullable String subject,
                                 @Nullable String message) {
        Uri contactUri = null;
        final Uri contactUriHelp;

        if (emailCC == null) {
            emailCC = "";
        }
        if (subject == null) {
            subject = "";
        }
        if (message == null) {
            message = "";
        }

        try {
            contactUri = Uri.parse(String.format(
                    "mailto:%s?cc=%s&subject=%s&body=%s",
                    URLEncoder.encode(email, "UTF-8"),
                    (!TextUtils.isEmpty(emailCC)) ? URLEncoder.encode(emailCC, "UTF-8") : emailCC,
                    subject,
                    message));
        } catch (UnsupportedEncodingException e) {
            Utils.showToast(activity, e.getMessage());
            return;
        }

        contactUriHelp = contactUri;
        if (contactUriHelp != null) {
            try {
                Intent intentSend = new Intent(Intent.ACTION_SENDTO, contactUriHelp);
                activity.startActivity(Intent.createChooser(intentSend, null));
            } catch (Exception e) {
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, contactUriHelp));
                } catch (Exception e2) {
                    Utils.showToast(activity, e2.getMessage());
                }
            }
        }
    }

    public static void applyStyleTextView(TextView textView, BlurMaskFilter.Blur style) {
        if (Build.VERSION.SDK_INT >= 11) {
            textView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        float radius = textView.getTextSize() / 10;
        BlurMaskFilter filter = new BlurMaskFilter(radius, style);
        textView.getPaint().setMaskFilter(filter);
    }

    public static Bitmap applyStyleBlurMaskImage(Context context,
                                                 Bitmap bitmap,
                                                 BlurMaskFilter.Blur style) {
        Bitmap bmOut = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmOut);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        float radius = bitmap.getHeight() / 10;
        Paint ptBlur = new Paint();
        ptBlur.setMaskFilter(new BlurMaskFilter(radius, style));

        int[] offsetXY = new int[2];

        Bitmap bmAlpha = bitmap.extractAlpha(ptBlur, offsetXY);

        Paint ptAlphaColor = new Paint();
        ptAlphaColor.setColor(context.getResources().getColor(
                R.color.cardview_shadow_start_color));

        canvas.drawBitmap(bmAlpha, offsetXY[0], offsetXY[1], ptAlphaColor);
        bmAlpha.recycle();

        canvas.drawBitmap(bitmap, 0, 0, null);

        return bmOut;
    }

    /**
     * Verifica la conexión a internet.
     *
     * @return <code>true</code> Si tiene conexion. <code>false</code> Si no tiene acceso a internet.
     */
    public static boolean hasNetworkConnected(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean hasNetworkConnectedOrConnecting(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isConnectedToWiFi(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Genera una vibracion en el telefono según la intensidad del parametro.
     *
     * @param context      Activity
     * @param milliseconds tiempo de vibracion en milisegundos.
     */
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(milliseconds);
        }
    }

    /**
     * Recursivamente navega a través de los fragmentos anidados para una entrada al backstack.
     *
     * @return {true} si se realizó un pop
     * @see <a href="https://stackoverflow.com/a/39524096">Recursive PopBackStack</a>
     */
    public static boolean recursivePopBackStack(@NotNull FragmentManager fragmentManager) {
        if (fragmentManager.getFragments() != null) {
            for (Fragment fragment : fragmentManager.getFragments()) {
                if (fragment != null && fragment.isVisible()) {
                    boolean popped = recursivePopBackStack(fragment.getChildFragmentManager());
                    if (popped) {
                        return true;
                    }
                }
            }
        }
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    /**
     * Revisa el dispositivo si cuenta con Google Play Services.
     *
     * @param context Contexto
     * @return true si tiene corriendo Google Play Services.
     * false si no tiene corriendo Google Play Services.
     */
    public static boolean checkGooglePlayServices(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    /**
     * Inicializa el progress pero no lo muestra
     */
    public static ProgressDialog setupProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        //progressDialog.getContext().setTheme(R.style.Dialog_LinkUp_ProgressDefault);
        return progressDialog;
    }

    /**
     * Inicializa el progress pero no lo muestra
     */
    public static ProgressDialog setupPercentProgressDialog(Context context) {
        ProgressDialog percentProgressDialog = new ProgressDialog(context);
        percentProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        percentProgressDialog.setIndeterminate(false);
        percentProgressDialog.setMax(100);
        percentProgressDialog.setCancelable(false);
        //percentProgressDialog.getContext().setTheme(R.style.Dialog_LinkUp_ProgressDefault);
        return percentProgressDialog;
    }

    /**
     * muetsra u oculta dialogo de coargando con un texto personalizado
     *
     * @param show    tru muestra dialog false oculta
     * @param message mensaje personalizado para mostrar en el dialogo
     */
    public static void showProgress(ProgressDialog progressDialog, boolean show, String message) {
        if (show) {
            if (progressDialog != null) {
                if (progressDialog.isShowing())
                    progressDialog.cancel();

                progressDialog.setMessage(message);
                progressDialog.show();
            }
        } else {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    public static String getStreet(Context context, double latitude, double longitude) {
        String street = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> direccion;
        try {
            direccion = geocoder.getFromLocation(latitude, longitude, 1);
            street = direccion.get(0).getAddressLine(0);

            String locality = direccion.get(0).getLocality();
            String adminArea = direccion.get(0).getAdminArea();
            String countryName = direccion.get(0).getCountryName();
            String featureName = direccion.get(0).getFeatureName();
            String countryCode = direccion.get(0).getCountryCode();
            String phone = direccion.get(0).getPhone();
            String subAdminArea = direccion.get(0).getSubAdminArea();
            String subLocality = direccion.get(0).getSubLocality();
            String premises = direccion.get(0).getPremises();
            String postalCode = direccion.get(0).getPostalCode();
            String subThoroughfare = direccion.get(0).getSubThoroughfare();
            String thoroughfare = direccion.get(0).getThoroughfare();
            String url = direccion.get(0).getUrl();

            Log.e(TAG, "getStreet locality: " + locality + ", adminArea: " + adminArea +
                    ", countryName: " + countryName + ", featureName: " + featureName +
                    ", countryCode: " + countryCode + ", phone: " + phone +
                    ", subAdminArea: " + subAdminArea + ", subLocality: " + subLocality +
                    ", premises: " + premises + ", postalCode: " + postalCode +
                    ", subThoroughfare: " + subThoroughfare + ", thoroughfare: " + thoroughfare +
                    ", url: " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return street;
    }

    public static boolean validateEmailFormat(String correo) {
        boolean validated;
        Pattern pattern = Pattern.compile(PatternUtils.PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);

        if (!matcher.matches()) {
            validated = false;
        } else {
            validated = true;
        }
        return validated;
    }
}
