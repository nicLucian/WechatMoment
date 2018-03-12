
package com.ljf.wechatmoment.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<Object> mItems = new ArrayList<>();
    private TypePool mTypePool;

    private boolean hasHeader = false;
    private boolean hasFooter = false;

    public HeaderFooterAdapter() {
        mTypePool = new TypePool();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mItems.get(position);
        return mTypePool.indexOf(item.getClass());
    }

    public void register(Class<?> clazz, BaseViewProvider provider) {
        mTypePool.register(clazz, provider);
    }

    public void registerHeader(BaseViewProvider provider) {
        if (hasHeader) {
            return;
        }
        Header header = new Header();
        mTypePool.register(header.getClass(), provider);
        mItems.add(0, header);
        hasHeader = true;
        notifyDataSetChanged();
    }

    public void registerFooter(BaseViewProvider provider) {
        if (hasFooter) {
            return;
        }
        Footer footer = new Footer();
        mTypePool.register(footer.getClass(), provider);
        mItems.add(footer);
        hasFooter = true;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        BaseViewProvider provider = getProviderByIndex(indexViewType);
        return provider.onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Object item = mItems.get(position);
        BaseViewProvider provider = getProviderByClass(item.getClass());
        provider.onBindView(holder, item);
    }

    public BaseViewProvider getProviderByIndex(int index) {
        return mTypePool.getProviderByIndex(index);
    }

    public <T extends BaseViewProvider> T getProviderByClass(@NonNull Class<?> clazz) {
        return mTypePool.getProviderByClass(clazz);
    }

    public void clearDatas() {
        int startIndex = 0;
        int endIndex = mItems.size();
        if (hasHeader) {
            startIndex++;
        }
        if (hasFooter) {
            endIndex--;
        }
        for (int i = endIndex - 1; i >= startIndex; i--) {
            mItems.remove(i);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<?> items) {
        if (hasFooter) {
            mItems.addAll(mItems.size() - 1, items);
        } else {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

}
