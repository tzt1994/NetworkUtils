package com.tangzhentao.libbase.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Description:
 *
 * @author tangzhentao
 * @date 2019/5/23
 */
public class AppUtils {

    private static Application bsApplicaiton;

    public static void  init(Application application) { bsApplicaiton = application; }

    public static Context getContex() { return bsApplicaiton; }

    public static Application getApplication() { return bsApplicaiton; }

    public static Resources getResources() { return bsApplicaiton.getResources(); }
}
