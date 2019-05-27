package com.ghuyfel.news.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ghuyfel.news.listeners.NewsListener;
import com.ghuyfel.news.R;
import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.managers.GlideManager;
import com.ghuyfel.news.constants.Constants;
import com.ghuyfel.news.databinding.ListItemNewsBinding;
import com.ghuyfel.news.models.News;
import com.ghuyfel.news.repositories.Repository;
import com.ghuyfel.news.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Repository.RepositoryNewsListener {

    private static final String TAG = NewsAdapter.class.getSimpleName();

    private List<News> newsList;
    private Repository repo;
    private NewsListener newsListener;

    public NewsAdapter(NewsListener newsListener) {
        newsList = new ArrayList<>();
        repo = new Repository(this);
        this.newsListener = newsListener;
        getNews();
    }

    private void getNews() {
        repo.getNews();
    }

    //Because we don't have a proper way to pick the main story (all items in the test data set return false for IsMainStory)
    private void pickRandomNewsAsHeadline() {
        if (newsList.isEmpty()) {
            return;
        }
        Random random = new Random();
        newsListener.onNewsHeadlinesReady(newsList.get(random.nextInt(newsList.size())));
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemNewsBinding newsBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_news, parent, false);
        return new NewsViewHolder(newsBinding, newsListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {
        News news = newsList.get(position);
        newsViewHolder.bind(news);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onGetNewsSucceeded(List<News> news) {
        newsList.clear();
        newsList.addAll(news);
        pickRandomNewsAsHeadline();
        notifyDataSetChanged();
    }

    @Override
    public void onRequestFailed(ApiErrorMessage message) {
        newsListener.onNewsRequestFailed(message);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ListItemNewsBinding binding;
        private NewsListener listener;

        public NewsViewHolder(@NonNull ListItemNewsBinding binding, NewsListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bind(News news) {
            this.binding.setNews(news);
            this.binding.category.setVisibility(news.getCategory().compareToIgnoreCase(Constants.EMPTY_STRING) == 0 ? View.GONE : View.VISIBLE);
            this.binding.dateCreated.setText(DateUtils.getFormattedDate(news.getDateCreated()));
            this.binding.getRoot().setOnClickListener(this);
            this.binding.getRoot().setBackgroundResource(R.color.selector_news);
            String url = GlideManager.getImageUrl(news.getImageUrlRemote(), news.getSmallImageName());
            GlideManager.loadImageInView(this.binding.image, url);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            listener.onItemClicked(binding.getNews());
        }
    }
}
