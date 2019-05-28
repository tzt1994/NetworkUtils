package com.tangzhentao.networkutils;

import com.google.gson.annotations.SerializedName;
import com.tangzhentao.network.body.BsRequest;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/22
 */
public class TestRequest extends BsRequest {
    @SerializedName("river")
    public String river;

    @SerializedName("key")
    public String key;

    @Override
    protected void initParams() {

    }
}
