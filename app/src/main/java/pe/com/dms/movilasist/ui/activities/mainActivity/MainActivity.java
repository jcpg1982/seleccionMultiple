package pe.com.dms.movilasist.ui.activities.mainActivity;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.config.Config;
import pe.com.dms.movilasist.databinding.ActivityMainBinding;
import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.interfaces.OnMoveFragmentListener;
import pe.com.dms.movilasist.interfaces.OnSetToolbarListener;
import pe.com.dms.movilasist.receivers.NetworkReceiver;
import pe.com.dms.movilasist.services.SendDataService;
import pe.com.dms.movilasist.ui.activities.base.BaseMainActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.drawer.NavigationDrawerFragment;
import pe.com.dms.movilasist.util.CustomToast;
import pe.com.dms.movilasist.util.TextUtils;
import pe.com.dms.movilasist.util.TintUtils;
import pe.com.dms.movilasist.util.Utils;

public class MainActivity extends BaseMainActivity
        implements IMainContract.View,
        NavigationDrawerFragment.OnBridgeToParent,
        OnSetToolbarListener,
        OnFragmentInteractionListener,
        OnMoveFragmentListener,
        Observer {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    private FragmentManager mFragManager;
    private NavigationDrawerFragment mNavigationFragment;
    private ProgressDialog progress;
    private final Handler handler = new Handler();
    private ProgressDialog horizontalProgressDialog;
    private Boolean erroresDetallados = false;

    private boolean navigationDrawerEnabled = true;

    private NetworkReceiver mNetworkReceiver = new NetworkReceiver();

    @Inject
    MainPresenter presenter;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Toolbar getToolbar() {
        return binding.rootLayout.includeToolbar.toolbar;
    }

    @Override
    public void startOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_right_in, R.anim.transition_slide_left_out);
    }

    @Override
    public void finishOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_left_in, R.anim.transition_slide_right_out);
    }

    @Override
    public void update(Observable o, Object data) {
        if (data instanceof Intent) {
            Intent intent = (Intent) data;
            boolean connected = intent.getBooleanExtra(Constants.Intent.EXTRA_CONNECTED, true);
            if (connected) {
                binding.rootLayout.containerConnection.textConnection.setBackgroundColor(getResources().getColor(R.color.success));
                binding.rootLayout.containerConnection.textConnection.setText(R.string.internet_connection);
                iniciarServicio();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.rootLayout.containerConnection.textConnection.setVisibility(View.GONE);
                    }
                }, 1300);
            } else {
                binding.rootLayout.containerConnection.textConnection.setBackgroundColor(getResources().getColor(R.color.error));
                binding.rootLayout.containerConnection.textConnection.setText(R.string.no_internet_connection);
                binding.rootLayout.containerConnection.textConnection.setVisibility(View.VISIBLE);
            }
        }
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
    public Fragment onMoveFragmentTo(int position) {
        return mNavigationFragment.onMoveFragmentTo(position);
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        binding.rootLayout.includeToolbar.toolbar.setTitle(title);
        if (binding.rootLayout.includeToolbar.toolbarTitle != null)
            binding.rootLayout.includeToolbar.toolbarTitle.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        setTitle(getResources().getString(titleRes));
    }

    @Override
    public void onSetTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onSetSubTitle(String subTitle) {
        if (!TextUtils.isEmpty(String.valueOf(subTitle))) {
            binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.VISIBLE);
            setSubTitle(subTitle);
        } else {
            binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (!actionBar.isShowing())
            actionBar.show();
    }

    @Override
    public void onHideToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar.isShowing())
            actionBar.hide();
    }

    @Override
    public void closedMain() {
        this.finish();
    }

    @Override
    public void goToHome() {
        while (mFragManager.getBackStackEntryCount() >= 1) {
            onBackPressed();
            if (popBackStackImmediate()) continue;
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
    public void updateProgressbar(String mensaje) {
        if (progress != null) {
            progress.setMessage(mensaje);
        }
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
    public void updatePercentage(int progress, String message) {
        horizontalProgressDialog.setProgress(progress);
        horizontalProgressDialog.setMessage(message);
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
    public void showWarningMessage(String mensaje) {
        hiddenProgressbar();
        hiddenProgressPercentage();
        new CustomToast.Builder(this, getLayoutInflater(), mensaje).build().show();
    }

    @Override
    public void showErrorMessage(String mensaje, String mensajeDetallado) {
        hiddenProgressbar();
        hiddenProgressPercentage();
        if (erroresDetallados) {
            new DefaultDialog.Builder(this)
                    .title(getMessage(R.string.error))
                    .message(mensajeDetallado)
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

    public Handler getHandler() {
        return handler;
    }

    @Override
    public String getMessage(int id) {
        return getString(id);
    }

    @Override
    public void setErroresDetallados(Boolean erroresDetallados) {
        this.erroresDetallados = erroresDetallados;
    }

    @Override
    public void setToolbarNavigationFromDrawer(int position) {
        setToolbarNavigation(position);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getActivityComponent().inject(this);

        mFragManager = getSupportFragmentManager();

        setUpToolbar();
        initNavigationFragment();
        configEnabledNavigationDrawer();
        initEvents();

        iniciarServicio();

        presenter.attachView(this);

        NetworkReceiver.ObservableObject.getInstance().addObserver(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (Utils.recursivePopBackStack(mFragManager))
            return;

        if (mNavigationFragment.onBackPressed())
            return;

        super.onBackPressed();
    }

    private void setToolbarHomeAsUp(boolean isHome) {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(!isHome);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.rootLayout.includeToolbar.toolbar);
        setToolbarHomeAsUp(true);
        binding.rootLayout.includeToolbar.toolbar.setPadding(
                binding.rootLayout.includeToolbar.toolbar.getPaddingLeft(),
                binding.rootLayout.includeToolbar.toolbar.getPaddingTop(),
                binding.rootLayout.includeToolbar.toolbar.getPaddingRight(),
                binding.rootLayout.includeToolbar.toolbar.getPaddingBottom());
    }

    private void initNavigationFragment() {
        mNavigationFragment = (NavigationDrawerFragment)
                mFragManager.findFragmentById(R.id.fragment_navigation_drawer);

        mNavigationFragment.setToolbar(binding.rootLayout.includeToolbar.toolbar);
        mNavigationFragment.setDrawer(binding.drawerLayout);
        mNavigationFragment.setFragmentManager(mFragManager);
        mNavigationFragment.initData();
    }

    private void configEnabledNavigationDrawer() {
        navigationDrawerEnabled = Config.get().navigationDrawerEnabled();
        if (!navigationDrawerEnabled) {
            binding.navigationView.setVisibility(View.GONE);
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            binding.navigationView.setVisibility(View.VISIBLE);
        }
    }

    private void initEvents() {
        mFragManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setToolbarNavigation(mNavigationFragment.getSelectedPosition());
                    }
                });
    }

    private void setToolbarNavigation(int position) {
        @ColorInt int tintColor = TintUtils.getToolbarColor(this, false);
        Drawable icon;
        CharSequence title, subTitle;

        if (mFragManager.getBackStackEntryCount() > 0) {
            setToolbarHomeAsUp(false);
            icon = getResources().getDrawable(R.drawable.ic_action_arrow_back);
            setIcon(TintUtils.createTintedDrawable(icon.mutate(),
                    getResources().getColor(R.color.colorCardDark)));
            binding.rootLayout.includeToolbar.toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onBackPressed();
                            //onRemoveFragmentToStack(true);
                        }
                    });

            //https://stackoverflow.com/a/39349524
            int lastBackStackEntryCount = getSupportFragmentManager().getBackStackEntryCount() - 1;
            FragmentManager.BackStackEntry lastBackStackEntry =
                    getSupportFragmentManager().getBackStackEntryAt(lastBackStackEntryCount);

            title = lastBackStackEntry.getBreadCrumbTitle();
            subTitle = lastBackStackEntry.getBreadCrumbShortTitle();

            setTitle(title);
            if (!TextUtils.isEmpty(String.valueOf(subTitle))) {
                binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.VISIBLE);
                setSubTitle(subTitle);
            } else {
                binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.GONE);
                setSubTitle(null);
            }

            return;
        }

        setToolbarHomeAsUp(true);
        if (navigationDrawerEnabled) {
            icon = getResources().getDrawable(R.drawable.ic_action_menu);
            setIcon(TintUtils.createTintedDrawable(icon.mutate(),
                    getResources().getColor(R.color.colorCardDark)));
            binding.rootLayout.includeToolbar.toolbar.setNavigationOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            binding.drawerLayout.openDrawer(GravityCompat.START);
                        }
                    });
        } else {
            setIcon(null);
            binding.rootLayout.includeToolbar.toolbar.setNavigationOnClickListener(null);
        }

        title = mNavigationFragment.getItemList().get(position).getTitle();
        subTitle = mNavigationFragment.getItemList().get(position).getSubtitle();

        setTitle(title);
        if (!TextUtils.isEmpty(String.valueOf(subTitle))) {
            binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.VISIBLE);
            setSubTitle(subTitle);
        } else {
            binding.rootLayout.includeToolbar.toolbarSubtitle.setVisibility(View.GONE);
            setSubTitle(null);
        }
    }

    public void setIcon(final Drawable drawable) {
        if (binding.rootLayout.includeToolbar.toolbar != null)
            binding.rootLayout.includeToolbar.toolbar.setNavigationIcon(drawable);
    }

    public void setSubTitle(final CharSequence subTitle) {
        binding.rootLayout.includeToolbar.toolbar.setSubtitle(subTitle);
        if (binding.rootLayout.includeToolbar.toolbarSubtitle != null)
            binding.rootLayout.includeToolbar.toolbarSubtitle.setText(subTitle);
    }

    public boolean popBackStackImmediate() {
        if (mFragManager == null) return false;
        if (mFragManager.getBackStackEntryCount() == 0) {
            return false;
        } else {
            mFragManager.popBackStackImmediate();
            return true;
        }
    }

    private void iniciarServicio() {
        if (!isMyServiceRunning(SendDataService.class)) {
            Intent envio = new Intent(getApplicationContext(), SendDataService.class);
            startService(envio);
        }
        /*Intent envio = new Intent(getApplicationContext(), SendDataService.class);
        startService(envio);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iniciarServicio();
            }
        }, 1800000);*/
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    //Fuente:https://www.iteramos.com/pregunta/1719/como-comprobar-si-un-servicio-esta-en-ejecucion-en-android
}
