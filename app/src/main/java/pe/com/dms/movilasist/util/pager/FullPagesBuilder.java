package pe.com.dms.movilasist.util.pager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Iterator;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.util.TintUtils;

public class FullPagesBuilder implements Iterable<FullPagesBuilder.Page> {

    private final ArrayList<Page> mPages;

    public FullPagesBuilder(int expectedSize) {
        mPages = new ArrayList<>(expectedSize);
    }

    public void add(@NonNull Page page) {
        mPages.add(page);
    }

    public Page get(int index) {
        return mPages.get(index);
    }

    public int size() {
        return mPages.size();
    }

    @Override
    public Iterator<Page> iterator() {
        return mPages.iterator();
    }

    public static class Page {

        public final StateListDrawable icon;
        public final String titleToolbar;
        public final String titleTab;
        @NonNull
        public final Fragment fragment;

        public Page(Context context,
                    @DrawableRes int iconRes,
                    @StringRes int titleToolbarRes,
                    @StringRes int titleTabRes,
                    @NonNull Fragment fragment) {
            this(getDrawableRes(context, iconRes),
                    getStringRes(context, titleToolbarRes),
                    getStringRes(context, titleTabRes),
                    fragment);
        }

        public Page(StateListDrawable icon, String titleToolbar, String titleTab,
                    @NonNull Fragment fragment) {
            this.icon = icon;
            this.titleToolbar = titleToolbar;
            this.titleTab = titleTab;
            this.fragment = fragment;
        }

        private static StateListDrawable getDrawableRes(Context context, @DrawableRes int iconRes) {
            if (iconRes == -1) return null;
            Drawable drawable = context.getResources().getDrawable(iconRes);
            if (drawable instanceof StateListDrawable) {
                return (StateListDrawable) drawable;
            } else {
                return createDrawableSelector(context, drawable);
            }
        }

        private static StateListDrawable createDrawableSelector(Context context, Drawable drawable) {
            final int normalColor = context.getResources().getColor(android.R.color.white);
            final int selectedColor = context.getResources().getColor(R.color.colorAccent);

            final StateListDrawable baseSelector = new StateListDrawable();
            baseSelector.setExitFadeDuration(150);
            baseSelector.addState(
                    new int[]{android.R.attr.state_selected},
                    TintUtils.createTintedDrawable(drawable.mutate(), selectedColor));
            baseSelector.addState(
                    new int[]{}, TintUtils.createTintedDrawable(drawable.mutate(), normalColor));
            return baseSelector;
        }

        private static String getStringRes(Context context, @StringRes int stringRes) {
            return stringRes != -1 ? context.getResources().getString(stringRes) : null;
        }
    }
}