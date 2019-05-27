package com.ghuyfel.news.managers;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ghuyfel.news.R;

public class GlideManager {

    public static void loadImageInView(ImageView imgView, String url) {
        Glide.with(imgView.getContext())
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_image_placeholder)
                .into(imgView);
    }

    public static String getImageUrl(String url, String imageName) {
        return url.concat(imageName);
    }
}
