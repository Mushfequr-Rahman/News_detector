package com.bashundhara.mushfequr.news_detector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bashundhara.mushfequr.news_detector.Common.HttpDataHandler;
import com.bashundhara.mushfequr.news_detector.FeedAdapter.FeedAdapter_for_news;
import com.bashundhara.mushfequr.news_detector.Model.WebSite;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.WebDetection;
import com.google.api.services.vision.v1.model.WebEntity;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Scan_Task extends AsyncTask<Void,String,Void>
{
    private Bitmap current_image;
    private String Wiki_Url= "https://en.wikipedia.org/wiki/";
    private Context Current_Context;
    private static final String TAG =Scan_Task.class.getSimpleName();
    private  String Information ="Holding Variable Untill Recieved";
    private ProgressDialog pdia;

    Scan_Task(Bitmap bitmap, Context context)
    {
        this.current_image=bitmap;
        Current_Context=context;

        Log.d(TAG,"Initializing Async Task");
    }







    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG,"Carrying out Async Task");
        Vision.Builder vision_builder = new Vision.Builder(new NetHttpTransport(),new AndroidJsonFactory(),null);
        vision_builder.setVisionRequestInitializer( new VisionRequestInitializer("AIzaSyCFRqZHIH3MgwRYmIE0-QaKdPWwkn1ddkU"));

        final Vision vision= vision_builder.build();
        ByteArrayOutputStream byteStream= new ByteArrayOutputStream();
        current_image.compress(Bitmap.CompressFormat.PNG,100,byteStream);
        byte[] byteArray= byteStream.toByteArray();
        //bitmap.recycle();

        Image input_Image = new Image();
        input_Image.encodeContent(byteArray);

        //==========================================================================================
        //Setting Up Features We Want to Use
        //==========================================================================================
        Feature desired_Feature = new Feature();
        desired_Feature.setType("WEB_DETECTION");


        AnnotateImageRequest request = new AnnotateImageRequest();
        request.setImage(input_Image);
        request.setFeatures(Arrays.asList(desired_Feature));

        final BatchAnnotateImagesRequest batchRequest =
                new BatchAnnotateImagesRequest();

        batchRequest.setRequests(Arrays.asList(request));

        try {
            BatchAnnotateImagesResponse batchResponse = vision.images().annotate(batchRequest).execute();
           // final TextAnnotation text = batchResponse.getResponses()
            //        .get(0).getFullTextAnnotation();



            //======================================================================================
            //Getting Web Pages Using Goggle Vision APi
            //======================================================================================3
            final WebDetection web_detection = batchResponse.getResponses().get(0).getWebDetection();

            for (WebEntity entity : web_detection.getWebEntities()) {
                Log.d("Web Detection", String.format(entity.getDescription() + " : " + entity.getEntityId()
                        + " : " + entity.getScore()));
            }
            List<WebEntity> entity=web_detection.getWebEntities();
            String Original_name=entity.get(0).getDescription();
            //Original_name =Original_name.replaceAll(" ","_");
            this.publishProgress(Original_name);
            //Wiki_Url=String.format(Wiki_Url+Original_name);
            //Log.d("Wiki_View",Wiki_Url);
           // this.publishProgress(Wiki_Url);


            /*
            Log.d("Web Detection","Pages with Visually Similar Images");
            for(WebPage pages:web_detection.getPagesWithMatchingImages())
            {
                Log.d("Web Detection",String.format(pages.getUrl()+" : "+pages.getScore()));
            }
            */



            //======================================================================================
            //END OF WEB DETECTION
            //======================================================================================

            //=========================================================================================
            //ORIGINAL PROGRAM WITH TEXT DETECTION
            //=========================================================================================
            //information=text.getText();
            //Toast.makeText(getApplicationContext(),text.getText(),Toast.LENGTH_LONG).show();
            //progressDialog.dismiss();

        /*
        if(text.getText()==null) {
            Log.d("OCR reader","Could not read image");

        }
        else
        {
            Log.d("OCR reader", text.getText());
            Information = text.getText();
            this.publishProgress(text.getText());
        }
    }
    */
        }
        catch(IOException e)
            {
                e.printStackTrace();
            }


    //===========================================================================================




        return null;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        //change Content View

        TextView display_name = (TextView) ((Activity)Current_Context).findViewById(R.id.sample_text);

        display_name.setText(values[0]);
        String name=values[0];


        //==========================================================================================
        //SECTION FOR Wikipedia
        //==========================================================================================
        name = name.replaceAll(" ","_");
        Wiki_Url=Wiki_Url+name ;
        Log.d("Updated Web View",Wiki_Url);
        ImageButton wiki_button = (ImageButton) ((Activity)Current_Context).findViewById(R.id.wiki_button);
        wiki_button.setEnabled(true);

        final Intent wiki_intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Wiki_Url));

        wiki_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Current_Context.startActivity(wiki_intent);
            }
        });


        //==========================================================================================
        //END OF WIKIPEDIA SECTION beginning bdnews section
        //==========================================================================================
        String bd_name = name.replaceAll("_","+").toLowerCase();
        Log.d("Searching Bdnews",bd_name);


        //==========================================================================================
        //LOAD VIDEO FOR APP
        //==========================================================================================


        Search video_search=new Search(name,Current_Context);
        //ideo_search.delegate=this;
        video_search.execute();



        //==========================================================================================

         //=========================================================================================
        //LOADING DIFFERENT NEWS SOURCES USING NEWS API
        //==========================================================================================

             String News_API_Key="apiKey=d232cb1a17174feda637ef5fd299c8e5";
             String News_Name = bd_name;
             Log.d("News Name",bd_name);
             String temporary_name = "Barrack+Obama";
             String Alpha = "Orange";
             String Url_Link_first_part = "https://newsapi.org/v2/everything?";

             String Url_paramters="country=in&sortBy=popularity&";
             final String final_url=String.format(Url_Link_first_part+"q="+News_Name+"&"+Url_paramters+News_API_Key);



             AsyncTask<String,String,String> download_name = new AsyncTask<String, String, String>() {
                 @Override
                 protected void onPreExecute() {
                     super.onPreExecute();
                     Log.d("News Loader","getting the relevant news");

                 }

                 @Override
                 protected String doInBackground(String... strings) {
                     Log.d("News_Loader", "working in Backgorund");
                     Log.d("News Loader",final_url);
                     String result;
                     HttpDataHandler http =new HttpDataHandler();
                     result = http.getHttpData(final_url);
                     if(result==null)
                     {
                         Log.d("News Loader","Nothing returned/Check API");
                     }
                     else
                     {
                         Log.d("News Loader",result);
                     }
                     return result;


                 }

                 @Override
                 protected void onPostExecute(String s) {
                     super.onPostExecute(s);
                     WebSite webSite = new WebSite(null,0,null);
                     webSite = new Gson().fromJson(s,WebSite.class);
                     RecyclerView recyclerView = (RecyclerView) ((Activity) Current_Context).findViewById(R.id.RecyclerView);
                     FeedAdapter_for_news feedAdapter = new FeedAdapter_for_news(webSite,Current_Context);
                     recyclerView.setAdapter(feedAdapter);
                     feedAdapter.notifyDataSetChanged();

                 }
             };

             download_name.execute(final_url);

    }



    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        pdia.dismiss();
    }

    public String getInformation() {
        return Information;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pdia = new ProgressDialog(Current_Context);
        pdia.setMessage("Loading Information...");
        pdia.show();
    }

}
