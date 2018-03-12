/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-04-08 16:13:03
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.ljf.wechatmoment.recyclerview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private View mRootView;
    private final SparseArray<View> mViews = new SparseArray<View>();

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
    }

    public View getRootView() {
        return mRootView;
    }

    @SuppressWarnings("unchecked")
    private <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) mRootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener l, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(l);
        }
    }

    public void setText(int id, String text) {
        TextView textView = get(id);
        textView.setText(text);
    }

    public void setImageView(int id, String url) {
        ImageView imageView = get(id);
        Picasso.Builder builder = new Picasso.Builder(mRootView.getContext())
                .loggingEnabled(true);
        builder.build().load(url).into(imageView);
    }

    public void setImageView(int id, Drawable drawable) {
        ImageView imageView = get(id);
        imageView.setImageDrawable(drawable);
    }
}
