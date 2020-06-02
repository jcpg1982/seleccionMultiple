package pe.com.dms.movilasist.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

public class ColorUtils {

    public static @ColorInt
    int adjustAlpha(
            @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static @ColorInt
    int adjustAlpha(
            @ColorInt int color, @FloatRange(from = 0.0, to = 1.0) float alpha) {
        int A = Math.round(Color.alpha(color) * alpha);
        int R = Color.red(color);
        int G = Color.green(color);
        int B = Color.blue(color);
        return Color.argb(A, R, G, B);
    }

    public static @ColorInt
    int stripAlpha(@ColorInt int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f; // value component
        color = Color.HSVToColor(hsv);
        return color;
    }

    public static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE) return true;
        final double darkness =
                1
                        - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))
                        / 255;
        return darkness < 0.5;
    }

    /**
     * Check that the lightness value (0–1)
     */
    public static boolean isDark(float[] hsl) { // @Size(3)
        return hsl[2] < 0.5f;
    }

    /**
     * Convert to HSL & check that the lightness value
     */
    public static boolean isDark(@ColorInt int color) {
        float[] hsl = new float[3];
        androidx.core.graphics.ColorUtils.colorToHSL(color, hsl);
        return isDark(hsl);
    }

    /**
     * Procesa un color para determinar si es BLANCO o NEGRO.
     *
     * @param color de ingreso a ser procesado.
     * @return {@link Color#WHITE} or {@link Color#BLACK}
     */
    public static @ColorInt
    int blackOrWhite(int color) {
        double darkness =
                1
                        - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))
                        / 255;
        if (darkness >= 0.5) return Color.WHITE;
        else return Color.BLACK;
    }

    public static String colorIntToColorHex(@ColorInt int color, boolean showAlpha) {
        int base = showAlpha ? 0xFFFFFFFF : 0xFFFFFF;
        String format = showAlpha ? "#%08X" : "#%06X";
        return String.format(format, (base & color)).toUpperCase();
    }

    private ColorUtils() {
    }
}
