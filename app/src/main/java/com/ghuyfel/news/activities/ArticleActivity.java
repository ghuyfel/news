package com.ghuyfel.news.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.ghuyfel.news.R;
import com.ghuyfel.news.managers.ConnectionManager;
import com.ghuyfel.news.constants.Constants;
import com.ghuyfel.news.databinding.ActivityArticleBinding;
import com.ghuyfel.news.models.News;
import com.ghuyfel.news.controllers.ArticleController;

public class ArticleActivity extends AppCompatActivity {
    private ActivityArticleBinding binding;
    private ArticleController articleController;
    private News news;
    private Button refresh;

    private View.OnClickListener retryClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retry();
        }
    };

    private String currentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!ConnectionManager.isDeviceConnected(this)) {
            onConnectionLost();
        } else {
            onConnectionAvailable();
        }
    }

    private void onConnectionLost() {
        setContentView(R.layout.dialog_error);
        refresh = findViewById(R.id.cancel);
        refresh.setText(getResources().getString(R.string.retry));
        refresh.setOnClickListener(retryClickListener);
    }

    private void onConnectionAvailable() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article);
        articleController = new ArticleController(binding);
        getArticle();
    }

    private void retry() {
        if (!ConnectionManager.isDeviceConnected(this)) {
            Snackbar.make(refresh, getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
            return;
        }
        Snackbar.make(refresh, getResources().getString(R.string.connected), Snackbar.LENGTH_SHORT).show();
        onConnectionAvailable();
    }

    private void getArticle() {
        articleController.getArticle(getNewsFromIntent());
    }

    private News getNewsFromIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constants.KEY_BUNDLE);
        news = (News) bundle.getSerializable(Constants.KEY_NEWS);
        return news;

    }

}
