package iot.mk.com.iotapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserRegistrationActivity extends AppCompatActivity {

    private boolean isEmailValid = false;
    private boolean isEverythingOk = false;
    private boolean isNameLeftBlank = true;
    private boolean isPasswordLeftBlank = true;
    private boolean isMobileLeftBlank = true;
    private boolean isEmailLeftBlank = true;

    private ProgressBar progressBar;

    private EditText nameEditText, passwordEditText, mobileEditText, recoveryEmailEditText, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        progressBar = (ProgressBar)findViewById(R.id.registerProgressBar);

        nameEditText=(EditText) findViewById(R.id.regNameEditText);
        emailEditText=(EditText) findViewById(R.id.regEmailEditText);
        recoveryEmailEditText=(EditText) findViewById(R.id.regRecoveryEmailEditText);
        passwordEditText=(EditText) findViewById(R.id.regPasswordEditText);
        mobileEditText=(EditText) findViewById(R.id.regMobileEditText);

        Button submitButton = (Button)findViewById(R.id.userSubmitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Status", "Button Clicked");
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String recoveryEmail = recoveryEmailEditText.getText().toString().trim();
                String mobile = mobileEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (isNameLeftBlank || isEmailLeftBlank || isPasswordLeftBlank || isMobileLeftBlank) {
                    Log.d("Status", "Checking for null value");
                    if (name.isEmpty()) {
                        isNameLeftBlank = true;
                        nameEditText.setError("Please enter name");
                    } else {
                        isNameLeftBlank = false;
                    }
                    if (email.isEmpty()) {
                        isEmailLeftBlank = true;
                        emailEditText.setError("Please enter email");
                    } else {
                        isEmailLeftBlank = false;
                    }
                    if (mobile.isEmpty()) {
                        isMobileLeftBlank = true;
                        mobileEditText.setError("Please enter mobile");
                    } else {
                        isMobileLeftBlank = false;
                    }
                    if (password.isEmpty()) {
                        isPasswordLeftBlank = true;
                        passwordEditText.setError("Please enter password");
                    } else {
                        isPasswordLeftBlank = false;
                    }
                } else {
                    Log.d("Status", "No null value found executing task");
                    RegistrationTask registrationTask = new RegistrationTask();
                    registrationTask.execute(name, email, password, recoveryEmail, mobile);
                }
                name = ""; email = ""; password = ""; recoveryEmail = ""; mobile = "";
                isEmailValid = false; isEverythingOk = false;
            }
        });
    }
    class RegistrationTask extends AsyncTask<String, Void, String> {
        //AlertDialog dialog = new AlertDialog(UserRegistrationActivity.this);
        @Override protected void onPreExecute() {
            super.onPreExecute();
            //dialog.setView(R.layout.loading_dialog);
            //dialog.show();
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override protected String doInBackground(String... params) {
            String result ="";
            try {
                Log.d("Status", "Executing Do In Background Method");
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(Constants.REGISTRER_URL);
                BasicNameValuePair name = new BasicNameValuePair("name", params[0]);
                BasicNameValuePair email = new BasicNameValuePair("email", params[1]);
                BasicNameValuePair password = new BasicNameValuePair("password", params[2]);
                BasicNameValuePair recoveryEmail = new BasicNameValuePair("recoveryEmail", params[3]);
                Log.d("DoInBackValues", params[0]+" "+params[1]+" "+params[2]+" " +params[3]+" "+params[4]);
                BasicNameValuePair mobile = new BasicNameValuePair("mobile", params[4]);
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(name);
                list.add(password);
                list.add(email);
                list.add(recoveryEmail);
                list.add(mobile);
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);
                post.setEntity(urlEncodedFormEntity);
                HttpResponse response = client.execute(post);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String buffer = null;
                while((buffer = bufferedReader.readLine()) != null) {
                    stringBuilder.append(buffer);
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject mainObject = new JSONObject(s);
                String result = mainObject.getString("result");
                if(result.equals("sucess")) {
                    Log.d("ERROR", "No Error");
                    isEmailValid = true;
                    isEverythingOk = true;
                } else if(result.equals("email error")){
                    Log.d("ERROR", "Email error");
                    isEmailValid = false;
                    isEverythingOk = false;
                } else if(result.equals("error")) {
                    Log.d("ERROR", "Big Error");
                    isEverythingOk = false;
                    isEmailValid = false;
                }
                progressBar.setVisibility(View.INVISIBLE);
                if (!isEmailValid) {
                    Log.d("Status", "Email already exists");
                    emailEditText.setError("Email already exists!");
                }
                if (isEverythingOk) {
                    Log.d("Status", "Everything is OK. Sending data....");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(UserRegistrationActivity.this);
                    dialog.setTitle("Info");
                    dialog.setMessage("Registered Sucessfully!");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            UserRegistrationActivity.this.finish();
                        }
                    });
                    dialog.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("Received Response", s);
        }
    }

}
