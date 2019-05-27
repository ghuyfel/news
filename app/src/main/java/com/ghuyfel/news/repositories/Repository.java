package com.ghuyfel.news.repositories;

import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.api.ApiManager;
import com.ghuyfel.news.models.Article;
import com.ghuyfel.news.models.News;

import java.util.List;

public class Repository implements ApiManager.ApiArticleResponseListener, ApiManager.ApiNewsResponseListener {

    private RepositoryListener listener;

    public Repository(RepositoryListener listener) {
        this.listener = listener;
    }

    public void getNews() {
        ApiManager.getInstance().getNews().listen( this);
    }

    public void getArticle(News news) {
         ApiManager.getInstance().getArticleForNews(news).listen(this);
    }

    @Override
    public void getNewsSucceeded(List<News> news) {
        ((RepositoryNewsListener)listener).onGetNewsSucceeded(news);
    }

    @Override
    public void getArticleSucceeded(Article article) {
        ((RepositoryArticleListener)listener).onGetArticleSucceeded(article);
    }

    @Override
    public void requestFailed(ApiErrorMessage message) {
        listener.onRequestFailed(message);
    }

    public interface RepositoryNewsListener extends RepositoryListener {
        void onGetNewsSucceeded(List<News> news);
    }

    public interface RepositoryArticleListener extends RepositoryListener {
        void onGetArticleSucceeded(Article article);
    }

    public interface RepositoryListener {
        void onRequestFailed(ApiErrorMessage message);
    }



}
