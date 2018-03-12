package com.ljf.wechatmoment.data;


import com.google.gson.annotations.SerializedName;

public class ImageUrl {
    @SerializedName("url")
    private String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "mUrl='" + mUrl + '\'' +
                '}';
    }
}
