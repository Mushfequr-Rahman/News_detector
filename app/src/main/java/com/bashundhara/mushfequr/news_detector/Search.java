package com.bashundhara.mushfequr.news_detector;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Search extends AsyncTask<Void,Void,String> {

    private static final String TAG=Search.class.getSimpleName();
    public AsyncResponse delegate=null;

    private static YouTube youtube;
    private Context context;
    String Search_Term;
    String Id=null;
    List<String> embed_values;
    String[] embed_codes= new String[10];

    Search(String search_Term, Context current_Context)
    {
        this.Search_Term=search_Term.replaceAll("_"," ");
        this.context=current_Context;


    }

    private void prettyPrint(Iterator<SearchResult> iterator, String query_term) {
        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + " videos for search on \"" + query_term + "\".");
        System.out.println("=============================================================\n");

        if (!iterator.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }
        int i =0;
        while (iterator.hasNext()) {

            SearchResult singleVideo = iterator.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                //Log.d(TAG,rId.getVideoId());
                Id=rId.getVideoId();
                embed_codes[i]=Id;
                i++;
                Log.d(TAG,String.valueOf(i));
                System.out.println(" Video Id" + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }

    public String getId() {
        return this.Id;
    }



    @Override
    protected String doInBackground(Void... voids) {
        try{
            youtube = new YouTube.Builder(new NetHttpTransport(), new AndroidJsonFactory(), null
            ).setApplicationName("youtube-cmdline-search-sample").build();
            String query_term=Search_Term.toLowerCase();
            Log.d(TAG,query_term);

            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey="AIzaSyCFRqZHIH3MgwRYmIE0-QaKdPWwkn1ddkU";
            search.setKey(apiKey);
            search.setQ(query_term);
            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long) 10);


            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator(), query_term);
            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
     //   delegate.processFinish(Id);
        /*
        WebView Video_display=(WebView) ((Activity)context).findViewById(R.id.Video_View);
        Video_display.getSettings().setJavaScriptEnabled(true);
        Video_display.getSettings().setPluginState(WebSettings.PluginState.ON);
        Video_display.setWebChromeClient(new WebChromeClient());
        Video_display.setWebViewClient(new WebViewClient());
        Log.d("Video Display","Playing Video");

        //ideo_search.delegate=this;
            */


        //==========================================================================================
        //Setting up slide view pagers
        //==========================================================================================

        ViewPager mViewPager = (ViewPager) ((Activity)context).findViewById(R.id.Youtube_pager);
        Slide_Adapter mSlide_adapter= new Slide_Adapter(embed_codes,context);
        mViewPager.setAdapter(mSlide_adapter);


    }
}








