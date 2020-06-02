package pe.com.dms.movilasist.ui.activities.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.util.Utils;

public abstract class BaseFragmentActivity extends BaseActivity
        implements OnFragmentInteractionListener {

    protected FragmentManager mFragManager;

    protected abstract void onFragmentBackStackChanged();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragManager = getSupportFragmentManager();
        initEvents();
    }

    @Override
    public void onAddFragmentToStack(
            @NonNull Fragment fragment,
            CharSequence title,
            CharSequence subTitle,
            boolean replaceFragment,
            boolean addToBackStack,
            boolean isAnimated,
            @Nullable String tag) {
        FragmentTransaction ft = mFragManager.beginTransaction();

        ft.setBreadCrumbTitle(title);
        ft.setBreadCrumbShortTitle(subTitle);

        if (isAnimated) {
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.setCustomAnimations(
                    R.anim.transition_slide_right_in,
                    R.anim.transition_slide_left_out,
                    android.R.anim.slide_in_left,
                    R.anim.transition_slide_right_out);
        }

        if (replaceFragment)
            ft.replace(R.id.fragment_container, fragment, tag);
        else
            ft.add(R.id.fragment_container, fragment, tag);

       /* if (newFragment.isAdded()) {
            ft.hide(currentFragment)
                    .show(newFragment);
        } else {
            if (currentFragment != null) {
                ft.hide(currentFragment)
                        .add(R.id.fragment_container, newFragment, tag);
            } else {
                ft.add(R.id.fragment_container, newFragment, tag);
            }
        }*/

        if (addToBackStack) ft.addToBackStack(null);

        try {
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onRemoveFragmentToStack(boolean withBackPressed) {
        if (withBackPressed) {
            if (Utils.recursivePopBackStack(mFragManager)) return;
            super.onBackPressed();
        } else {
            Utils.recursivePopBackStack(mFragManager);
        }
    }

    private void initEvents() {
        mFragManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        onFragmentBackStackChanged();
                    }
                });
    }
}