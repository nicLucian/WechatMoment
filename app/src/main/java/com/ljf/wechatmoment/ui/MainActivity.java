package com.ljf.wechatmoment.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.ErrorMessage;
import com.ljf.wechatmoment.data.Repository;
import com.ljf.wechatmoment.data.Tweet;
import com.ljf.wechatmoment.recyclerview.FooterProvider;
import com.ljf.wechatmoment.recyclerview.HeaderFooterAdapter;
import com.ljf.wechatmoment.recyclerview.HeaderProvider;
import com.ljf.wechatmoment.recyclerview.TweetProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private Repository mRepository;
    private HeaderFooterAdapter mAdapter;
    private boolean mCanLoadMore = true;
    private boolean mNeedClear;
    private int mCurrentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mRepository = Repository.getInstance();
        initRecyclerView();
        initRefreshView();
        loadUserInfo();
    }

    private void loadUserInfo() {
        mRepository.loadUserInfo();
    }

    private void initRecyclerView() {
        mAdapter = new HeaderFooterAdapter();
        mAdapter.registerHeader(new HeaderProvider(this));
        mAdapter.registerFooter(new FooterProvider(this) {
            @Override
            protected void loadMore() {
                mNeedClear = false;
                load();
            }
        });
        mAdapter.register(Tweet.class, new TweetProvider(this));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initRefreshView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCurrentPage = 1;
                mNeedClear = true;
                mCanLoadMore = true;
                mRepository.getTweets(mCurrentPage);
            }
        });
    }

    private void load() {
        if (mCanLoadMore) {
            mRepository.getTweets(mCurrentPage++);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTweet(List<Tweet> tweets) {
        if (tweets == null || tweets.size() == 0) {
            mCanLoadMore = false;
        }
        if (mNeedClear) {
            mRefreshLayout.setRefreshing(false);
            mAdapter.clearDatas();
        }
        mAdapter.addDatas(tweets);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onError(ErrorMessage message) {
        Toast.makeText(this, message.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
