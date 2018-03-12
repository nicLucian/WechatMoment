package com.ljf.wechatmoment.data;


import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("profile-image")
    private String mProfile;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("username")
    private String mUserName;
    
    @SerializedName("nick")
    private String mNickName;

    public String getProfile() {
        return mProfile;
    }

    public void setProfile(String profile) {
        mProfile = profile;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

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

    @Override
    public String toString() {
        return "UserInfo{" +
                "mProfile='" + mProfile + '\'' +
                ", mAvatar='" + mAvatar + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mNickName='" + mNickName + '\'' +
                '}';
    }
}
