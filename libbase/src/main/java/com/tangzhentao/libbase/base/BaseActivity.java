package com.tangzhentao.libbase.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


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
    //内容布局
    private FrameLayout mContentLayout;

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //全屏+状态栏透明化
        if (getStatusBarTransparent()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

        mRootLayout = new LinearLayout(this);
        //避免控件会顶到状态栏上
        mRootLayout.setFitsSystemWindows(true);
        mRootLayout.setOrientation(LinearLayout.VERTICAL);

        //设置头部布局（状态栏背景和标题栏）
        if (getTitleParams() != null) {
            TitleParams titleParams = getTitleParams();

            //设置状态栏
            if (getStatusBarTransparent()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(titleParams.BgColor);
                    setStatusBarTextColor(titleParams.statusBarTextColor);
                }
            }

            //设置标题栏
            if (mTitleLayout == null) {
                mTitleLayout = new BaseTitle(this);
                mTitleLayout.setBackgroundColor(titleParams.BgColor);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, titleParams.Height);
                mRootLayout.addView(mTitleLayout, params);

                updateTitleBar();
            }
        }

        mContentLayout = new FrameLayout(this);
        mRootLayout.addView(mContentLayout, new LinearLayout.LayoutParams(-1,-1));
        super.setContentView(mRootLayout);
    }

    @Override
    public final void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public final void setContentView(View view) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        setContentView(view, params);
    }

    @Override
    public final void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.addView(view, params);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 更新标题栏
     */
    private void updateTitleBar() {
        TitleParams titleParams = getTitleParams();
        if (titleParams == null) {
            return;
        }

        mTitleLayout.setHeight(titleParams.Height);
        mTitleLayout.removeAllViews();
        mTitleLayout.setCenterTitle(titleParams.title);
        mTitleLayout.addLeftBaseAction(titleParams.leftAction);
        mTitleLayout.addRightBaseAction(titleParams.rightActions);
    }

    /**
     * 获取是否显示状态透明化
     * @return boolean
     */
    protected abstract boolean getStatusBarTransparent();

    /**
     * 是否展示标题栏
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
     * 设置状态栏文字颜色
     * @param color
     */
    private void setStatusBarTextColor(int color) {

    }

    /**
     * 获取状态高度
     * @return int
     */
    private int getStatusBarHeight() {
        int statusBarHeight = -1;
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resId);
        }

        return statusBarHeight;
    }
}
