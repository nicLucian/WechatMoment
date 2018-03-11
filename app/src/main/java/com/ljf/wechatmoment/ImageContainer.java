package com.ljf.wechatmoment;


import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Locale;

public class ImageContainer extends ViewGroup {
    private final static int IMAGE_MAX_COUNT = 9;
    private final static int MAX_COLUMNS = 3;

    private int mImageWidth;
    private int mImageHeight;
    private int mImageMargin;
    private List<String> mImageUrls;

    Context mContext;

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

    public void setImageUrls(List<String> imageUrls) {
        if (imageUrls == null) {
            throw new IllegalArgumentException("imageUrls cannot be null");
        }
        int size = imageUrls.size();
        if (size > IMAGE_MAX_COUNT) {
            throw new IllegalArgumentException(String.format(
                    Locale.getDefault(),
                    "imageUrls's size should be less than %d but is %d",
                    IMAGE_MAX_COUNT,
                    size));
        }
        mImageUrls = imageUrls;
        addImages(mImageUrls);
        requestLayout();
    }

    private void addImages(List<String> imageUrls) {
        for (String url : imageUrls) {
            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams params = new LayoutParams(mImageWidth, mImageHeight);
            addView(imageView, params);
            Picasso.get(mContext).load(url).into(imageView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = mImageUrls.size();
        int row = (size + size % MAX_COLUMNS) / MAX_COLUMNS;
        int column = size > MAX_COLUMNS ? MAX_COLUMNS : size;
        int width = column * mImageWidth + (column - 1) * mImageMargin;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
