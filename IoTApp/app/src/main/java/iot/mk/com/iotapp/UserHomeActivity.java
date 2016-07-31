package iot.mk.com.iotapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    private String deviceId;
    private MqttConnectionManager connectionManager;
    private static Button connectButton = null;
    private static DBHelper database = null;
    private List<ConnectionConfiguration> configList = null;
    private ArrayAdapter<ConnectionConfiguration> adapter;
    private AutoCompleteTextView ipAddressAutoCompleteTextView;

    @Override protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        database = new DBHelper(getApplicationContext());

        configList = database.getAllConfiguration();
        adapter = new ArrayAdapter<ConnectionConfiguration>(getApplicationContext(), android.R.layout.simple_list_item_1, configList);
        ipAddressAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.dialogIpEditText);
        ipAddressAutoCompleteTextView.setAdapter(adapter);

        ((TextView)findViewById(R.id.userNameTextView)).setText(getSharedPreferences("userprofile", MODE_PRIVATE).getString("name", ""));

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final Button connectButton = (Button)findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                if(((SwitchCompat)findViewById(R.id.rememberConfigCheckBox)).isChecked()) {
                    database.saveConfiguration(new ConnectionConfiguration(
                            ((AutoCompleteTextView) findViewById(R.id.dialogIpEditText)).getText().toString().trim(),
                            Integer.parseInt(((EditText) findViewById(R.id.dialogPortEditText)).getText().toString().trim()),
                            ((SwitchCompat) findViewById(R.id.cleanSessionCheckBox)).isChecked(),
                            ((EditText) findViewById(R.id.dialogClientIdEditText)).getText().toString().trim()
                    ));
                }

                if(connectButton.getText().toString().trim().equals("Connect")) {
                    Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show();
                    String brokerId = "tcp://" + (((AutoCompleteTextView) findViewById(R.id.dialogIpEditText)).getText().toString().trim()) + ":" +
                            (((EditText) findViewById(R.id.dialogPortEditText)).getText().toString().trim());
                    String clientId = (((EditText) findViewById(R.id.dialogClientIdEditText)).getText().toString().trim());
                    boolean cleanSession = (((SwitchCompat) findViewById(R.id.cleanSessionCheckBox)).isChecked());
                    connectionManager = MqttConnectionManager.getConnection(getApplicationContext(), brokerId, clientId);
                    connectionManager.publish("temp", "hello", 2, false);
                }

                if(connectButton.getText().toString().trim().equals("Disconnect")) {
                    Toast.makeText(getApplicationContext(), "Disconnecting", Toast.LENGTH_SHORT).show();
                    connectionManager.disconnect();
                }

                if (connectionManager.isConencted()) {
                    Log.d("Status", "isConnected() if");
                    ((EditText) findViewById(R.id.dialogIpEditText)).setEnabled(false);
                    ((EditText) findViewById(R.id.dialogPortEditText)).setEnabled(false);
                    ((EditText) findViewById(R.id.dialogClientIdEditText)).setEnabled(false);
                    ((SwitchCompat) findViewById(R.id.cleanSessionCheckBox)).setEnabled(false);
                    connectButton.setText("Disconnect");
                } else {
                    Log.d("Status", "isConnected() else");
                    ((EditText) findViewById(R.id.dialogIpEditText)).setEnabled(true);
                    ((EditText) findViewById(R.id.dialogPortEditText)).setEnabled(true);
                    ((EditText) findViewById(R.id.dialogClientIdEditText)).setEnabled(true);
                    ((SwitchCompat) findViewById(R.id.cleanSessionCheckBox)).setEnabled(true);
                    connectButton.setText("Connect");
                    connectionManager = null;
                }
            }
        });
        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = getSharedPreferences("userprofile", MODE_PRIVATE).edit();
                edit.putInt("id", 0);
                edit.commit();
                startActivity(new Intent(UserHomeActivity.this, LoginActivity.class));
                finish();
            }
        });
        findViewById(R.id.controlPanelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, ControlPanelActivity.class));
            }
        });
        findViewById(R.id.setAlarmForDevices).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(UserHomeActivity.this, AlarmActivity.class));
            }
        });
    }

}
