package com.ljf.wechatmoment.ui;


import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ljf.wechatmoment.R;
import com.ljf.wechatmoment.data.ImageUrl;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ImageContainer extends ViewGroup {
    private static final int MAX_IMAGE_COUNT = 9;
    private static final int IMAGE_COUNT_PER_ROW = 3;

    private Context mContext;
    private int mImageWidth;
    private int mImageHeight;
    private int mImageMargin;

    public ImageContainer(Context context) {
        this(context, null);
    }

    public ImageContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImageContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        Resources resources = context.getResources();
        mImageWidth = resources.getDimensionPixelOffset(R.dimen.image_width);
        mImageHeight = resources.getDimensionPixelOffset(R.dimen.image_height);
        mImageMargin = resources.getDimensionPixelOffset(R.dimen.image_margin);
    }

    public void setImageUrls(List<ImageUrl> urls) {
        removeAllViews();
        if (urls == null) {
            return;
        }

        int size = urls.size();
        if (size > MAX_IMAGE_COUNT) {
            throw new IllegalArgumentException(String.format(
                    Locale.getDefault(),
                    "urls' length should be less than %d but is %d",
                    MAX_IMAGE_COUNT,
                    size));
        }
        addImageViews(urls);
        requestLayout();
    }

    private void addImageViews(List<ImageUrl> urls) {
        for (ImageUrl url : urls) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            LayoutParams params = new LayoutParams(mImageWidth, mImageHeight);
            addView(imageView, params);
            Picasso.get()
                    .load(url.getUrl())
                    .placeholder(R.mipmap.aa)
                    .into(imageView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        int row = childCount / IMAGE_COUNT_PER_ROW + ((childCount % IMAGE_COUNT_PER_ROW == 0) ? 0 : 1);
        if (row != 0) {
            height = mImageHeight * row + mImageMargin * row * 2;
            int widthFactor = row > 1 ? IMAGE_COUNT_PER_ROW : childCount;
            width = widthFactor * mImageWidth + widthFactor * mImageMargin * 2;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int row = i / IMAGE_COUNT_PER_ROW;
            int column = i % IMAGE_COUNT_PER_ROW;
            int left = (2 * column + 1) * mImageMargin + column * mImageWidth;
            int top = (2 * row + 1) * mImageMargin + row * mImageHeight;
            int right = left + mImageWidth;
            int bottom = top + mImageHeight;
            child.layout(left, top, right, bottom);
        }
    }

}
