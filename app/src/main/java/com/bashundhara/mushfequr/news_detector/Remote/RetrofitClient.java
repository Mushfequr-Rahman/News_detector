package com.bashundhara.mushfequr.news_detector.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit=null;
    public static Retrofit getClient(String base_url)
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder().baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
