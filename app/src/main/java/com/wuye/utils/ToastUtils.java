package com.wuye.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.wuye.BaseApplication;

/**
 * Description: 吐司工具类
 */
public class ToastUtils {

    public static void toast(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        Toast.makeText(BaseApplication.appContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, String msg) {
        if (context == null || TextUtils.isEmpty(msg))
            return;
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String msg) {
        if (context == null || TextUtils.isEmpty(msg))
            return;
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
