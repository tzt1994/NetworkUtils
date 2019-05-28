package com.tangzhentao.network.oberver;

import android.arch.lifecycle.MutableLiveData;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public abstract class NetworkLiveData<T> extends MutableLiveData<T> {
    private Type mType;

    public NetworkLiveData() {
        mType = getSuperclassTypeParameter(getClass());
    }

    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return Object.class;
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }

    public NetworkLiveData<T> setType(Type type) {
        mType = type;
        return this;
    }

    public Type getType() {
        return mType;
    }
}
