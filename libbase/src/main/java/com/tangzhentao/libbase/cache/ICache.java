package com.tangzhentao.libbase.cache;

import android.support.annotation.NonNull;

/**
 * Description:缓存接口
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public interface ICache {
    <T> void put(@NonNull String key, T value);

    Object get(@NonNull String key);

    <T> T get(@NonNull String key, @NonNull Class<T> clazz);

    boolean contains(@NonNull String key);

    void remove(@NonNull String key);

    void clear();
}
