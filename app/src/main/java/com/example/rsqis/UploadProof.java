package com.example.rsqis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

public class UploadProof extends AppCompatActivity {
    Button mButtonupload;
    VideoView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_proof);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload proof");

        mButtonupload= findViewById(R.id.btn_upload);
        mVideo= findViewById(R.id.videoplay);


    }

}
