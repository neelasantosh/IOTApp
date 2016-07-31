package iot.mk.com.iotapp;

import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttCallbackHandler implements MqttCallback {

    private Context context;

    public MqttCallbackHandler(Context context) {
        this.context = context;
    }

    @Override public void connectionLost(Throwable throwable) {
        //Toast.makeText(context, "Connection Lost", Toast.LENGTH_LONG).show();
    }

    @Override public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        //Toast.makeText(context, "Received: " + mqttMessage.toString(), Toast.LENGTH_LONG).show();
    }

    @Override public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        //Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG).show();
    }
}
