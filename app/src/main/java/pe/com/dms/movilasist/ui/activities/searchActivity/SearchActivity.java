package pe.com.dms.movilasist.ui.activities.searchActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.SelectorType;
import pe.com.dms.movilasist.databinding.ActivitySearchBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.OnSelectorListener;
import pe.com.dms.movilasist.model.filterModel.ResultListApobSolic;
import pe.com.dms.movilasist.model.filterModel.ResultListMarc;
import pe.com.dms.movilasist.model.filterModel.ResultListMarcPers;
import pe.com.dms.movilasist.model.filterModel.ResultListSolicPers;
import pe.com.dms.movilasist.ui.activities.base.BaseToolbarActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroAproSolic.FiltroAprobSolicFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarc.FiltroListMarcFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroListMarcPers.FiltroListMarcPersFragment;
import pe.com.dms.movilasist.ui.fragment.filtersFragments.filtroSolicPerm.FiltroListSolicPermisoFragment;
import pe.com.dms.movilasist.util.CustomToast;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.Utils;

public class SearchActivity extends BaseToolbarActivity
        implements OnSelectorListener,
        OnFragmentInteractionListener,
        ISearchActivityContract.View {


    private static final String TAG = SearchActivity.class.getSimpleName();

    private ProgressDialog progress;
    private final Handler handler = new Handler();
    private ProgressDialog horizontalProgressDialog;
    private Boolean erroresDetallados = false;

    private ActivitySearchBinding binding;

    private String mTitle, mSubTitle;
    @SelectorType
    private int mSelectorType;
    private int mSelectorFilter;

    private FragmentManager mFragManager;

    @Override
    public Toolbar getToolbar() {
        return binding.includeToolbar.toolbar;
    }

    @Override
    public void startOverridePendingTransaction() {
        //overridePendingTransition(R.anim.transition_slide_bottom_in, R.anim.transition_slide_top_out);
    }

    @Override
    public void finishOverridePendingTransaction() {
        //overridePendingTransition(R.anim.transition_slide_top_in, R.anim.transition_slide_bottom_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getActivityComponent().inject(this);

        if (getIntent() != null) {
            mTitle = (String) getIntent().getStringExtra(Constants.Intent.EXTRA_TITLE);
            mSubTitle = (String) getIntent().getStringExtra(Constants.Intent.EXTRA_SUB_TITLE);
            mSelectorType = (int) getIntent().getIntExtra(Constants.Intent.EXTRA_SELECTOR_TYPE, -1);
            mSelectorFilter = (int) getIntent().getIntExtra(Constants.Intent.EXTRA_SELECTOR_FILTER, -1);
        }

        mFragManager = getSupportFragmentManager();

        setupToolbar();
        init();

        if (savedInstanceState == null) {
            switch (mSelectorType) {
                case SelectorType.LIST_MARC:
                    onAddFragmentToStack(
                            FiltroListMarcFragment.newInstance(),
                            null,
                            null,
                            false,
                            false,
                            false,
                            FiltroListMarcFragment.TAG);
                    break;
                case SelectorType.LIST_MARC_PERS:
                    onAddFragmentToStack(
                            FiltroListMarcPersFragment.newInstance(),
                            null,
                            null,
                            false,
                            false,
                            false,
                            FiltroListMarcPersFragment.TAG);
                    break;
                case SelectorType.LIST_SOLIC_PERS:
                    onAddFragmentToStack(
                            FiltroListSolicPermisoFragment.newInstance(),
                            null,
                            null,
                            false,
                            false,
                            false,
                            FiltroListSolicPermisoFragment.TAG);
                    break;
                case SelectorType.LIST_APROB_SOLIC:
                    onAddFragmentToStack(
                            FiltroAprobSolicFragment.newInstance(),
                            null,
                            null,
                            false,
                            false,
                            false,
                            FiltroAprobSolicFragment.TAG);
                    break;
            }
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Utils.recursivePopBackStack(mFragManager)) {
            return;
        }
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED, null);
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        binding.includeToolbar.toolbarTitle.setText(title);
    }

    public void setSubTitle(final CharSequence subTitle) {
        binding.includeToolbar.toolbar.setSubtitle(subTitle);
        if (binding.includeToolbar.toolbarSubtitle != null)
            binding.includeToolbar.toolbarSubtitle.setText(subTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        menu.findItem(R.id.menu_search).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //mMenu.findItem()
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_CANCELED, null);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onSelected(Object object) {
        Intent result = new Intent();
        switch (mSelectorType) {
            case SelectorType.LIST_MARC:
                if (object instanceof ResultListMarc)
                    result.putExtra(Constants.Intent.EXTRA_SELECTOR_FILTER,
                            (ResultListMarc) object);
                break;
            case SelectorType.LIST_MARC_PERS:
                if (object instanceof ResultListMarcPers)
                    result.putExtra(Constants.Intent.EXTRA_SELECTOR_FILTER,
                            (ResultListMarcPers) object);
                break;
            case SelectorType.LIST_SOLIC_PERS:
                if (object instanceof ResultListSolicPers)
                    result.putExtra(Constants.Intent.EXTRA_SELECTOR_FILTER,
                            (ResultListSolicPers) object);
                break;
            case SelectorType.LIST_APROB_SOLIC:
                if (object instanceof ResultListApobSolic)
                    result.putExtra(Constants.Intent.EXTRA_SELECTOR_FILTER,
                            (ResultListApobSolic) object);
                break;
        }
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbar.setPadding(
                binding.includeToolbar.toolbar.getPaddingLeft(),
                binding.includeToolbar.toolbar.getPaddingTop(),
                binding.includeToolbar.toolbar.getPaddingRight(),
                binding.includeToolbar.toolbar.getPaddingBottom());
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void init() {
        setTitle(mTitle);
        if (!TextUtils.isEmpty(String.valueOf(mSubTitle))) {
            binding.includeToolbar.toolbarSubtitle.setVisibility(View.VISIBLE);
            setSubTitle(mSubTitle);
        } else {
            binding.includeToolbar.toolbarSubtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void closed() {
        finish();
    }

    @Override
    public void setColorToolbar(int color) {
        binding.includeToolbar.toolbar.setBackgroundColor(color);
    }

    @Override
    public void setErroresDetallados(Boolean erroresDetallados) {
        this.erroresDetallados = erroresDetallados;
    }

    @Override
    public void showSuccessMessage(String mensaje) {
        hiddenProgressbar();
        hiddenProgressPercentage();

        new CustomToast.Builder(this, getLayoutInflater(), mensaje)
                .setBackgroundColor(this.getResources().getColor(R.color.colorSuccess))
                .setIcon(this.getResources().getDrawable(R.drawable.ic_action_done_all))
                .build().show();
    }

    @Override
    public void showProgressPercentage(String titulo, String mensaje) {
        horizontalProgressDialog = new ProgressDialog(this);
        horizontalProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        horizontalProgressDialog.setTitle(titulo);
        horizontalProgressDialog.setMessage(mensaje);
        horizontalProgressDialog.setCancelable(false);
        horizontalProgressDialog.setMax(100);
        horizontalProgressDialog.show();
    }

    @Override
    public void showWarningMessage(String mensaje) {
        hiddenProgressbar();
        hiddenProgressPercentage();
        new CustomToast.Builder(this, getLayoutInflater(), mensaje).build().show();
    }

    @Override
    public void showErrorMessage(String mensaje, String detalle) {
        hiddenProgressbar();
        hiddenProgressPercentage();
        if (erroresDetallados) {
            new DefaultDialog.Builder(this)
                    .title(getMessage(R.string.error))
                    .message(detalle)
                    .setIcon(R.drawable.ic_help_outline)
                    .build().createDialog().show();
        } else {
            new CustomToast.Builder(this, getLayoutInflater(), mensaje)
                    .setBackgroundColor(this.getResources().getColor(R.color.colorError))
                    .setIcon(this.getResources().getDrawable(R.drawable.ic_action_error))
                    .setDuration(Toast.LENGTH_LONG)
                    .build().show();
        }
    }

    @Override
    public void updateProgressbar(String mensaje) {
        if (progress != null) {
            progress.setMessage(mensaje);
        }
    }

    @Override
    public String getMessage(int id) {
        return getString(id);
    }

    @Override
    public void hiddenProgressPercentage() {
        if (horizontalProgressDialog != null) {
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                }
            }, 60);

            horizontalProgressDialog.dismiss();
            horizontalProgressDialog.setMessage("");
        }
    }

    @Override
    public void showProgressbar(String titulo, String mensaje) {
        progress = ProgressDialog.show(this, titulo, mensaje, true);
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 60);
    }

    @Override
    public void hiddenProgressbar() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Override
    public void updatePercentage(int progress, String message) {
        horizontalProgressDialog.setProgress(progress);
        horizontalProgressDialog.setMessage(message);
    }

    public Handler getHandler() {
        return handler;
    }

}
