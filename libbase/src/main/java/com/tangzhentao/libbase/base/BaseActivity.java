package com.tangzhentao.libbase.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tangzhentao.libbase.R;
import com.tangzhentao.libbase.utils.OsUtils;
import com.tangzhentao.libbase.utils.ResUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public abstract class BaseActivity extends AppCompatActivity {

    //整个页面布局
    private LinearLayout mRootLayout;
    //标题栏布局
    private BaseTitle mTitleLayout;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        mRootLayout = new LinearLayout(this);
        //避免控件会顶到状态栏上
        mRootLayout.setFitsSystemWindows(true);
        mRootLayout.setOrientation(LinearLayout.VERTICAL);
        mRootLayout.setBackgroundColor(ResUtils.getColor(R.color.white));

        //设置头部布局（状态栏背景和标题栏）
        if (getTitleParams() != null) {
            TitleParams titleParams = getTitleParams();

            if (getStatusBarColorSameAsTitleBarColor()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //设置状态栏颜色
                    getWindow().setStatusBarColor(titleParams.bgColor);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //设置状态栏文字图标 黑色还是白色
                        getWindow().getDecorView().setSystemUiVisibility(ResUtils.isLightColor(titleParams.bgColor) ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            }

            //设置标题栏
            if (mTitleLayout == null) {
                mTitleLayout = new BaseTitle(this);
                mTitleLayout.setBackgroundColor(titleParams.bgColor);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, titleParams.titleHeight);
                mRootLayout.addView(mTitleLayout, params);

                updateTitleBar();
            }
        }

        if (getLayoutId() > 0) {
            View content =View.inflate(this, getLayoutId(), null);
            if (content != null) {
                mRootLayout.addView(content, new LinearLayout.LayoutParams(-1,-1));
            }
        }

        setContentView(mRootLayout);

        initView();
        initData();
        bindListener();

        Log.i(this.getClass().getName(), "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getName(), "onDestroy");
    }

    /**
     * 更新标题栏
     */
    private void updateTitleBar() {
        TitleParams titleParams = getTitleParams();
        if (titleParams == null) {
            return;
        }

        mTitleLayout.setHeight(titleParams.titleHeight);
        mTitleLayout.removeAllViews();
        mTitleLayout.setCenterTitle(titleParams.title);
        mTitleLayout.addLeftBaseAction(titleParams.leftAction);
        mTitleLayout.addRightBaseAction(titleParams.rightActions);
    }

    /**
     * 获取布局资源id
     * @return int 值的布局资源id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置点击
     */
    protected abstract void bindListener();

    /**
     * 设置是否让状态颜色和标题栏一致
     * @return 默认一致，可以重写返回false展示不一致
     */
    protected boolean getStatusBarColorSameAsTitleBarColor() {return true;}

    /**
     * 是否展示标题栏
     * 返回null不展示标题栏
     */
    protected abstract TitleParams getTitleParams();

    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        //真正的沉浸式，隐藏状态栏导航栏，顶部下拉或底部上拉会显示状态栏和导航栏
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }

    /**
     * 通用的左边返回按钮图片构建
     * @return BaseAction
     */
    protected BaseTitle.BaseAction createLeftBackImgAction() {
        return BaseTitle.createLeftAction(this);
    }

    /**
     * 通用的右边文字输入
     * @return BaseAction
     */
    protected BaseTitle.BaseAction createRightTextAction(String rightText, Intent intent) {
        return BaseTitle.createRightTextAction(this, rightText, intent);
    }

    /**
     * 设置状态栏文字颜色
     * @param color 0x...x
     */
    private void setStatusBarTextColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (OsUtils.isFlyme()) {
                setFlyme(color);
            }else if (OsUtils.isMIUI()) {
                setMIUI(color);
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(ResUtils.isLightColor(color) ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }


    private void setFlyme(int color) {
        Window window = getWindow();
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (ResUtils.isLightColor(color)) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setMIUI(int color) {
        Window window = getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (ResUtils.isLightColor(color)) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (ResUtils.isLightColor(color)) {
                        getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
