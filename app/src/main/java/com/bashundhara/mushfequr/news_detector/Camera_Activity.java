package com.bashundhara.mushfequr.news_detector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Camera_Activity extends Activity{

    private android.hardware.Camera mCamera;
    private CameraPreview mPreview;
    private Button Capture;
    private static final String TAG = Camera_Activity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);


        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        Capture = (Button) findViewById(R.id.button_capture);
        SetUpZoom();
        preview.addView(mPreview);

        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.takePicture(null,null,mPicture);
                Intent intent = new Intent(getApplicationContext(),Display_info.class);
                startActivity(intent);
                finish();
            }
        });

    //==============================================================================================
    //SETTING UP ZOOM FOR CAMERA
    //==============================================================================================



    }
    public void SetUpZoom()
    {
        Log.d(TAG,"Setting Up Zoom");
        Camera.Parameters params = mCamera.getParameters();
        if (params.getMaxNumMeteringAreas() > 0){ // check that metering areas are supported
            List<Camera.Area> meteringAreas = new ArrayList<Camera.Area>();

            Rect areaRect1 = new Rect(-100, -100, 100, 100);    // specify an area in center of image
            meteringAreas.add(new Camera.Area(areaRect1, 600)); // set weight to 60%
            Rect areaRect2 = new Rect(800, -1000, 1000, -800);  // specify an area in upper right of image
            meteringAreas.add(new Camera.Area(areaRect2, 400)); // set weight to 40%
            params.setMeteringAreas(meteringAreas);
        }
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        //params.setZoom(Camera.Parameter);

        mCamera.setParameters(params);


    }

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            e.printStackTrace();
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "Error creating media file, check storage permissions: " );
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}
