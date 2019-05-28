package com.tangzhentao.network.body;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.tangzhentao.network.utils.GsonUtils;
import com.tangzhentao.network.utils.NetWorkUtils;

import java.io.Serializable;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public class BsNoDataResponse implements Serializable {
    @SerializedName(NetWorkUtils.RESULT_CODE)
    public int resultCode;

    @SerializedName(NetWorkUtils.RESULT_MSG)
    public String resultMsg;

    public static BsNoDataResponse fromJson(@Nullable String json) throws Exception {
        if (json == null) {
            return null;
        }

        try {
            return GsonUtils.getGson().fromJson(json, BsNoDataResponse.class);
        }catch (Exception e) {
            throw e;
        }
    }
}
