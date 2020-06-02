package pe.com.dms.movilasist.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.CheckResult;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.lang.reflect.Field;

import pe.com.dms.movilasist.R;

public class TintUtils {

    public static Drawable createTintedDrawable(
            @NonNull Context context, @DrawableRes int drawable, @ColorInt int color) {
        return createTintedDrawable(ContextCompat.getDrawable(context, drawable), color);
    }

    @CheckResult
    @Nullable
    public static Drawable createTintedDrawable(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable == null) return null;
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                && drawable instanceof VectorDrawable) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            return drawable;
        }
        drawable = DrawableCompat.wrap(drawable.mutate());
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        DrawableCompat.setTint(drawable, color);
        return drawable;
    }

    private static void tintImageView(Object target, Field field, int tintColor) throws Exception {
        field.setAccessible(true);
        final ImageView imageView = (ImageView) field.get(target);
        if (imageView == null) return;
        if (imageView.getDrawable() != null)
            imageView.setImageDrawable(createTintedDrawable(imageView.getDrawable(), tintColor));
    }

    public static void themeSearchView(
            @NonNull Toolbar toolbar,
            @NonNull SearchView searchView,
            @ColorInt int tintColor,
            @ColorInt int tintColorCursor) {
        final Class<?> cls = searchView.getClass();
        try {
            final Field mCollapseIconField = toolbar.getClass().getDeclaredField("mCollapseIcon");
            mCollapseIconField.setAccessible(true);
            final Drawable drawable = (Drawable) mCollapseIconField.get(toolbar);
            final Drawable iconBack =
                    Utils.getDrawableWithShadow(toolbar.getContext(), R.drawable.ic_action_arrow_back);
            if (drawable != null)
                mCollapseIconField.set(toolbar, createTintedDrawable(iconBack, tintColor));

            final Field mSearchSrcTextViewField = cls.getDeclaredField("mSearchSrcTextView");
            mSearchSrcTextViewField.setAccessible(true);
            final EditText mSearchSrcTextView = (EditText) mSearchSrcTextViewField.get(searchView);
            mSearchSrcTextView.setTextColor(tintColor);
            mSearchSrcTextView.setHintTextColor(ColorUtils.adjustAlpha(tintColor, 0.6f));
            setCursorTint(mSearchSrcTextView, tintColorCursor);

            Field field = cls.getDeclaredField("mSearchButton");
            tintImageView(searchView, field, tintColor);
            field = cls.getDeclaredField("mGoButton");
            tintImageView(searchView, field, tintColor);
            field = cls.getDeclaredField("mCloseButton");
            tintImageView(searchView, field, tintColor);
            field = cls.getDeclaredField("mVoiceButton");
            tintImageView(searchView, field, tintColor);
            field = cls.getDeclaredField("mCollapsedIcon");
            tintImageView(searchView, field, ColorUtils.adjustAlpha(tintColor, 0.6f));
            field = cls.getDeclaredField("mSearchHintIcon");
            field.setAccessible(true);
            Drawable hintIcon = (Drawable) field.get(searchView);
            hintIcon.setBounds(0, 0, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCursorTint(@NonNull EditText editText, @ColorInt int color) {
        // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            drawables[0] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[0] = createTintedDrawable(drawables[0], color);
            drawables[1] = ContextCompat.getDrawable(editText.getContext(), mCursorDrawableRes);
            drawables[1] = createTintedDrawable(drawables[1], color);
            fCursorDrawable.set(editor, drawables);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ColorInt
    public static int getDisabledColor(Context context) {
        final int primaryColor = ResolveUtils.resolveColor(context, android.R.attr.textColorPrimary);
        final int disabledColor = ColorUtils.isDark(primaryColor) ? Color.BLACK : Color.WHITE;
        return ColorUtils.adjustAlpha(disabledColor, 0.2f);
    }

    @ColorInt
    public static int getToolbarColor(Context context, boolean textColor) {
        @ColorInt final int toolbarColor = ResolveUtils.resolveColor(context, R.attr.toolbarColor);
        @ColorInt int titleColor;
        @ColorInt int iconColor;

        if (ColorUtils.isColorLight(toolbarColor)) {
            titleColor = context.getResources().getColor(R.color.toolbar_dark_title_color);
            iconColor = context.getResources().getColor(R.color.toolbar_dark_icons_color);
        } else {
            titleColor = context.getResources().getColor(R.color.toolbar_light_title_color);
            iconColor = context.getResources().getColor(R.color.toolbar_light_icons_color);
        }
        if (textColor) return titleColor;
        else return iconColor;
    }

    @ColorInt
    public static int getPopupColor(Context context, boolean textColor) {
        @ColorInt final int popupColor = ResolveUtils.resolveColor(context, android.R.attr.popupBackground);
        @ColorInt int titleColor;
        @ColorInt int iconColor;

        if (ColorUtils.isColorLight(popupColor)) {
            titleColor = context.getResources().getColor(R.color.toolbar_dark_title_color);
            iconColor = context.getResources().getColor(R.color.toolbar_dark_icons_color);
        } else {
            titleColor = context.getResources().getColor(R.color.toolbar_light_title_color);
            iconColor = context.getResources().getColor(R.color.toolbar_light_icons_color);
        }
        if (textColor) return titleColor;
        else return iconColor;
    }

    private TintUtils() {
    }
}
