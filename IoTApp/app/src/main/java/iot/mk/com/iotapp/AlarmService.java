package iot.mk.com.iotapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class AlarmService extends Service {

    private MqttConnectionManager connectionManager = null;

    @Nullable @Override public IBinder onBind(Intent intent) {
        return null;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        connectionManager = MqttConnectionManager.getInstance();
        connectionManager.publish(Constants.LAMP_TOPIC, "Hello, World!", 2, false);
        Log.d("Selected Device", intent.getStringExtra("Device Type"));
        return super.onStartCommand(intent, flags, startId);
    }
}
