package pe.com.dms.movilasist.config;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pe.com.dms.movilasist.R;

public class Config implements IConfig {

    private static Config mConfig;
    private Context mContext;
    private Resources mR;

    private Config(@Nullable Context context) {
        mR = null;
        mContext = context;
        if (context != null) mR = context.getResources();
    }

    public static void init(@NonNull Context context) {
        mConfig = new Config(context);
    }

    public static void setContext(Context context) {
        if (mConfig != null) {
            mConfig.mContext = context;
            if (context != null) mConfig.mR = context.getResources();
        }
    }

    private void destroy() {
        mContext = null;
        mR = null;
    }

    public static void deinit() {
        if (mConfig != null) {
            mConfig.destroy();
            mConfig = null;
        }
    }

    @NonNull
    public static IConfig get() {
        if (mConfig == null) return new Config(null);
        return mConfig;
    }

    @Override
    public boolean navigationDrawerEnabled() {
        return mR == null || mR.getBoolean(R.bool.navigation_drawer_enabled);
    }

    @Override
    public boolean showIconPopupToolbar() {
        return mR == null || mR.getBoolean(R.bool.show_icon_popup_toolbar);
    }
}