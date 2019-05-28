package com.tangzhentao.network.oberver;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.tangzhentao.network.body.BsResponse;
import com.tangzhentao.network.utils.NetWorkUtils;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/21
 */
public class NetworkObserve<T> implements Observer<BsResponse<T>>, RspCallback<T> {
    @Override
    public void onChanged(@Nullable BsResponse<T> tBsResponse) {
        if (tBsResponse != null) {
            if (tBsResponse.resultCode == NetWorkUtils.SUCCESS_CODE) {
                if (tBsResponse.data != null) {
                    onSuccess(tBsResponse.data);
                }else {
                    onNoData(true);
                }
            }else {
                if (tBsResponse.resultCode == NetWorkUtils.REQUEST_FAIL_CODE) {
                    onFailure(tBsResponse.resultCode, tBsResponse.resultMsg);
                }else {
                    onError(tBsResponse.resultCode, tBsResponse.resultMsg);
                }

                onNoData(false);
            }
            onEnd();
        }
    }
}
