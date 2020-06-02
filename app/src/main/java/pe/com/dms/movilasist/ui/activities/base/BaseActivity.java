package pe.com.dms.movilasist.ui.activities.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import pe.com.dms.movilasist.Application;
import pe.com.dms.movilasist.R;
import pe.com.dms.movilasist.config.Config;
import pe.com.dms.movilasist.injection.ActivityComponent;
import pe.com.dms.movilasist.injection.DaggerActivityComponent;
import pe.com.dms.movilasist.util.ColorUtils;
import pe.com.dms.movilasist.util.FragmentUtils;
import pe.com.dms.movilasist.util.ResolveUtils;
import pe.com.dms.movilasist.util.ViewUtils;

public abstract class BaseActivity extends AppCompatActivity implements IBaseContract.View {

    private ActivityComponent activityComponent;

    public int getLastStatusBarInsetHeight() {
        return 0;
    }

    public abstract void startOverridePendingTransaction();

    public abstract void finishOverridePendingTransaction();

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        startOverridePendingTransaction();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public void clearDaggerComponent() {
        Application.get(this).clearDaggerComponent();
    }

    @Override
    public void finish() {
        super.finish();
        finishOverridePendingTransaction();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Config.init(this);
        super.onCreate(savedInstanceState);
        activityComponent = DaggerActivityComponent.builder().applicationComponent(Application.get(this).getAppComponent()).build();
        ViewUtils.setTaskDescriptionColor(
                BaseActivity.this, ResolveUtils.resolveColor(this, R.attr.colorPrimary));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final View decorView = getWindow().getDecorView();
            final boolean lightStatusEnabled =
                    ColorUtils.isColorLight(ResolveUtils.resolveColor(this, R.attr.colorPrimaryDark));
            if (lightStatusEnabled) {
                ViewUtils.setLightStatusBar(decorView);
            } else {
                ViewUtils.clearLightStatusBar(decorView);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.setContext(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            Config.deinit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FragmentUtils.onRequestPermissionsResultSupport(getSupportFragmentManager(),
                requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentUtils.onActivityResultSupport(getSupportFragmentManager(),
                requestCode, resultCode, data);
    }
}