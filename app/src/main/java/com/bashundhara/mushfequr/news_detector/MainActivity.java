package com.bashundhara.mushfequr.news_detector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Button Open_Camera;
    private static final int REQUEST_GET_ACCOUNT = 112;
    private static final int PERMISSION_REQUEST_CODE = 200;

    RecyclerView recyclerView;
    ImageButton Wiki_button;


    private static final String RSS_link="http://bdnews24.com/?widgetName=rssfeed&widgetId=1150&getXmlFeed=true";
    private static final String RSS_to_JSON_APi="https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String My_title = "News detector";
        setTitle(My_title);
        setContentView(R.layout.activity_main);

        Wiki_button = (ImageButton) findViewById(R.id.wiki_button);
        Wiki_button.setEnabled(false);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

        Open_Camera= (Button) findViewById(R.id.button);
        Open_Camera.setText("Scan");
        Open_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent=new Intent(getApplicationContext(),Camera_Activity.class);
                //startActivity(intent);

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,0);

            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), android.support.v7.widget.LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final Button load_news = (Button) findViewById(R.id.load_news);
        load_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRSS();
            }
        });



        //loadRSS();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //oadRSS();
    }

    private  void loadRSS() {


        loadRssfile loadRssfilex = new loadRssfile(this);
        StringBuilder url_get_data =  new StringBuilder(RSS_to_JSON_APi);
        url_get_data.append(RSS_link);
        Log.d("LoadRSS",url_get_data.toString());
        String url = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fbdnews24.com%2F%3FwidgetName%3Drssfeed%26widgetId%3D1150%26getXmlFeed%3Dtrue";
        //loadRssfile.execute(url_get_data.toString());
        Log.e("LoadRSS",url);
        loadRssfilex.execute(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode== 0 && resultCode == Activity.RESULT_OK) {
            Bitmap x = (Bitmap) data.getExtras().get("data");
            ((ImageView)findViewById(R.id.imageView)).setImageBitmap(x);

            /*
            ContentValues values = new ContentValues();
            values.put(Images.Media.TITLE, "title");
            values.put(Images.Media.BUCKET_ID, "test");
            values.put(Images.Media.DESCRIPTION, "test Image taken");
            values.put(Images.Media.MIME_TYPE, "image/jpeg");
            Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
            OutputStream outstream;
            try {
                outstream = getContentResolver().openOutputStream(uri);
                x.compress(Bitmap.CompressFormat.JPEG, 70, outstream);
                outstream.close();
            } catch (FileNotFoundException e) {
                //
                e.printStackTrace();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            */

           // Display_information(x);
            Scan_Task current_task = new Scan_Task(x,this);
            current_task.execute();
           // current_task.getInformation();


            TextView Sample_Text = (TextView) findViewById(R.id.sample_text);

            Sample_Text.setText(current_task.getInformation());


            //TODO: CREATE A NEW ACTIVITY FOR FEATURES DISPLAY
            //*==============================================
            //SAMPLE ACTIVITY
            //Intent intent = new Intent(this,Display_info.class);
            //intent.putExtra(BitMap x);
            //==================================================
        }
    }

    private void Display_information(Bitmap bitmap) {
        Vision.Builder vision_builder = new Vision.Builder(new NetHttpTransport(),new AndroidJsonFactory(),null);
        vision_builder.setVisionRequestInitializer( new VisionRequestInitializer("AIzaSyCFRqZHIH3MgwRYmIE0-QaKdPWwkn1ddkU"));

        final Vision vision= vision_builder.build();
        ByteArrayOutputStream byteStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteStream);
        byte[] byteArray= byteStream.toByteArray();
        //bitmap.recycle();

        Image input_Image = new Image();
        input_Image.encodeContent(byteArray);

        Feature desired_Feature = new Feature();
        desired_Feature.setType("TEXT_DETECTION");

        AnnotateImageRequest request = new AnnotateImageRequest();
        request.setImage(input_Image);
        request.setFeatures(Arrays.asList(desired_Feature));

        final BatchAnnotateImagesRequest batchRequest =
                new BatchAnnotateImagesRequest();

        batchRequest.setRequests(Arrays.asList(request));
    //    final ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());

  //      progressDialog.setMessage("Uploading Image to online Server");
//        progressDialog.show();



        final String information="mgkdgm";
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();
                    final TextAnnotation text = batchResponse.getResponses()
                            .get(0).getFullTextAnnotation();

                   //information=text.getText();
                    //Toast.makeText(getApplicationContext(),text.getText(),Toast.LENGTH_LONG).show();
                    //progressDialog.dismiss();
                    if(text.getText()!=null) {
                        Log.d("OCR reader", text.getText());
                        Toast.makeText(getApplicationContext(),text.getText(),Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("OCR reader","Could not read image");
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


            }

        });


        TextView Sample_Text = (TextView) findViewById(R.id.sample_text);


    }
     //==================================================================================================
    //PERMISSIONS SECTION
    //==============================================================================================
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA_SERVICE}, REQUEST_GET_ACCOUNT);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA_SERVICE);
        return result1  == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access location data and camera", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access location data and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    //requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                                    //        PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean Check_Camera(Context context)
    {
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
