package com.ghuyfel.news.listeners;

import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.models.News;

public interface NewsListener {
    void onItemClicked(News news);
    void onNewsHeadlinesReady(News news);
    void onNewsRequestFailed(ApiErrorMessage message);
}
