package com.bashundhara.mushfequr.news_detector.Interface;

import com.bashundhara.mushfequr.news_detector.Model.WebSite;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService {
    @GET("v2/sources?language=en")
    Call<WebSite> getSources();
}
