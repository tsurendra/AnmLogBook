package com.example.anmlogbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


            private EditText editTextName,editTextSecName,editTextMobileNumber;
            private Button buttonRegister;
            private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextSecName = (EditText)findViewById(R.id.editTextSecName);
        editTextMobileNumber = (EditText)findViewById(R.id.editTextMobileNumber);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
    }

    private void userRegister(){
            final String name = editTextName.getText().toString().trim();
            final String Sec_name = editTextSecName.getText().toString().trim();
            final String mobile = editTextMobileNumber.getText().toString().trim();
            Log.i("mobile",mobile);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Log.i("message",response);
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                if(jsonObject.getString("error")=="false"){
                                    //Toast.makeText(RegisterActivity.this, "will take to registration pasge", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                                    editTextMobileNumber.setText("");
                                    editTextName.setText("");
                                    editTextSecName.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("name",name);
                    params.put("mobile",mobile);
                    params.put("subcenter",Sec_name);
                    return params;
                }
            };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            String name = editTextName.getText().toString().trim();
            String Sec_name = editTextSecName.getText().toString().trim();
            String mobile = editTextMobileNumber.getText().toString().trim();
            if(TextUtils.isEmpty(name)||TextUtils.isEmpty(Sec_name)||TextUtils.isEmpty(mobile)){
               Toast.makeText(this, "Please enter all fields and Try again!", Toast.LENGTH_SHORT).show();
            }else{
                  userRegister();
                //Toast.makeText(this, "name= "+name, Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        //getMenuInflater().inflate(R.menu.main_menu,menu);
        //return  true;
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                SharedPrefManager.getInstance(this).userLogout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return true;
    }
}