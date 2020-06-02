package pe.com.dms.movilasist.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.StringRes;

import pe.com.dms.movilasist.font.TypefaceSpan;

public class FontUtils {

    /**
     * Cambia de manera recursiva la fuente de texto de los widgets como TextView, Button y EditText.
     *
     * @param view
     * @param typeface
     */
    public static void overrideFonts(final View view, Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, typeface);
                }
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(typeface);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la fuente de texto de un TextView
     *
     * @param view
     * @param typeface
     */
    public static void overrideFontTextView(final View view, Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, typeface);
                }
            } else if (view instanceof TextView) {
                ((TextView) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la fuente de texto de un Button
     *
     * @param view
     * @param typeface
     */
    public static void overrideFontButton(final View view, Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, typeface);
                }
            } else if (view instanceof Button) {
                ((TextView) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la fuente de texto de un EditText
     *
     * @param view
     * @param typeface
     */
    public static void overrideFontEditText(final View view, Typeface typeface) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(child, typeface);
                }
            } else if (view instanceof EditText) {
                ((TextView) view).setTypeface(typeface);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Typeface getTypeFace(Context context, @StringRes int fontNameRes) {
        return Typeface.createFromAsset(
                context.getAssets(), "fonts/" + context.getResources().getString(fontNameRes));
    }

    public static SpannableString typeface(Typeface typeface, CharSequence string) {
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new TypefaceSpan(typeface),
                0,
                spannableString.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString shadowText(String text, int end) {
        SpannableString spannableString = new SpannableString(text);
        BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.YELLOW);
        spannableString.setSpan(backgroundColorSpan,
                0,
                0 + end,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
