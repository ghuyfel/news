package com.ghuyfel.news.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {

    @SerializedName("DateCreated")
    private String dateCreated;
    @SerializedName("Headline")
    private String headline;
    @SerializedName("Blurb")
    private String blurb;
    @SerializedName("Category")
    private String category;
    @SerializedName("ImageUrlRemote")
    private String imageUrlRemote;
    @SerializedName("ImageUrlLocal")
    private String imageUrlLocal;
    @SerializedName("SmallImageName")
    private String smallImageName;
    @SerializedName("LargeImageName")
    private String largeImageName;

    public String getImageUrlRemote() {
        return imageUrlRemote;
    }

    public String getImageUrlLocal() {
        return imageUrlLocal;
    }

    public String getSmallImageName() {
        return smallImageName;
    }

    public String getLargeImageName() {
        return largeImageName;
    }

    @SerializedName("SiteName")
    private String siteName;
    @SerializedName("UrlName")
    private String urlName;
    @SerializedName("UrlFriendlyDate")
    private String urlFriendlyDate;
    @SerializedName("UrlFriendlyHeadline")
    private String urlFriendlyHeadline;


    public String getDateCreated() {
        return dateCreated;
    }

    public String getHeadline() {
        return headline;
    }

    public String getBlurb() {
        return blurb;
    }

    public String getCategory() {
        return category;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUrlName() {
        return urlName;
    }

    public String getUrlFriendlyDate() {
        return urlFriendlyDate;
    }

    public String getUrlFriendlyHeadline() {
        return urlFriendlyHeadline;
    }
}
