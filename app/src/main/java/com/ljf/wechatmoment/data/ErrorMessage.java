package com.ljf.wechatmoment.data;


public class ErrorMessage {
    private String mContent;

    public ErrorMessage(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "mContent='" + mContent + '\'' +
                '}';
    }
}
