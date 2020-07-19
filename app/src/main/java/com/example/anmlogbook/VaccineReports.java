package com.example.anmlogbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class VaccineReports extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextSelectDate;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_reports);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        editTextSelectDate = findViewById(R.id.editTextSelectDate);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnSubmit){
            String date = editTextSelectDate.getText().toString().trim();
            String url = Constants.URL_VACCINE_ENTRY ;
            String finalurl = url.concat("?date=").concat(date);

            String folder_main = "LogBook";

            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
            }
            DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(finalurl);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle("My File");
            request.setDescription("Downloading");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setVisibleInDownloadsUi(false);
           // request.setDestinationUri(Uri.parse("file://" + logbook + "/myfile.mp3"));

            downloadmanager.enqueue(request);
        }
    }
}