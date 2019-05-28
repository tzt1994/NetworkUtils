package com.tangzhentao.libbase.cache;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public class BsLruCache implements ICache {
    private LruCache<String, Object> lruCache;

    private BsLruCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<>(cacheSize);
    }

    public static BsLruCache get() { return BsLruCacheHolder.instance; }

    @Override
    public synchronized void put(@NonNull String key, Object value) {
        if (TextUtils.isEmpty(key)) { return;}

        if (lruCache.get(key) != null) {
            lruCache.remove(key);
        }

        lruCache.put(key, value);
    }

    @Override
    public Object get(@NonNull String key) {
        return lruCache.get(key);
    }

    @Override
    public synchronized <T> T get(@NonNull String key, @NonNull Class<T> clazz) {
        try {
            return clazz.cast(lruCache.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(@NonNull String key) {
        return lruCache.get(key) != null;
    }

    @Override
    public void remove(@NonNull String key) {
        if (lruCache.get(key) != null) {
            lruCache.remove(key);
        }
    }

    @Override
    public void clear() {
        lruCache.evictAll();
    }

    private static class BsLruCacheHolder {
        private static BsLruCache instance = new BsLruCache();
    }
}
