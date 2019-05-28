package com.tangzhentao.libbase.utils;

import android.content.Context;
import android.widget.Toast;

import com.tangzhentao.libbase.R;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/28
 */
public class ToastUtils {
    public static void ShowShort(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    };
}
