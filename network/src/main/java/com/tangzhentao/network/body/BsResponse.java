package com.tangzhentao.network.body;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.tangzhentao.network.utils.GsonUtils;
import com.tangzhentao.network.utils.NetWorkUtils;

import java.io.Serializable;
import java.lang.reflect.Type;


/**
 * Description:返回体基类
 *
 * @author tangzhentao
 * @date 2019/5/16
 */
public class BsResponse<T> implements Serializable {

    @SerializedName(NetWorkUtils.RESULT_CODE)
    public int resultCode;

    @SerializedName(NetWorkUtils.RESULT_MSG)
    public String resultMsg;

    @SerializedName(NetWorkUtils.DATA)
    public T data;

    public static <T> BsResponse<T> fromJson(@Nullable String json, @NonNull Type type) {
        if (json == null) {
            return null;
        }

        return GsonUtils.getGson().fromJson(json, type);
    }
}
