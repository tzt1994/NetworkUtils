package com.tangzhentao.network.oberver;


import android.support.annotation.NonNull;

/**
 * Description:网络请求回调带请求内容
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public interface RspCallback<T> {

    /**
     * 请求成功 返回数据体
     *
     * @param t 数据
     */
    default void onSuccess(@NonNull T t) {

    }

    /**
     * 请求成功
     *
     * 没有或者不关心数据体
     */
    default void onSuccess() {

    }

    /**
     * 请求成功，返回了错误码
     * @param errorCode 错误码
     * @param message 错误信息
     */
    default void onError(int errorCode, @NonNull String message) {
        onNoData(false);
    }

    /**
     * 网络请求失败
     *
     * @param failCode 失败码
     * @param message 失败信息
     */
    default void onFailure(int failCode, @NonNull String message) {
        onNoData(false);
    }

    /**
     * 处理没有数据的情况
     * @param isSuccess 请求是否成功
     */
    default void onNoData(boolean isSuccess) {

    }


    /**
     * 请求结束
     */
    default void onEnd() {}
}
