package pe.com.dms.movilasist.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import pe.com.dms.movilasist.services.SendDataService;

public class BootServiceSendData extends BroadcastReceiver {
    String TAG = BootServiceSendData.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: ");
        context.startService(new Intent(context, SendDataService.class));
    }
}
