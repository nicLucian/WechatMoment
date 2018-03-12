package com.ljf.wechatmoment.recyclerview;


import android.content.Context;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.Comment;
import com.ljf.wechatmoment.data.ImageUrl;
import com.ljf.wechatmoment.data.Tweet;

import java.util.List;

public class TweetProvider extends BaseViewProvider<Tweet> {
    public TweetProvider(Context context) {
        super(context, R.layout.tweet_item);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Tweet tweet) {
        if (tweet.isEmpty()) {
            return;
        }
        setBasicInfo(holder, tweet);
        addImages(tweet.getImages());
        addComments(tweet.getComments());
    }

    private void addImages(List<ImageUrl> images) {

    }

    private void addComments(List<Comment> comments) {

    }

    private void setBasicInfo(RecyclerViewHolder holder, Tweet tweet) {
        holder.setImageView(R.id.iv_tweet_sender_avatar, tweet.getSender().getAvatar());
        holder.setText(R.id.tv_tweet_sender_name, tweet.getSender().getNickName());
        holder.setText(R.id.tv_tweet_content, tweet.getContent());
    }
}
