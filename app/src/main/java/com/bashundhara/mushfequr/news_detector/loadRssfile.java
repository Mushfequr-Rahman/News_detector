package com.bashundhara.mushfequr.news_detector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bashundhara.mushfequr.news_detector.Common.HttpDataHandler;
import com.bashundhara.mushfequr.news_detector.FeedAdapter.FeedAdapter;
import com.bashundhara.mushfequr.news_detector.Model.RSSObject;
import com.google.gson.Gson;


public  class loadRssfile extends AsyncTask<String,String,String> {

        Context Current_Context;
        RSSObject rssObject ;// = new RSSObject(null,null,null);
        private ProgressDialog progressDialog;

        loadRssfile(Context current_Context)
        {
            this.Current_Context = current_Context;


        }



        @Override
        protected void onPreExecute() {
             progressDialog = new ProgressDialog(Current_Context);
            super.onPreExecute();
            progressDialog.setMessage("Loading");
            Log.d("Async Task","Started");
           progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            //.setMessage("Currently getting news");
            Log.d("Async Task","doing task");
            Log.d("Async Task",strings[0]);
            String result;
            HttpDataHandler http= new HttpDataHandler();
            result = http.getHttpData(strings[0]);
            if(result==null)
            {
                Log.d("Async Task","no result returned");
            }
            else {
                Log.d("Async Task", result);
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null) {
                //super.onPostExecute(s);
                Log.d("ASYNC task result", s);
                Log.i("ASYNC task","Task completed");

               progressDialog.dismiss();
               rssObject = new RSSObject(null,null,null);
                rssObject = new Gson().fromJson(s,rssObject.getClass());
                if(rssObject!=null) {
                    RecyclerView recyclerView = (RecyclerView) ((Activity) Current_Context).findViewById(R.id.RecyclerView);
                    FeedAdapter feedAdapter = new FeedAdapter(rssObject, Current_Context);
                    recyclerView.setAdapter(feedAdapter);
                    feedAdapter.notifyDataSetChanged();
                }
                }
            else
            {
                Log.d("ASYNC task result","failed");
            }
        }
    }


