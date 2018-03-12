package com.ljf.wechatmoment.data;


import com.google.gson.annotations.SerializedName;

public class Sender {
    @SerializedName("username")
    private String mUserName;

    @SerializedName("nick")
    private String mNickName;

    @SerializedName("avatar")
    private String mAvatar;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        mNickName = nickName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "mNickName='" + mNickName + '\'' +
                '}';
    }
}
