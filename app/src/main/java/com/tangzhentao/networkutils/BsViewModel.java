package com.tangzhentao.networkutils;

import android.arch.lifecycle.ViewModel;

import com.tangzhentao.network.body.BsResponse;
import com.tangzhentao.network.oberver.NetworkLiveData;

import java.util.List;


/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/22
 */
public class BsViewModel extends ViewModel {
    public NetworkLiveData<BsResponse<List<TestResponse>>> oberverRiver;

    public BsViewModel() {
        oberverRiver = new NetworkLiveData<BsResponse<List<TestResponse>>>() {};
    }

    public NetworkLiveData<BsResponse<List<TestResponse>>> getOberverRiver() {
        return oberverRiver;
    }
}
