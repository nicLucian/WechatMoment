package com.ljf.wechatmoment.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseViewProvider<T> {
    private LayoutInflater mInflater;
    private int mLayoutId;
    protected Context mContext;

    public BaseViewProvider(Context context, @LayoutRes int layoutId) {
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mContext = context;
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(mLayoutId, parent, false);
        return new RecyclerViewHolder(view);
    }

    public abstract void onBindView(RecyclerViewHolder holder, T bean);
}
