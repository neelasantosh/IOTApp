package iot.mk.com.iotapp;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MqttConnectionManager {

    private Context context;
    private static MqttConnectionManager object;

    private static MqttClient client = null;
    private MemoryPersistence memoryPersistence = null;

    private MqttConnectionManager(Context context, String brokerUrl, String clientId) {
        this.context = context;
        memoryPersistence = new MemoryPersistence();
        try {
            client = new MqttClient(brokerUrl, clientId, memoryPersistence);
            client.setCallback(new MqttCallbackHandler(context));
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static MqttConnectionManager getConnection(Context context, String brokerUrl, String clientId) {
        if(object == null)  {
            Log.d("Status", "Creating object");
            object = new MqttConnectionManager(context, brokerUrl, clientId);
        }
        return object;
    }

    public static MqttConnectionManager getInstance() {
        if(object != null && client != null) {
            return object;
        }
        return null;
    }

    public void publish(String topic, String payload, int qos, boolean retained) {
        if(client != null && client.isConnected()) {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(qos);
            message.setRetained(retained);
            try {
                client.publish(topic, message);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    public void subscribe(String topic, int qos) {
        if(client != null && client.isConnected()) {
            try {
                client.subscribe(topic, qos);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConencted() {
        if(client != null && client.isConnected()) {
            return client.isConnected();
        }
        return false;
    }

    public void disconnect() {
        if(client != null && client.isConnected()) {
            try {
                client.disconnect();
                client = null;
                object = null;
                Log.d("Status", "Client is null");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

}
