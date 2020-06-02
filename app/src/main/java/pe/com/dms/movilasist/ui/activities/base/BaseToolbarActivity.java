package pe.com.dms.movilasist.ui.activities.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.Toolbar;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.util.ResolveUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.ViewUtils;

public abstract class BaseToolbarActivity extends BaseActivity {

    public abstract Toolbar getToolbar();

    @Override
    protected void onStart() {
        super.onStart();

        final Toolbar toolbar = getToolbar();
        if (toolbar != null) {

            @ColorInt int toolbarColor = ResolveUtils.resolveColor(this, R.attr.toolbarColor);
            @ColorInt int tintTextColor = TintUtils.getToolbarColor(this, true);
            @ColorInt int tintIconColor = TintUtils.getToolbarColor(this, false);

            toolbar.setBackgroundColor(toolbarColor);
            toolbar.setTitleTextColor(tintTextColor);
            ViewUtils.setOverflowButtonColor(this, getResources().getColor(R.color.colorCardDark));

            for (int i = 0; i < toolbar.getChildCount(); i++) {
                if (toolbar.getChildAt(i) instanceof TextView) {
                    TextView title = (TextView) toolbar.getChildAt(i);
                    title.setTextColor(tintTextColor);
                }
            }

            /*if (ColorUtils.isColorLight(toolbarColor)) {
                toolbar.setPopupTheme(R.style.AppTheme_Popup_Dark);
            } else {
                toolbar.setPopupTheme(R.style.AppTheme_Popup_Light);
            }*/
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        themeMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Configura el tema para el menu.
     *
     * @param context : Contexto.
     * @param menu    : Menu que se inflará en la vista.
     */
    public static void themeMenu(Context context, Menu menu) {
        @ColorInt int tintColorPopup = TintUtils.getPopupColor(context, false);
        @ColorInt int tintColorToolbar = TintUtils.getToolbarColor(context, false);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            if (menuItem.getIcon() != null)
                menuItem.setIcon(
                        TintUtils.createTintedDrawable(menuItem.getIcon().mutate(), tintColorPopup));
        }
    }
}