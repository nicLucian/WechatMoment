package com.ljf.wechatmoment.recyclerview;


import android.content.Context;

import com.ljf.wechatmoment.R;

public class HeaderProvider extends BaseViewProvider<Header> {
    public HeaderProvider(Context context) {
        super(context, R.layout.recyclerview_header);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Header bean) {
        holder.setText(R.id.tv_user_name, "userName");
    }
}
