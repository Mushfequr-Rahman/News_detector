package com.bashundhara.mushfequr.news_detector.Common;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDataHandler {

    static String stream = null;
    private static String TAG = HttpDataHandler.class.getSimpleName();

    public HttpDataHandler()
    {

    }

    public String getHttpData(String url_String) {
        try {
            Log.d(TAG,url_String);
            URL url = new URL(url_String);
            HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();

            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder builder = new StringBuilder();
                String line;

                while((line=r.readLine())!=null)
                {
                    //Log.d(TAG,line);
                    stream = builder.toString();
                    urlConnection.disconnect();
                    return line;
                }
            }

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(stream!=null) {
            Log.d(TAG, stream);
        }
        return stream;
    }
}
