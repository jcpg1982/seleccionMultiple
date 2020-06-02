package pe.com.dms.movilasist.ui.activities.base;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;


public abstract class BaseFragmentToolbarActivity extends BaseFragmentActivity
        implements OnFragmentInteractionListener {

    public abstract Toolbar getToolbar();

    public abstract TextView getToolbarTitle();

    public abstract TextView getToolbarSubTitle();

    @Override
    protected void onFragmentBackStackChanged() {
        setToolbarNavigation();
    }

    public void setTitle(@StringRes int titleRes) {
        setTitle(getResources().getString(titleRes));
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        getToolbar().setTitle(title);
        if (getToolbarTitle() != null)
            getToolbarTitle().setText(title);
    }

    public void setSubTitle(@StringRes int subTitleRes) {
        setSubTitle(getResources().getString(subTitleRes));
    }

    public void setSubTitle(final CharSequence subTitle) {
        getToolbar().setSubtitle(subTitle);
        if (getToolbarSubTitle() != null)
            getToolbarSubTitle().setText(subTitle);
    }

    public void setIcon(@DrawableRes int drawableRes) {
        setIcon(getResources().getDrawable(drawableRes));
    }

    public void setIcon(final Drawable drawable) {
        if (getToolbar() != null)
            getToolbar().setNavigationIcon(drawable);
    }

    private void setToolbarHomeAsUp(boolean isHome) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(!isHome);
        }
    }

    private void setToolbarNavigation() {
        @ColorInt int tintColor = TintUtils.getToolbarColor(this, true);
        Drawable icon;
        CharSequence title, subTitle;

        if (mFragManager.getBackStackEntryCount() > 0) {
            setToolbarHomeAsUp(false);
            icon = getResources().getDrawable(R.drawable.ic_action_arrow_back);
            setIcon(TintUtils.createTintedDrawable(icon.mutate(), getResources().getColor(R.color.colorCardDark)));
            getToolbar().setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                        }
                    });

            //https://stackoverflow.com/a/39349524
            int lastBackStackEntryCount = mFragManager.getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry lastBackStackEntry =
                    getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

            title = lastBackStackEntry.getBreadCrumbTitle();
            setTitle(title);

            if (getToolbarSubTitle() != null) {
                subTitle = lastBackStackEntry.getBreadCrumbShortTitle();
                if (!TextUtils.isEmpty(String.valueOf(subTitle))) {
                    getToolbarSubTitle().setVisibility(View.VISIBLE);
                    setSubTitle(subTitle);
                } else {
                    getToolbarSubTitle().setVisibility(View.GONE);
                    setSubTitle(null);
                }
            }
            return;
        }

        setToolbarHomeAsUp(true);
        setIcon(null);
        getToolbar().setNavigationOnClickListener(null);

        title = null;
        setTitle(title);

        if (getToolbarSubTitle() != null) {
            subTitle = null;
            if (!TextUtils.isEmpty(String.valueOf(subTitle))) {
                getToolbarSubTitle().setVisibility(View.VISIBLE);
                setSubTitle(subTitle);
            } else {
                getToolbarSubTitle().setVisibility(View.GONE);
                setSubTitle(null);
            }
        }
    }
}