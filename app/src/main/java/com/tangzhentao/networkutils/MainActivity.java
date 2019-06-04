package com.tangzhentao.networkutils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tangzhentao.libbase.base.BaseActivity;
import com.tangzhentao.libbase.base.TitleParams;
import com.tangzhentao.libbase.utils.DimenSizeUtils;
import com.tangzhentao.libbase.utils.ToastUtils;
import com.tangzhentao.network.body.BsResponse;
import com.tangzhentao.network.oberver.NetworkLiveData;
import com.tangzhentao.network.oberver.NetworkObserve;
import com.tangzhentao.network.repository.BsRepository;

import java.util.List;

public class MainActivity extends BaseActivity {
    private TextView textView;
    private EditText etInput;
    private RiliRepository repository = BsRepository.get(RiliRepository.class);

    @Override
    protected TitleParams getTitleParams() {
        TitleParams titleParams = new TitleParams(createLeftBackImgAction(), "标题栏", createRightTextAction("设置", new Intent(mContext, SettingActivity.class)));
//        titleParams.titleHeight = DimenSizeUtils.dpTopx(50);
        return titleParams;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository.register();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        textView = findViewById(R.id.tv_test);
        etInput = findViewById(R.id.et_input_river);
    }

    @Override
    protected void initData() {
        final NetworkLiveData<BsResponse<List<TestResponse>>> obser =  repository.getBsViewModel().getOberverRiver();
        obser.observe(this, new NetworkObserve<List<TestResponse>>() {
            @Override
            public void onSuccess(@NonNull List<TestResponse> testResponses) {
                StringBuilder builder = new StringBuilder();
                if (testResponses.size() > 0) {
                    for (TestResponse response : testResponses) {
                        builder.append("id:").append(response.id).append("   省:").append(response.province).append("   市:").append(response.city).append("   区或县:").append(response.district).append("\n");
                    }
                }
                textView.setText(builder.toString());
            }

            @Override
            public void onFailure(int failCode, @NonNull String message) {
                Toast.makeText(MainActivity.this, "请求失败 : " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int errorCode, @NonNull String message) {
                Toast.makeText(MainActivity.this, "请求成功返回错误信息 : " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEnd() {
                obser.setValue(null);
            }
        });
    }

    @Override
    protected void bindListener() {
        findViewById(R.id.btn_start).setOnClickListener(v -> {
            ToastUtils.ShowShort(mContext, "点击了");
            repository.getCitys();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.unRegister();
    }
}
