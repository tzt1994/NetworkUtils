package com.tangzhentao.networkutils;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tangzhentao.libbase.base.BaseActivity;
import com.tangzhentao.libbase.base.TitleParams;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/29
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected boolean getStatusBarColorSameAsTitleBarColor() {
        return true;
    }

    @Override
    protected TitleParams getTitleParams() {
        return new TitleParams(createLeftBackImgAction(), "设置");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindListener() {

    }
}
