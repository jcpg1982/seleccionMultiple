package pe.com.dms.movilasist.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

public class ResolveUtils {

    public static int resolveColor(final Context context, @AttrRes int attr) {
        return resolveColor(context, attr, 0);
    }

    public static int resolveColor(final Resources.Theme theme, @AttrRes int attr) {
        return resolveColor(theme, attr, 0);
    }

    public static int resolveColor(final Context context, @AttrRes int attr, @ColorInt int defaultColor) {
        return resolveColor(context.getTheme(), attr, defaultColor);
    }

    /**
     * Para obtner el color al determinar el thema de una vista especifica:
     * <pre><code>
     * ResolveUtils.resolveColor(View.getContext().getTheme(),
     *              R.attr.textColorSecondary,
     *              getResources().getColor(R.color.default_color)))
     * </code></pre>
     * Para obtner el color al determinar el thema de una controlador activity, fragment o dialog:
     * <pre><code>
     * ResolveUtils.resolveColor(getContext().getTheme(),
     *              R.attr.textColorSecondary,
     *              getResources().getColor(R.color.default_color)))
     * </code></pre>
     * O
     * <pre><code>
     * ResolveUtils.resolveColor(getContext(),
     *              R.attr.textColorSecondary,
     *              getResources().getColor(R.color.default_color)))
     * </code></pre>
     *
     * @param theme        Theme del contexto.
     * @param attr         atributo res.
     * @param defaultColor color por defecto.
     * @return color.
     */
    public static int resolveColor(final Resources.Theme theme, @AttrRes int attr, @ColorInt int defaultColor) {
        TypedArray a = theme.obtainStyledAttributes(new int[]{attr});
        try {
            return a.getColor(0, defaultColor);
        } finally {
            a.recycle();
        }
    }

    public static int resolveDimension(final Context context, @AttrRes int attr) {
        return resolveDimension(context, attr, -1);
    }

    private static int resolveDimension(Context context, @AttrRes int attr, int fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getDimensionPixelSize(0, fallback);
        } finally {
            a.recycle();
        }
    }

    public static boolean resolveBoolean(final Context context, @AttrRes int attr, boolean fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getBoolean(0, fallback);
        } finally {
            a.recycle();
        }
    }

    public static boolean resolveBoolean(final Context context, @AttrRes int attr) {
        return resolveBoolean(context, attr, false);
    }

    public static String resolveString(Context context, @AttrRes int attr) {
        TypedValue v = new TypedValue();
        context.getTheme().resolveAttribute(attr, v, true);
        return (String) v.string;
    }

    public static Drawable resolveDrawable(final Context context, @AttrRes int attr) {
        return resolveDrawable(context, attr, null);
    }

    private static Drawable resolveDrawable(final Context context, @AttrRes int attr, Drawable fallback) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            Drawable d = a.getDrawable(0);
            if (d == null && fallback != null) {
                d = fallback;
            }
            return d;
        } finally {
            a.recycle();
        }
    }

    private ResolveUtils() {
    }
}
