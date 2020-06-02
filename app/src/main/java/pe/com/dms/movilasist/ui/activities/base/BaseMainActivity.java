package pe.com.dms.movilasist.ui.activities.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.config.Config;
import pe.com.dms.movilasist.util.ColorUtils;
import pe.com.dms.movilasist.util.ResolveUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.ViewUtils;

public abstract class BaseMainActivity extends BaseActivity {

    public abstract Toolbar getToolbar();

    @Override
    protected void onStart() {
        super.onStart();

        final Toolbar toolbar = getToolbar();
        if (toolbar != null) {

            @ColorInt final int toolbarColor = ResolveUtils.resolveColor(this, R.attr.toolbarColor);
            @ColorInt int tintTextColor = TintUtils.getToolbarColor(this, true);
            @ColorInt int tintIconColor = TintUtils.getToolbarColor(this, false);

            toolbar.setBackgroundColor(toolbarColor);
            toolbar.setTitleTextColor(tintTextColor);
            ViewUtils.setOverflowButtonColor(this, getResources().getColor(R.color.colorCardDark));

            setLogo(toolbar, toolbarColor);

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
        if (Config.get().showIconPopupToolbar()) {
            if (menu instanceof MenuBuilder) {
                ((MenuBuilder) menu).setOptionalIconsVisible(true);
            }
        }
        themeMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setLogo(final View view, int toolbarColor) {
        try {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    setLogo(child, toolbarColor);
                }
            } else if (view instanceof ImageView) {
                ImageView logo = (ImageView) view;
                /*if (ColorUtils.isColorLight(toolbarColor)) {
                    if (logo.getBackground() == null)
                        logo.setImageResource(R.drawable.logo_light_toolbar);
                } else {
                    if (logo.getBackground() == null)
                        logo.setImageResource(R.drawable.logo_dark);
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                menuItem.setIcon(TintUtils.createTintedDrawable(menuItem.getIcon().mutate(),
                        tintColorPopup));
        }

        MenuItem homeItem = menu.findItem(R.id.menu_save);
        if (homeItem != null && homeItem.getIcon() != null)
            homeItem.setIcon(TintUtils.createTintedDrawable(homeItem.getIcon().mutate(),
                    tintColorToolbar));
    }
}