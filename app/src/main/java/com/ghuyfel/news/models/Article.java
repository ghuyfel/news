package com.ghuyfel.news.models;

import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("Headline")
    private String headline;
    @SerializedName("DateCreated")
    private String dateCreated;
    @SerializedName("Body")
    private String body;
    @SerializedName("LargeImageName")
    private String largeImageName;
    @SerializedName("SmallImageName")
    private String smallImageName;
    @SerializedName("ImageUrlRemote")
    private String imageUrlRemote;
    @SerializedName("ImageUrlLocal")
    private String imageUrlLocal;

    public String getHeadline() {
        return headline;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getBody() {
        return body;
    }

    public String getLargeImageName() {
        return largeImageName;
    }

    public String getSmallImageName() {
        return smallImageName;
    }

    public String getImageUrlRemote() {
        return imageUrlRemote;
    }

    public String getImageUrlLocal() {
        return imageUrlLocal;
    }

    @Override
    public String toString() {
        return "Article{" +
                "headline='" + headline + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", body='" + body + '\'' +
                ", largeImageName='" + largeImageName + '\'' +
                ", imageUrlRemote='" + imageUrlRemote + '\'' +
                ", imageUrlLocal='" + imageUrlLocal + '\'' +
                '}';
    }
}
