package com.tangzhentao.network;

import com.tangzhentao.network.body.BsRequest;
import com.tangzhentao.network.body.BsResponse;
import com.tangzhentao.network.executor.BsExecutor;
import com.tangzhentao.network.oberver.NetworkLiveData;
import com.tangzhentao.network.utils.GsonUtils;
import com.tangzhentao.network.utils.NetWorkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description:网络请求
 *
 * @author tangzhentao
 * @date 2019/5/16
 */
public class BsHttp {
    private static BsHttp mTztHttp;
    private OkHttpClient mOkHttpClient;
    private Interceptor mNetInterceptor;

    private BsHttp() {
        initOkHttpClient();
    }


    public static BsHttp get() {
        if (mTztHttp == null) {
            mTztHttp = new BsHttp();
        }

        return mTztHttp;
    }

    public void setNetInterceptor(Interceptor interceptor) {
        this.mNetInterceptor= interceptor;
    }

    /**
     * 初始化OkHttpClient对象
     */
    private void initOkHttpClient() {
        if (mOkHttpClient == null ) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            if (mNetInterceptor != null) {
                builder.addInterceptor(mNetInterceptor);
            }

            mOkHttpClient = builder
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * get请求无参数
     * @param url url
     */
    public <T> void get(final String url, final NetworkLiveData<BsResponse<T>> callback) {
        BsExecutor.get().executeOnNetworkIO(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            request(request, callback);
        });
    }

    /**
     * get请求有参数
     * @param module module
     * @param bsRequest 请求参数
     */
    public <T> void get(String module, BsRequest bsRequest, NetworkLiveData<BsResponse<T>> callback) {
        BsExecutor.get().executeOnNetworkIO(() -> {
            MediaType json = MediaType.parse("applicaiton/json; charset=utf-8");
            String string = GsonUtils.getGson().toJson(bsRequest);
            RequestBody body = RequestBody.create(json, string);

            Request request = new Request.Builder()
                    .get()
                    .url(NetWorkUtils.HTTP_BASE + module)
                    .post(body)
                    .build();

            request(request, callback);
        });
    }

    /**
     * post请求
     * @param module 具接口
     * @param params 请求参数
     */
    public <T> void post(String module, Map<String, String> params, NetworkLiveData<BsResponse<T>> callback) {
        BsExecutor.get().executeOnNetworkIO(() -> {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }

            Request request = new Request.Builder()
                    .url(NetWorkUtils.HTTP_BASE + module)
                    .post(builder.build())
                    .build();

            request(request, callback);
        });
    }

    /**
     * post请求
     * @param module 具体接口
     * @param bsRequest 请求参数
     * @param observer liveData
     * @param <T> 观察对象
     */
    public <T> void post(String module, BsRequest bsRequest, NetworkLiveData<BsResponse<T>> observer) {
        BsExecutor.get().executeOnNetworkIO(() -> {
            String json = GsonUtils.getGson().toJson(bsRequest);
            HashMap params = GsonUtils.getGson().fromJson(json, HashMap.class);
            FormBody.Builder builder = new FormBody.Builder();
            for (Object key : params.keySet()) {
                builder.add(String.valueOf(key), String.valueOf(params.get(key)));
            }

            Request request = new Request.Builder()
                    .url(NetWorkUtils.HTTP_BASE + module)
                    .post(builder.build())
                    .build();

            request(request, observer);
        });
    }

    /**
     * 处理请求结果的地方
     * @param request request对象
     * @param observse livedata对象
     * @param <T> 需要的数据
     */
    private <T> void request(Request request, NetworkLiveData<BsResponse<T>> observse) {
        try {
            BsResponse<T> bsResponse;
            Response response = mOkHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                if (response.body() !=null) {
                    String result = response.body().string();
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt(NetWorkUtils.RESULT_CODE);
                    if (resultCode == NetWorkUtils.SUCCESS_CODE) {
                        bsResponse = BsResponse.fromJson(result, observse.getType());
                    }else {
                        bsResponse = new BsResponse<>();
                        bsResponse.resultCode = object.getInt(NetWorkUtils.RESULT_CODE);
                        bsResponse.resultMsg = object.getString(NetWorkUtils.RESULT_MSG);
                        bsResponse.data = null;
                    }
                }else {
                    bsResponse = new BsResponse<>();
                    bsResponse.resultCode = response.code();
                    bsResponse.resultMsg = response.message();
                    bsResponse.data = null;
                }
            }else {
                bsResponse = new BsResponse<>();
                bsResponse.resultCode = response.code();
                bsResponse.resultMsg = response.message();
                bsResponse.data = null;
            }

            BsExecutor.get().runOnUiThread(() -> observse.setValue(bsResponse));
        } catch (IOException e) {
            e.printStackTrace();
            BsResponse<T> errorResponse = new BsResponse<>();
            errorResponse.resultCode = NetWorkUtils.REQUEST_FAIL_CODE;
            errorResponse.resultMsg = e.getMessage();
            errorResponse.data = null;
            BsExecutor.get().runOnUiThread(() -> observse.setValue(errorResponse));
        } catch (JSONException e) {
            e.printStackTrace();
            BsResponse<T> errorResponse = new BsResponse<>();
            errorResponse.resultCode = NetWorkUtils.PARSE_FAIL_CODE;
            errorResponse.resultMsg = e.getMessage();
            errorResponse.data = null;
            BsExecutor.get().runOnUiThread(() -> observse.setValue(errorResponse));
        }
    }
}
