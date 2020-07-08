package com.example.anmlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextMobileNumber, editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    InternetCheck internetCheck =new InternetCheck();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMobileNumber = (EditText)findViewById(R.id.editTextMobileNumber);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonLogin =(Button)findViewById(R.id.buttonLogin);
        progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(this);
    }

    private void loginUser(){
        final String mobile = editTextMobileNumber.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Logging in please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                             if(jsonObject.getString("error")=="false"){
                                 //Toast.makeText(LoginActivity.this, "will take to registration pasge", Toast.LENGTH_SHORT).show();
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("name"),
                                        jsonObject.getString("designation"),
                                        jsonObject.getString("mobile"),
                                        jsonObject.getString("subcenter"),
                                        jsonObject.getString("phc") );

                                //https://stackoverflow.com/questions/21242244/if-else-statement-in-android-not-working
                             if(jsonObject.getString("designation").equals("MO")){

                                 startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                                 finish();

                             }else if(jsonObject.getString("designation").equals("ANM")){

                                 startActivity(new Intent(getApplicationContext(),Vaccine_entry_Activity.class));
                                 finish();
                             }

                             }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mobile",String.valueOf(mobile));
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            String mobile = editTextMobileNumber.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
          //  String connection = InternetCheck
            if(TextUtils.isEmpty(mobile)||TextUtils.isEmpty(password)){
                Toast.makeText(this, "Please enter credentials ...", Toast.LENGTH_SHORT).show();
            }else if(internetCheck.isConnected(this)){

                loginUser();

            }else{

                Toast.makeText(this, "Oops...! You seem to have no active internet Connection!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}