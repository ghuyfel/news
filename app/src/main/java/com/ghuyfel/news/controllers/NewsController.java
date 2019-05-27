package com.ghuyfel.news.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghuyfel.news.listeners.NewsListener;
import com.ghuyfel.news.R;
import com.ghuyfel.news.activities.ArticleActivity;
import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.managers.ConnectionManager;
import com.ghuyfel.news.managers.GlideManager;
import com.ghuyfel.news.constants.Constants;
import com.ghuyfel.news.databinding.ActivityNewsBinding;
import com.ghuyfel.news.models.News;
import com.ghuyfel.news.utils.ApiUtils;
import com.ghuyfel.news.utils.DateUtils;

public class NewsController implements NewsListener {

    private ActivityNewsBinding binding;
    private Context context;

    public NewsController(ActivityNewsBinding binding) {
        this.binding = binding;
        this. context = this.binding.getRoot().getContext();
    }

    @Override
    public void onItemClicked(News news) {
        if(!ConnectionManager.isDeviceConnected(binding.getRoot().getContext())) {
            ConnectionManager.showConnectionDialog(binding.getRoot().getContext());
            return;
        }
        startActivityForNews(news);
    }

    @Override
    public void onNewsHeadlinesReady(News news) {
        updateHeadlinesView(news);
    }

    @Override
    public void onNewsRequestFailed(ApiErrorMessage message) {
        showRequestFailedDialog(message);
    }

    private void startActivityForNews(News news) {
        Intent intent = new Intent(context, ArticleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_NEWS, news);
        intent.putExtra(Constants.KEY_BUNDLE, bundle);
        context.startActivity(intent);
    }

    private void updateHeadlinesView(final News news) {
        binding.setNews(news);
        binding.spinner.setVisibility(View.GONE);
        binding.dateCreated.setText(DateUtils.getFormattedDate(news.getDateCreated()));
        this.binding.category.setVisibility(news.getCategory().compareToIgnoreCase(Constants.EMPTY_STRING) == 0 ? View.GONE : View.VISIBLE);
        String url = GlideManager.getImageUrl(news.getImageUrlRemote(), news.getLargeImageName());
        GlideManager.loadImageInView(binding.image, url);
        binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClicked(news);
            }
        });
        binding.executePendingBindings();
        binding.layout.setVisibility(View.VISIBLE);
    }

    private void showRequestFailedDialog(ApiErrorMessage message) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Light);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_error);
        View view =((Activity) context).getLayoutInflater().inflate(R.layout.dialog_error, null);
        Button button = view.findViewById(R.id.cancel);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(ApiUtils.getImageForErrorCode(message.getCode()));
        TextView textView = view.findViewById(R.id.message);
        textView.setText(message.getMessage());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                dialog.cancel();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
