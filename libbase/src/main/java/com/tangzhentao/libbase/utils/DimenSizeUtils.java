package com.tangzhentao.libbase.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/27
 */
public class DimenSizeUtils {

    /**
     * 获取屏幕宽度
     * @return int
     */
    public static int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) AppUtils.getContex().getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return int
     */
    public static int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) AppUtils.getContex().getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        return displayMetrics.heightPixels;
    }

    /**
     * dp转为px
     *
     */
    public static int dpTopx(int dp) {
        return (int) (AppUtils.getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
