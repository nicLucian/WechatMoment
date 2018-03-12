package com.ljf.wechatmoment.recyclerview;


import android.content.Context;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HeaderProvider extends BaseViewProvider<Header> {
    private RecyclerViewHolder mViewHolder;

    public HeaderProvider(Context context) {
        super(context, R.layout.recyclerview_header);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Header bean) {
        mViewHolder = holder;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetUserInfo(UserInfo userInfo) {
        mViewHolder.setText(R.id.tv_user_name, userInfo.getNickName());
        mViewHolder.setImageView(R.id.iv_avatar, userInfo.getAvatar());
        mViewHolder.setImageView(R.id.iv_profile, userInfo.getProfile());
    }
}
