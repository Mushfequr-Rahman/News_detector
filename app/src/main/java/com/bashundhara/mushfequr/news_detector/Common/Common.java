package com.bashundhara.mushfequr.news_detector.Common;

import android.util.Log;

import com.bashundhara.mushfequr.news_detector.Interface.NewsService;
import com.bashundhara.mushfequr.news_detector.Remote.RetrofitClient;

public class Common {
    private static final String TAG= Common.class.getSimpleName();
    private static final String Base_url="https://newsapi.org/";

    public static  final String API_key = "d232cb1a17174feda637ef5fd299c8e5";

    public static NewsService getService()
    {
        Log.d(TAG,"Creating Service");
        return RetrofitClient.getClient(Base_url).create(NewsService.class);
    }
}
