package com.ljf.wechatmoment.data;


import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("content")
    private String mContent;

    @SerializedName("sender")
    private Sender mSender;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Sender getSender() {
        return mSender;
    }

    public void setSender(Sender sender) {
        mSender = sender;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "mContent='" + mContent + '\'' +
                ", mSender=" + mSender +
                '}';
    }
}
