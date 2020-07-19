package com.example.anmlogbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Vaccine_entry_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText childName, parentName;
    DatePickerDialog datePicker;
    DatePickerDialog datePicker2;
    private EditText dateSelect;
    private EditText vaccineDate;
    private TextView selectedVaccine;
    private  RadioGroup radioGroup;
    private RadioButton selectedRadioButton;
    String[] listVaccines;
    String vaccine="Please selct vaccine ......!..";
    String item;
    boolean[] checkedItems;
    ArrayList<Integer> givenVaccines = new ArrayList<>();
    private Button buttonSubmit;
    InternetCheck internetCheck =new InternetCheck();
    private ProgressDialog progressDialog;
   // ArrayList<String> selectedVaccines = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_entry_);
        childName =(EditText) findViewById(R.id.editTextChildName);
        parentName = (EditText) findViewById(R.id.editTextParentName);
        radioGroup = findViewById(R.id.radioGroup);
        dateSelect = (EditText)findViewById(R.id.editTextDate);
        dateSelect.setInputType(InputType.TYPE_NULL);
        //https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datePicker = new DatePickerDialog(Vaccine_entry_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        dateSelect.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },year, month, day);
                datePicker.show();
            }
        });
        vaccineDate = (EditText)findViewById(R.id.editTextVaccineDate);
        vaccineDate.setInputType(InputType.TYPE_NULL);
        vaccineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datePicker2 = new DatePickerDialog(Vaccine_entry_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker2, int year, int monthOfYear, int dayOfMonth) {
                                vaccineDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },year, month, day);
                datePicker2.show();
            }
        });

        selectedVaccine = (TextView)findViewById(R.id.vaccineSelect);
        selectedVaccine.setMovementMethod(new ScrollingMovementMethod());
        listVaccines = getResources().getStringArray(R.array.VaccineList);
        checkedItems = new boolean[listVaccines.length];

        selectedVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(Vaccine_entry_Activity.this);
                mbuilder.setTitle("Select the vaccine ");
                mbuilder.setMultiChoiceItems(listVaccines, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            givenVaccines.add(position);
                        }else {
                            givenVaccines.remove(Integer.valueOf(position));
                        }
                    }
                });
                mbuilder.setCancelable(false);
                mbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                       item ="";
                      vaccine ="";
                      for(int i =0; i< givenVaccines.size();i++){
                          item = item + "\n"+listVaccines[givenVaccines.get(i)];
                          if(vaccine ==""){
                              vaccine= givenVaccines.get(i).toString();
                          }else{
                              vaccine = vaccine +"$"+ givenVaccines.get(i);
                          }

                      }
                      if(item==""){
                          item= "Select Vaccine By clicking here..!";
                          vaccine= "";
                      }

                      selectedVaccine.setText(item);
                    }
                });
                mbuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mbuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        for (int i=0; i< checkedItems.length;i++){
                            checkedItems[i]=false;
                            givenVaccines.clear();
                            selectedVaccine.setText("Select Vaccine by Clicking here!");
                            vaccine ="";
                        }
                    }
                });
                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }
        });
        progressDialog = new ProgressDialog(this);
        buttonSubmit = (Button) findViewById(R.id.btnSubmit);
        buttonSubmit.setOnClickListener(this);
    }
    private void vaccineEntry(){
        final String child = childName.getText().toString().trim();
        final String parent = parentName.getText().toString().trim();
       final String date = dateSelect.getText().toString().trim();
        final String vGivenDate = vaccineDate.getText().toString().trim();
        //String gender ="";
        final  String vaccineGiven = vaccine;
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
         selectedRadioButton = (RadioButton)findViewById(selectedRadioButtonId);
        final String gender = selectedRadioButton.getText().toString();

        progressDialog.setMessage("please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_VACCINE_ENTRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                         childName.setText("");
                         parentName.setText("");
                         dateSelect.setText("");
                         vaccineDate.setText("");
                        givenVaccines.clear();
                        selectedVaccine.setText("Select Vaccine by Clicking here!");
                        vaccine ="";
                        selectedRadioButton.setChecked(false);


                        Toast.makeText(Vaccine_entry_Activity.this,response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                Toast.makeText(Vaccine_entry_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("childName",child);
                params.put("motherName",parent);
                params.put("dateOfBirth",date);
                params.put("gender",gender);
                params.put("dateOfVaccine",vGivenDate);
                params.put("vaccine",vaccine);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSubmit){
            String child = childName.getText().toString().trim();
            String parent = parentName.getText().toString().trim();
            String date = dateSelect.getText().toString().trim();
            String vGivenDate = vaccineDate.getText().toString().trim();
            String gender ="";
            String vaccineGiven = vaccine;

            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = (RadioButton)findViewById(selectedRadioButtonId);
                 gender = selectedRadioButton.getText().toString();
                // selectedRadioButton.setChecked(false);

               // Toast.makeText(this,selectedRbText, Toast.LENGTH_SHORT).show();
            }
            if(TextUtils.isEmpty(child)){
                Toast.makeText(this, "Please enter Child name...", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(parent)){
                Toast.makeText(this, "Please enter Parent name...", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(date)){
                Toast.makeText(this, "Please enter the date of birth", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(vGivenDate)){
                Toast.makeText(this, "Please select the date of vaccine given...", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(vaccineGiven)){
                Toast.makeText(this, "Please select the vaccine ..", Toast.LENGTH_SHORT).show();
            }else if(internetCheck.isConnected(this)){
                vaccineEntry();
               // Toast.makeText(this,vGivenDate,Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this, "you seem to lost internet connection...", Toast.LENGTH_SHORT).show();
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