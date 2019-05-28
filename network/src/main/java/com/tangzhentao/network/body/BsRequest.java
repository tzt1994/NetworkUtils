package com.tangzhentao.network.body;

import com.tangzhentao.network.BsHttp;
import com.tangzhentao.network.oberver.NetworkLiveData;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Description:请求参数基类
 *
 * @author tangzhentao
 * @date 2019/5/16
 */
public abstract class BsRequest implements Serializable {


    public BsRequest() {
        initParams();
    }

    protected abstract void initParams();



    public <T> void post(String module, NetworkLiveData<BsResponse<T>> observable) {
        BsHttp.get().post(module, this, observable);
    }

    public <T> void post(String module, HashMap<String, String> params, NetworkLiveData<BsResponse<T>> observable) {
        BsHttp.get().post(module, params, observable);
    }

    public <T> void get(String module, NetworkLiveData<BsResponse<T>> observable) {
        BsHttp.get().get(module, this, observable);
    }
}
