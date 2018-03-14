package com.ljf.wechatmoment.recyclerview;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TypePool {
    private final List<Class<?>> mContents;
    private final List<BaseViewProvider> mProviders;

    TypePool() {
        mContents = new ArrayList<>();
        mProviders = new ArrayList<>();
    }

    void register(Class<?> clazz, BaseViewProvider provider) {
        if (!mContents.contains(clazz)) {
            mContents.add(clazz);
            mProviders.add(provider);
        } else {
            int index = mContents.indexOf(clazz);
            mProviders.set(index, provider);
        }
    }

    int indexOf(@NonNull final Class<?> clazz) throws ProviderNotFoundException {
        int index = mContents.indexOf(clazz);
        if (index >= 0) {
            return index;
        }
        throw new ProviderNotFoundException(clazz);
    }

    BaseViewProvider getProviderByIndex(int index) {
        return mProviders.get(index);
    }

    <T extends BaseViewProvider> T getProviderByClass(final Class<?> clazz) {
        return (T) getProviderByIndex(indexOf(clazz));
    }

    public List<Class<?>> getContents() {
        return mContents;
    }

    public List<BaseViewProvider> getProviders() {
        return mProviders;
    }

    public class ProviderNotFoundException extends RuntimeException {

        ProviderNotFoundException(Class<?> clazz) {
            super("Have you registered the provider for {className}.class in the adapter/pool?"
                    .replace("{className}", clazz.getSimpleName()));
        }
    }
}
