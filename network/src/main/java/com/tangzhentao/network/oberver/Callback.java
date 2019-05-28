package com.tangzhentao.network.oberver;


import android.support.annotation.NonNull;

/**
 * Description:网络请求回调，不带请求内容
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public interface Callback {
    /**
     * 请求成功 返回数据体
     * @param json
     */
    default void onSuccess(String json) {

    }

    /**
     * 请求成功，返回了错误码
     * @param errorCode 错误码
     * @param message 错误信息
     */
    default void onError(int errorCode, @NonNull String message) {

    }

    /**
     * 网络请求失败
     *
     * @param failCode 失败码
     * @param message 失败信息
     */
    default void onFailure(int failCode, @NonNull String message) {

    }

    /**
     * 处理没有数据的情况
     * @param isSuccess 请求是否成
     */
    default void onNoData(boolean isSuccess) {

    }
}
