package com.ljf.wechatmoment.data;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private static volatile Repository sInstance;
    private static final Object sLock = new Object();
    private static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com/";
    private static final int PAGE_TWEET_COUNT = 5;

    private RepositoryService mService;
    private EventBus mEventBus;
    private List<Tweet> mTweets = new ArrayList<>();

    private Executor mExecutor = Executors.newCachedThreadPool();

    private Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(RepositoryService.class);
        mEventBus = EventBus.getDefault();
    }

    public static Repository getInstance() {
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

    public void getTweets(int pageIndex) {
        mExecutor.execute(new GetTweetRunnable(pageIndex));
    }

    public void loadTweets() {
        Call<List<Tweet>> call = mService.tweets();
        call.enqueue(new CommonCallBack<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                List<Tweet> tweets = response.body();
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        if (!tweet.isEmpty()) {
                            mTweets.add(tweet);
                        }
                    }
                }
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

    private class GetTweetRunnable implements Runnable {
        private int mPageIndex;

        public GetTweetRunnable(int pageIndex) {
            mPageIndex = pageIndex;
        }

        @Override
        public void run() {
            int total = mTweets.size();
            List<Tweet> tweets = new ArrayList<>();
            int startIndex = (mPageIndex - 1) * PAGE_TWEET_COUNT;
            int endIndex = mPageIndex * PAGE_TWEET_COUNT;
            if (startIndex > total) {
                startIndex = total;
            }

            if (endIndex > total) {
                endIndex = total;
            }
            for (int i = startIndex; i < endIndex; i++) {
                tweets.add(mTweets.get(i));
            }
            mEventBus.post(tweets);
        }
    }
}
