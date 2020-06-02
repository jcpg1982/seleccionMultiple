package pe.com.dms.movilasist.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;

import java.net.URL;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtils {
    static String TAG = TextUtils.class.getSimpleName();

    /**
     * Método para determinar si un string es nulo, esté vacio o tenga un cadena "null"
     *
     * @param str Texto a ser validado
     * @return {tru} or {false}
     * @see <a href="https://stackoverflow.com/a/31115484"></a>
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.trim().isEmpty() || str.trim().equals("null"));
    }

    public static boolean isEmpty(View input) {
        return input instanceof TextView
                && isEmpty(((TextView) input).getText().toString());
    }

    public static String capitalize(String text) {
        String c = (text != null) ? text.trim() : "";
        String[] words = c.split(" ");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            result.append(w.length() > 1 ? w.substring(0, 1).toUpperCase(Locale.getDefault())
                    + w.substring(1, w.length()).toLowerCase(Locale.getDefault()) : w).append(" ");
        }
        return result.toString().trim();
    }

    /**
     * https://stackoverflow.com/a/23381504/7999917
     *
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        Log.e(TAG, "checkURL: " + url);
        if (TextUtils.isEmpty(url))
            return false;
        if (Patterns.WEB_URL.matcher(url).matches()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }
}
