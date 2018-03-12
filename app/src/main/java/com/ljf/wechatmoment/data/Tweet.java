package com.ljf.wechatmoment.data;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tweet {
    @SerializedName("content")
    private String mContent;

    @SerializedName("images")
    private List<ImageUrl> mImages;

    @SerializedName("sender")
    private Sender mSender;

    @SerializedName("comments")
    private List<Comment> mComments;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public List<ImageUrl> getImages() {
        return mImages;
    }

    public void setImages(List<ImageUrl> images) {
        mImages = images;
    }

    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    public List<Comment> getComments() {
        return mComments;
    }

    public void setComments(List<Comment> comments) {
        mComments = comments;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(mContent) && (mImages == null || mImages.size() == 0);
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "isEmpty=" + isEmpty() +
                ", mSender=" + mSender +
                ", mContent='" + mContent + '\'' +
                ", mImages=" + mImages +
                ", mComments=" + mComments +
                '}';
    }
}
