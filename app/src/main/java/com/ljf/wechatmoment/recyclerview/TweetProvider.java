package com.ljf.wechatmoment.recyclerview;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.Comment;
import com.ljf.wechatmoment.data.Tweet;
import com.ljf.wechatmoment.ui.ImageContainer;

import java.util.List;

public class TweetProvider extends BaseViewProvider<Tweet> {
    private static final int IMAGE_COUNT_PER_ROW = 3;

    private LinearLayout mCommentContainer;
    private ImageContainer mImagesContainer;
    private LinearLayout.LayoutParams mParams;

    public TweetProvider(Context context) {
        super(context, R.layout.tweet_item);
        Resources resources = context.getResources();
        int width = resources.getDimensionPixelOffset(R.dimen.image_width);
        int height = resources.getDimensionPixelOffset(R.dimen.image_height);
        int margin = resources.getDimensionPixelOffset(R.dimen.image_margin);
        mParams = new LinearLayout.LayoutParams(width, height);
        mParams.setMargins(margin, margin, margin, margin);
    }

    @Override
    public void onBindView(RecyclerViewHolder holder, Tweet tweet) {
        if (tweet.isEmpty()) {
            return;
        }
        setBasicInfo(holder, tweet);

        mImagesContainer = holder.get(R.id.ll_tweet_images_container);
        mImagesContainer.setImageUrls(tweet.getImages());

        mCommentContainer = holder.get(R.id.ll_tweet_comment_container);
        mCommentContainer.removeAllViews();
        addComments(tweet.getComments());
    }

    private void addComments(List<Comment> comments) {
        if (comments == null) {
            return;
        }
        for (Comment comment : comments) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.comment_container, null);
            TextView senderNameTv = (TextView) linearLayout.findViewById(R.id.tv_comment_sender_name);
            TextView commentContent = (TextView) linearLayout.findViewById(R.id.tv_comment_content);
            senderNameTv.setText(comment.getSender().getNickName() + ":");
            commentContent.setText(comment.getContent());
            mCommentContainer.addView(linearLayout);
        }
    }

    private void setBasicInfo(RecyclerViewHolder holder, Tweet tweet) {
        holder.setImageView(R.id.iv_tweet_sender_avatar, tweet.getSender().getAvatar());
        holder.setText(R.id.tv_tweet_sender_name, tweet.getSender().getNickName());
        holder.setText(R.id.tv_tweet_content, tweet.getContent());
    }
}
