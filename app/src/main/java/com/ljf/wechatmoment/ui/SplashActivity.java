package com.ljf.wechatmoment.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.ErrorMessage;
import com.ljf.wechatmoment.data.LoadCompleteMessage;
import com.ljf.wechatmoment.data.Repository;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashActivity extends Activity {
    private TextView mStatusTv;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStatusTv = (TextView) findViewById(R.id.tv_status);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        EventBus.getDefault().register(this);
        Repository.getInstance().loadTweets();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadTweetComplete(LoadCompleteMessage message) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadError(ErrorMessage message) {
        Toast.makeText(this, message.getContent(), Toast.LENGTH_SHORT).show();
        mStatusTv.setText("网络异常，数据未能成功加载");
        mProgressBar.setVisibility(View.GONE);
    }
}
