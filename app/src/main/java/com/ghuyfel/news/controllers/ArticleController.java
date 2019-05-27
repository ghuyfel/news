package com.ghuyfel.news.controllers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.icu.util.LocaleData;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghuyfel.news.R;
import com.ghuyfel.news.api.ApiErrorMessage;
import com.ghuyfel.news.managers.GlideManager;
import com.ghuyfel.news.constants.Constants;
import com.ghuyfel.news.databinding.ActivityArticleBinding;
import com.ghuyfel.news.models.Article;
import com.ghuyfel.news.models.News;
import com.ghuyfel.news.repositories.Repository;
import com.ghuyfel.news.utils.ApiUtils;
import com.ghuyfel.news.utils.DateUtils;

import java.io.ByteArrayInputStream;

public class ArticleController implements Repository.RepositoryArticleListener {
    private static final String TAG = ArticleController.class.getSimpleName();
    private ActivityArticleBinding binding;
    private Repository repo;

    public ArticleController(ActivityArticleBinding binding) {
        this.binding = binding;
        this.repo = new Repository(this);
    }

    public void getArticle(News news) {
        repo.getArticle(news);
    }

    private  void showConnectionDialog(final ApiErrorMessage message) {

        ((Activity)binding.getRoot().getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(binding.getRoot().getContext(), android.R.style.Theme_Light);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_error);
                View view =((Activity) binding.getRoot().getContext()).getLayoutInflater().inflate(R.layout.dialog_error, null);
                Button button = view.findViewById(R.id.cancel);
                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageResource(ApiUtils.getImageForErrorCode(message.getCode()));
                TextView textView = view.findViewById(R.id.message);
                textView.setText(message.getMessage());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        ((Activity) binding.getRoot().getContext()).finish();
                    }
                });
                dialog.setContentView(view);
                dialog.show();
            }
        });


    }

    @Override
    public void onRequestFailed(ApiErrorMessage message) {
        showConnectionDialog(message);
    }

    @Override
    public void onGetArticleSucceeded(final Article article) {
        this.binding.setArticle(article);
        this.binding.body.getSettings().setBlockNetworkLoads(true);
        this.binding.body.loadData( article.getBody(), Constants.MIMETYPE_HTML, null);
        WebViewClient client = new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if(url.compareToIgnoreCase(Constants.BLANK_URL) == 0) {
                    binding.body.loadData(article.getBody(), Constants.MIMETYPE_HTML, null);
                }

                super.onPageStarted(view,url,favicon);
            }
        };
        this.binding.body.setWebViewClient(client);
        this.binding.dateCreated.setText(DateUtils.getFormattedDate(article.getDateCreated()));
        String url = GlideManager.getImageUrl(article.getImageUrlLocal(), article.getLargeImageName());
        GlideManager.loadImageInView(this.binding.image, url);
    }
}
