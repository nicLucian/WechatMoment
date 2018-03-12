package com.ljf.wechatmoment.data;


import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static volatile Repository sInstance;
    private static final Object sLock = new Object();
    private static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com/";

    private RepositoryService mService;
    private EventBus mEventBus;
    private List<Tweet> mTweets;

    private Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(RepositoryService.class);
        mEventBus = EventBus.getDefault();
    }

    public static Repository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new Repository();
                }
            }
        }
        return sInstance;
    }

    public void loadUserInfo() {
        Call<UserInfo> call = mService.userInfo();
        call.enqueue(new CommonCallBack<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                mEventBus.post(response.body());
            }
        });
    }

    public void loadTweets() {
        Call<List<Tweet>> call = mService.tweets();
        call.enqueue(new CommonCallBack<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                mTweets = response.body();
                mEventBus.post(new LoadCompleteMessage());
            }
        });
    }

    private abstract class CommonCallBack<T> implements Callback<T> {

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            ErrorMessage errorMessage = new ErrorMessage("网络错误，请稍后重试");
            mEventBus.post(errorMessage);
        }
    }
}
