package com.ljf.wechatmoment.recyclerview;


import android.content.Context;

import com.ljf.wechatmoment.R;

public class FooterProvider extends BaseViewProvider<Footer> {
    public FooterProvider(Context context) {
        super(context, R.layout.footer);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Footer bean) {
        loadMore();
    }

    protected void loadMore() {

    }
}
