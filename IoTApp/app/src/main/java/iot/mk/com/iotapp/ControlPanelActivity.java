package iot.mk.com.iotapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import iot.mk.com.iotapp.Constants.Action;

import static iot.mk.com.iotapp.Constants.Action.PUBLISH;

public class ControlPanelActivity extends AppCompatActivity {

    private Button lampOnButton, lampOffButton, doorOpenButton, doorCloseButton, garrageDoorOpenButton, garrageDoorCloseButton;
    private SeekBar tempSeekBar, dimmerLampSeekBar, waterValveSeekbar;
    private ImageView dimmerLight, lampImageView, doorImageView,valveImageView, garrageImageView;

    private static boolean bulbStatus = false;

    private Action action;

    private MqttConnectionManager connectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        connectionManager = MqttConnectionManager.getInstance();

        lampOffButton = (Button)findViewById(R.id.lampOffButton);
        lampOnButton = (Button)findViewById(R.id.lampOnButton);
        dimmerLampSeekBar = (SeekBar)findViewById(R.id.dimmerSeekBar);
        tempSeekBar = (SeekBar)findViewById(R.id.tempGaugeSeekBar);
        waterValveSeekbar = (SeekBar)findViewById(R.id.valveSeekbar);
        dimmerLight = (ImageView)findViewById(R.id.dimmerLightImageView);
        lampImageView = (ImageView)findViewById(R.id.lampImageView);
        valveImageView = (ImageView)findViewById(R.id.valveImageView);
        doorOpenButton = (Button)findViewById(R.id.doorOpenButton);
        doorCloseButton = (Button)findViewById(R.id.doorCloseButton);
        doorImageView = (ImageView)findViewById(R.id.doorImageView);
        garrageDoorCloseButton = (Button)findViewById(R.id.garrageDoorCloseButton);
        garrageDoorOpenButton = (Button)findViewById(R.id.garrageDoorOpenButton);
        garrageImageView = (ImageView)findViewById(R.id.garrageDoorImageView);

        lampOffButton.setOnClickListener(new View.OnClickListener() {
           @Override public void onClick(View view) {
               action = Action.PUBLISH;
               connectionManager.publish(Constants.LAMP_TOPIC, "0", 2, false);
               lampOffButton.setEnabled(false);
               lampOnButton.setEnabled(true);
               lampImageView.setImageResource(R.drawable.lamp_off);
           }
        });
        lampOnButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                action = Action.PUBLISH;
                connectionManager.publish(Constants.LAMP_TOPIC, "1", 2, false);
                lampOffButton.setEnabled(true);
                lampOnButton.setEnabled(false);
                lampImageView.setImageResource(R.drawable.lamp_on);
            }
        });

        tempSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Seekbar Value:", String.valueOf(progress));
                action = Action.PUBLISH;
                connectionManager.publish(Constants.TEMPERATURE_GAUGE_TOPIC, "" + progress, 2, false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        dimmerLampSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Seekbar Value:", String.valueOf(progress));
                action = Action.PUBLISH;
                connectionManager.publish(Constants.DIMMER_LIGHT_TOPIC, "" + progress, 2, false);
                float alpha = (progress) / 100.0f;
                Log.d("Alpha", alpha + "");
                dimmerLight.setAlpha(alpha);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        doorOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionManager.publish(Constants.MAIN_DOOR_TOPIC, "1", 2, false);
                doorImageView.setImageResource(R.drawable.door_open);
                doorOpenButton.setEnabled(false);
                doorCloseButton.setEnabled(true);
            }
        });
        doorCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionManager.publish(Constants.MAIN_DOOR_TOPIC, "0", 2, false);
                doorImageView.setImageResource(R.drawable.door_close);
                doorOpenButton.setEnabled(true);
                doorCloseButton.setEnabled(false);
            }
        });
        garrageDoorOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionManager.publish(Constants.GARAGE_TOPIC, "1", 2, false);
                garrageImageView.setImageResource(R.drawable.garrage_open);
                garrageDoorOpenButton.setEnabled(false);
                garrageDoorCloseButton.setEnabled(true);
            }
        });
        garrageDoorCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionManager.publish(Constants.GARAGE_TOPIC, "0", 2, false);
                garrageImageView.setImageResource(R.drawable.garrage_close);
                garrageDoorOpenButton.setEnabled(true);
                garrageDoorCloseButton.setEnabled(false);
            }
        });
        waterValveSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                connectionManager.publish(Constants.WATER_VALVE_TOPIC, progress+"", 2, false);
                valveImageView.setRotation(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    class BackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override protected Void doInBackground(Void... params) {
            switch(action) {
                case PUBLISH: break;
                    //connectionManager.publish(Constants.);
                case SUBSCRIBE: break;
                default: break;
            }
            return null;
        }
    }
}
