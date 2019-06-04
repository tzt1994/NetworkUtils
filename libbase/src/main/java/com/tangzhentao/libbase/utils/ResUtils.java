package com.tangzhentao.libbase.utils;

import android.graphics.Color;
import android.support.v4.graphics.ColorUtils;

/**
 * Description:获取资源方法
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public class ResUtils {

    /**
     * 获取颜色值
     * @param resid 资源id
     * @return  A single color value in the form 0xAARRGGBB
     */
    public static int getColor(int resid) {
        return AppUtils.getResources().getColor(resid);
    }

    /**
     * 是否是亮色
     * @param color 16进制颜色值
     * @return true亮色 false暗色
     */
    public static boolean isLightColor(int color) {
//        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
//        return darkness < 0.5;
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }
}
