package com.bashundhara.mushfequr.news_detector;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;

public class Display_info extends AppCompatActivity {

    private static final String TAG =Display_info.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        Vision.Builder vision_builder = new Vision.Builder(new NetHttpTransport(),new AndroidJsonFactory(),null);
        vision_builder.setVisionRequestInitializer( new VisionRequestInitializer("AIzaSyCFRqZHIH3MgwRYmIE0-QaKdPWwkn1ddkU"));

        Vision vision= vision_builder.build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

    }
}
