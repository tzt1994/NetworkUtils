package com.tangzhentao.networkutils;

import android.app.Application;

import com.tangzhentao.libbase.utils.AppUtils;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public class NwApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtils.init(this);
    }
}
