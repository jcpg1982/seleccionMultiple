package pe.com.dms.movilasist.ui.activities.configActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.jetbrains.annotations.NotNull;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.annotacion.TypeActivity;
import pe.com.dms.movilasist.databinding.ActivityConfigBinding;
import pe.com.dms.movilasist.interfaces.OnFragmentInteractionListener;
import pe.com.dms.movilasist.ui.activities.base.BaseMainActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.configFragment.configIpFragment.ConfigIpFragment;
import pe.com.dms.movilasist.util.CustomToast;
import pe.com.dms.movilasist.util.Utils;

public class ConfigActivity extends BaseMainActivity
        implements IConfigActivityContract.View,
        OnFragmentInteractionListener {

    String TAG = ConfigActivity.class.getSimpleName();

    private ActivityConfigBinding binding;

    private ProgressDialog progress;
    private final Handler handler = new Handler();
    private ProgressDialog horizontalProgressDialog;
    private Boolean erroresDetallados = false;
    private FragmentManager mFragManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        binding = ActivityConfigBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        mFragManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            onAddFragmentToStack(
                    ConfigIpFragment.newInstance(TypeActivity.CONFIG),
                    "Configuración IP",
                    null,
                    false,
                    false,
                    false,
                    ConfigIpFragment.TAG);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        ConfigIpFragment fragment = (ConfigIpFragment)
                getSupportFragmentManager().findFragmentByTag(ConfigIpFragment.TAG);
        if (fragment != null && fragment.onBackPressed())
            return;

        if (Utils.recursivePopBackStack(mFragManager))
            return;

        super.onBackPressed();
    }

    @Override
    public Toolbar getToolbar() {
        return binding.includeToolbarTransparent.toolbar;
    }

    @Override
    public void startOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_right_in, R.anim.transition_slide_left_out);
    }

    @Override
    public void finishOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_left_in, R.anim.transition_slide_right_out);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.includeToolbarTransparent.toolbar);
        binding.includeToolbarTransparent.toolbar.setPadding(
                binding.includeToolbarTransparent.toolbar.getPaddingLeft(),
                binding.includeToolbarTransparent.toolbar.getPaddingTop(),
                binding.includeToolbarTransparent.toolbar.getPaddingRight(),
                binding.includeToolbarTransparent.toolbar.getPaddingBottom());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void closed() {
        finish();
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
}
