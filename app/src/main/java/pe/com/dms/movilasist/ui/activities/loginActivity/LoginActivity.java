package pe.com.dms.movilasist.ui.activities.loginActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.ActivityLoginBinding;
import pe.com.dms.movilasist.ui.activities.base.BaseFragmentToolbarActivity;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.ui.fragment.configFragment.ConfigPagerFragment;
import pe.com.dms.movilasist.ui.fragment.loginFragment.LoginFragment;
import pe.com.dms.movilasist.util.CustomToast;
import pe.com.dms.movilasist.util.Utils;

public class LoginActivity extends BaseFragmentToolbarActivity
        implements ILoginActivityContract.View {

    private ActivityLoginBinding binding;

    private ProgressDialog progress;
    private final Handler handler = new Handler();
    private ProgressDialog horizontalProgressDialog;
    private Boolean erroresDetallados = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolbar();
        if (savedInstanceState == null) {
            onAddFragmentToStack(LoginFragment.newInstance(),
                    null,
                    null,
                    false,
                    false,
                    false,
                    LoginFragment.TAG);
        }
    }

    @Override
    public void onBackPressed() {
        if (Utils.recursivePopBackStack(mFragManager))
            return;
        super.onBackPressed();
    }

    @Override
    public Toolbar getToolbar() {
        return binding.includeToolbarTransparent.toolbar;
    }

    @Override
    public TextView getToolbarTitle() {
        return binding.includeToolbarTransparent.toolbarTitle;
    }

    @Override
    public TextView getToolbarSubTitle() {
        return binding.includeToolbarTransparent.toolbarSubtitle;
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
}
