package com.tangzhentao.network.repository;

import android.support.annotation.NonNull;

import com.tangzhentao.libbase.cache.BsLruCache;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Description:解除仓库类用于Viewmodel 和 LiveData配合使用，
 * 方便不同页面观察同一个数据
 *
 *
 * @author tangzhentao
 * @date 2019/5/16
 */
public class BsRepository implements releaseable {
    private boolean isRegistered;

    /**
     * 加入lrucache缓存
     */
    public void register() {
        if (isRegistered) {
            return;
        }

        BsLruCache.get().put(this.getClass().getName(), this);
    }

    /**
     * 从lrucache缓存中释放
     */
    public void unRegister() {
        if (!isRegistered) {
            return;
        }

        BsLruCache.get().remove(this.getClass().getName());
    }

    /**
     * 是否在lrucache缓存中
     * @return boolean
     */
    public boolean isRegistered() {
        return isRegistered;
    }

    @Override
    public void release() {
        if (isRegistered) {
            return;
        }

        BsLruCache.get().remove(this.getClass().getName());
    }

    public static <T extends BsRepository> T get(@NonNull Class<T> clazz) {
        T t = BsLruCache.get().get(clazz.getName(), clazz);
        if (null == t) {
            synchronized (BsRepository.class) {
                t = BsLruCache.get().get(clazz.getName(), clazz);
                if (null == t) {
                    Constructor<? extends BsRepository> constructor;

                    try {
                        constructor = clazz.getConstructor();
                        t = clazz.cast(constructor.newInstance());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return t;
    }
}
