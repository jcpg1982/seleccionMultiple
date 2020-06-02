package pe.com.dms.movilasist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.Observable;

import pe.com.dms.movilasist.helpers.Constants;
import pe.com.dms.movilasist.util.NetworkUtil;

public class NetworkReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            switch (status) {
                case NetworkUtil.NETWORK_STATUS_WIFI:
                    Log.d(TAG, "Internet, NETWORK_STATUS_WIFI");
                    intent.putExtra(Constants.Intent.EXTRA_CONNECTED, true);
                    ObservableObject.getInstance().updateValue(intent);
                    break;
                case NetworkUtil.NETWORK_STATUS_MOBILE:
                    Log.d(TAG, "Internet, NETWORK_STATUS_MOBILE");
                    intent.putExtra("connected", true);
                    ObservableObject.getInstance().updateValue(intent);
                    break;
                case NetworkUtil.NETWORK_STATUS_NOT_CONNECTED:
                    Log.d(TAG, "No Internet, NETWORK_STATUS_NOT_CONNECTED");
                    intent.putExtra(Constants.Intent.EXTRA_CONNECTED, false);
                    ObservableObject.getInstance().updateValue(intent);
                    break;
            }
        }
    }

    /**
     * http://trycatchworld.blogspot.com/2016/10/android-communicating-between.html
     */
    public static class ObservableObject extends Observable {

        private static ObservableObject instance = new ObservableObject();

        public static ObservableObject getInstance() {
            return instance;
        }

        private ObservableObject() {
        }

        public void updateValue(Object data) {
            synchronized (this) {
                setChanged();
                notifyObservers(data);
            }
        }
    }
}