package com.ghuyfel.news.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.ghuyfel.news.R;
import com.ghuyfel.news.adapters.NewsAdapter;
import com.ghuyfel.news.managers.ConnectionManager;
import com.ghuyfel.news.databinding.ActivityNewsBinding;
import com.ghuyfel.news.controllers.NewsController;

public class NewsActivity extends AppCompatActivity {

    private ActivityNewsBinding binding;
    private NewsController newsController;
    private Button refresh;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            retry();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        refresh.setOnClickListener(clickListener);
    }

    private void onConnectionAvailable() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        binding.spinner.setVisibility(View.VISIBLE);
        newsController = new NewsController(binding);
        setupUiElements();
    }

    private void retry() {
        if (!ConnectionManager.isDeviceConnected(this)) {
            Snackbar.make(refresh, getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_SHORT).show();
            return;
        }
        Snackbar.make(refresh, getResources().getString(R.string.connected), Snackbar.LENGTH_SHORT).show();
        onConnectionAvailable();
    }

    private void setupUiElements() {
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(new NewsAdapter(newsController));
    }

}
