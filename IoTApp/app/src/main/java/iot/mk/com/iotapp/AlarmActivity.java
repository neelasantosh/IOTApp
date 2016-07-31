package iot.mk.com.iotapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private int hourToExecute;
    private int minuteToExecute;

    private String selectedDevice="";

    private Spinner deviceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent i = new Intent(AlarmActivity.this, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        deviceSpinner = (Spinner)findViewById(R.id.deviceSpinner);
        deviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Selecting Item", "Selecting Item");
                if (position == 1) selectedDevice = "Balcony Lamp";
                if (position == 2) selectedDevice = "Main Door";
                if (position == 3) selectedDevice = "Garage Door";
                if (position == 4) selectedDevice = "Main Room Dimmer Light";
                if (position == 5) selectedDevice = "Temperature of Main Room";
                if (position == 6) selectedDevice = "Water Valve";
                Log.d("Device", selectedDevice);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if(selectedDevice.equals("Temperature of Main Room")) {
                    Log.d("DeviceType:", selectedDevice);
                }
                if(selectedDevice.equals("Main Room Dimmer Light")) {
                    Log.d("DeviceType:", selectedDevice);
                }

                Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
                intent.putExtra("Device Type", selectedDevice);

                PendingIntent pendingIntent = PendingIntent.getService(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourToExecute);
                calendar.set(Calendar.MINUTE, minuteToExecute);

                Log.d("Date", calendar.toString());

                long timeToExecute = calendar.getTimeInMillis();
                Log.e("Time to execute", timeToExecute + "");

                alarmManager.set(AlarmManager.RTC_WAKEUP, timeToExecute,pendingIntent);

                Log.d("Status:", "Alarm Started!");

            }
        });

        findViewById(R.id.alarmSetTimeButton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourToExecute = hourOfDay;
                        minuteToExecute = minute;
                        ((TextView) findViewById(R.id.alarmTextView)).setText("You have set the alarm for " + hourToExecute + ":" + minuteToExecute);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

    }

}
