package com.ghuyfel.news.api;

import com.ghuyfel.news.models.Article;
import com.ghuyfel.news.models.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("news")
    Call<List<News>> getNews(@Query("format") String format);

    @GET("{SiteName}/{UrlName}/news/{UrlFriendlyDate}/{UrlFriendlyHeadline}")
    Call<Article> getArticle(@Path("SiteName") String siteName, @Path("UrlName") String UrlName,@Path("UrlFriendlyDate") String urlFriendlyDate,
                             @Path("UrlFriendlyHeadline") String urlFriendlyHeadline, @Query("format") String format);

}
