package com.tangzhentao.networkutils;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tangzhentao.libbase.base.BaseActivity;
import com.tangzhentao.libbase.base.BaseTitle;
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
    protected boolean getStatusBarTransparent() {
        return true;
    }

    @Override
    protected TitleParams getTitleParams() {
        TitleParams titleParams = new TitleParams(new BaseTitle.BaseAction() {
            @Override
            public int getDrawable() {
                return R.drawable.left_back;
            }

            @Override
            public void doClick(View v) {
                ToastUtils.ShowShort(mContext, "点击了左边图片");
            }
        }, "标题栏", new BaseTitle.BaseAction() {
            @Override
            public int getDrawable() {
                return R.drawable.left_back;
            }

            @Override
            public void doClick(View v) {
                ToastUtils.ShowShort(mContext, "点击了右边图片");
            }
        });
        titleParams.BgColor = Color.parseColor("#1874cd");
        titleParams.Height = DimenSizeUtils.dpTopx(90);
        return titleParams;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository.register();

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tv_test);

        etInput = findViewById(R.id.et_input_river);

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

        findViewById(R.id.btn_start).setOnClickListener(v -> repository.getCitys());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repository.unRegister();
    }
}
