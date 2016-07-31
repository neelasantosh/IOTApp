package iot.mk.com.iotapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref=null;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar)findViewById(R.id.loginProgressBar);
        progressBar.setVisibility(View.INVISIBLE);

        pref = getSharedPreferences("userprofile", MODE_PRIVATE);

        int id = pref.getInt("id", 0);
        Log.d("ID", id + "");
        if(id != 0 ) {
            startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
            finish();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UserRegistrationActivity.class));
            }
        });
        findViewById(R.id.signInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute(((EditText) findViewById(R.id.emailEditText)).getText().toString().trim(), (((EditText) findViewById(R.id.passwordEditText)).getText().toString().trim()));
                //startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
            }
        });
    }
    class LoginTask extends AsyncTask<String, Void, String> {
        @Override protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override protected String doInBackground(String... params) {
            String result ="";
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(Constants.LOGIN_URL);
                BasicNameValuePair username = new BasicNameValuePair("email", params[0]);
                BasicNameValuePair password = new BasicNameValuePair("password", params[1]);
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(username);
                list.add(password);
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            readJsonData(s);
            progressBar.setVisibility(View.GONE);
        }
    }
    private void readJsonData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jObj = new JSONObject(result);
            String strResult = jObj.getString("result");
            SharedPreferences prefs = getSharedPreferences("userprofile", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            if(strResult.equalsIgnoreCase("sucess")) {
                JSONObject userJsonObject = jObj.getJSONObject("data");
                Toast.makeText(getApplicationContext(), userJsonObject.getString("name"), Toast.LENGTH_LONG).show();
                editor.putInt("id", userJsonObject.getInt("id"));
                editor.putString("name", userJsonObject.getString("name"));
                editor.putString("email", userJsonObject.getString("email"));
                editor.putString("mobile", userJsonObject.getString("mobile"));
                editor.putString("recoveryEmail", userJsonObject.getString("recoveryEmail"));
                editor.putString("password", userJsonObject.getString("password"));
                editor.commit();
                startActivity(new Intent(LoginActivity.this, UserHomeActivity.class));
                finish();
            } else {

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
