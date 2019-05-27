package com.ghuyfel.news.api;

import com.ghuyfel.news.constants.Constants;
import com.ghuyfel.news.models.Article;
import com.ghuyfel.news.models.News;
import com.ghuyfel.news.utils.ApiUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private Retrofit retrofit;
    private Api api;
    private static ApiManager instance;
    ApiResponseListener listener;

    private Callback<List<News>> newsCallBack = new Callback<List<News>>() {
        @Override
        public void onResponse(Call<List<News>> call, Response<List<News>> response) {
            if (response.isSuccessful() && listener != null) {
                ((ApiNewsResponseListener) listener).getNewsSucceeded(response.body());
            }
        }

        @Override
        public void onFailure(Call<List<News>> call, Throwable t) {
        }

    };

    private Callback<Article> articleCallback = new Callback<Article>() {
        @Override
        public void onResponse(Call<Article> call, Response<Article> response) {
            if (response.isSuccessful() && listener != null) {
                ((ApiArticleResponseListener) listener).getArticleSucceeded(response.body());
            }
        }

        @Override
        public void onFailure(Call<Article> call, Throwable t) {
        }
    };

    private Interceptor interceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);

            if (!isSucceededRequest(response)) {
                listener.requestFailed(ApiUtils.getDescriptionForErrorCode(response.code()));
            }
            return response;
        }
    };

    private boolean isSucceededRequest(okhttp3.Response response) {
        return response.code() == Constants.SUCCEEDED;
    }

    private ApiManager() {
        initialiseApi();
    }

    private void initialiseApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public ApiManager getNews() {
        Call<List<News>> news = api.getNews(Constants.FORMAT_JSON);
        news.enqueue(newsCallBack);
        return this;
    }

    public ApiManager getArticleForNews(News news) {
        Call<Article> article = api.getArticle(news.getSiteName(), news.getUrlName(), news.getUrlFriendlyDate(), news.getUrlFriendlyHeadline(), Constants.FORMAT_JSON);
        article.enqueue(articleCallback);
        return this;
    }

    public void listen(ApiArticleResponseListener listener) {
        this.listener = listener;
    }

    public static synchronized ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    public interface ApiResponseListener {
        void requestFailed(ApiErrorMessage apiErrorMessage);
    }

    public interface ApiArticleResponseListener extends ApiResponseListener {
        void getArticleSucceeded(Article article);
    }

    public interface ApiNewsResponseListener extends ApiResponseListener {
        void getNewsSucceeded(List<News> news);
    }
}
