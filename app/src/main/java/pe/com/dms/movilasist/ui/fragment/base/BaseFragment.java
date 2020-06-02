package pe.com.dms.movilasist.ui.fragment.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.injection.ActivityComponent;
import pe.com.dms.movilasist.ui.activities.base.BaseActivity;
import pe.com.dms.movilasist.ui.activities.base.IBaseContract;
import pe.com.dms.movilasist.ui.dialog.DefaultDialog;
import pe.com.dms.movilasist.util.CustomToast;
import pe.com.dms.movilasist.util.FragmentUtils;

public class BaseFragment extends Fragment implements IBaseContract.View {

    private ProgressDialog progress;
    private ProgressDialog horizontalProgressDialog;
    private Boolean erroresDetallados = false;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentUtils.onRequestPermissionsResultSupport(getChildFragmentManager(),
                requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentUtils.onActivityResultSupport(getChildFragmentManager(),
                requestCode, resultCode, data);
    }

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected ActivityComponent getActivityComponent() {
        return getBaseActivity().getActivityComponent();
    }

    public void clearDaggerComponent() {
        getBaseActivity().clearDaggerComponent();
    }

    @Override
    public void showProgressbar(String titulo, String mensaje) {
        progress = ProgressDialog.show(getContext(), titulo, mensaje, true);
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
        horizontalProgressDialog = new ProgressDialog(getContext());
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
        new CustomToast.Builder(getActivity().getApplicationContext(), getLayoutInflater(), mensaje)
                .setBackgroundColor(this.getResources().getColor(R.color.colorSuccess))
                .setIcon(this.getResources().getDrawable(R.drawable.ic_action_done_all))
                .build().show();
    }

    @Override
    public void showWarningMessage(String mensaje) {
        hiddenProgressbar();
        hiddenProgressPercentage();
        new CustomToast.Builder(getActivity().getApplicationContext(), getLayoutInflater(), mensaje)
                .build().show();
    }

    @Override
    public void showErrorMessage(String mensaje, String mensajeDetallado) {
        hiddenProgressbar();
        hiddenProgressPercentage();

        if (erroresDetallados) {
            new DefaultDialog.Builder(getActivity())
                    .title(getMessage(R.string.error))
                    .message(mensajeDetallado)
                    .setIcon(R.drawable.ic_action_error)
                    .build().createDialog().show();
        } else {
            new CustomToast.Builder(getActivity().getApplicationContext(), getLayoutInflater(), mensaje)
                    .setBackgroundColor(this.getResources().getColor(R.color.colorError))
                    .setIcon(this.getResources().getDrawable(R.drawable.ic_action_error))
                    .setDuration(Toast.LENGTH_LONG)
                    .build().show();
        }
    }

    @Override
    public String getMessage(int id) {
        return getResources().getString(id);
    }

    @Override
    public void setErroresDetallados(Boolean erroresDetallados) {
        this.erroresDetallados = erroresDetallados;
    }
}