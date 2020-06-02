package pe.com.dms.movilasist.ui.activities.splashActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.databinding.ActivitySplashBinding;
import pe.com.dms.movilasist.ui.activities.base.BaseActivity;
import pe.com.dms.movilasist.ui.activities.loginActivity.LoginActivity;

public class SplashActivity extends BaseActivity implements ISplashContract.View {

    @Inject
    SplashPresenter presenter;

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter.createTypeMarcacion();

        presenter.attachView(this);

    }

    @Override
    public void moveToLogin() {
        overridePendingTransition(R.anim.transition_slide_bottom_in, R.anim.transition_slide_left_out);
        Intent Intent = new Intent(this, LoginActivity.class);
        startActivity(Intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void setErroresDetallados(Boolean erroresDetallados) {

    }

    @Override
    public void showSuccessMessage(String mensaje) {

    }

    @Override
    public void showProgressPercentage(String titulo, String mensaje) {

    }

    @Override
    public void showWarningMessage(String mensaje) {

    }

    @Override
    public void showErrorMessage(String mensaje, String detalle) {

    }

    @Override
    public void updateProgressbar(String mensaje) {

    }

    @Override
    public String getMessage(int id) {
        return null;
    }

    @Override
    public void hiddenProgressPercentage() {

    }

    @Override
    public void showProgressbar(String titulo, String mensaje) {

    }

    @Override
    public void hiddenProgressbar() {

    }

    @Override
    public void updatePercentage(int progress, String message) {

    }

    @Override
    public void startOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_right_in, R.anim.transition_slide_left_out);
    }

    @Override
    public void finishOverridePendingTransaction() {
        overridePendingTransition(R.anim.transition_slide_left_in, R.anim.transition_slide_right_out);
    }
}
