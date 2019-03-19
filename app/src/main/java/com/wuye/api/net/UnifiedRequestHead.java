package com.wuye.api.net;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.wuye.BaseApplication;
import com.wuye.utils.DeviceUtils;
import com.wuye.utils.NetworkUtils;
import com.wuye.utils.SpUtils;
import com.wuye.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description: 添加统一请求头拦截器
 */
public class UnifiedRequestHead implements Interceptor {

    private Context context;

    public UnifiedRequestHead() {
        this.context = BaseApplication.appContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        String appVersionName = DeviceUtils.getAppVersionName(context);
        String ipAddress = DeviceUtils.getIPAddress(context);
        int sdkInt = Build.VERSION.SDK_INT;
        String networkType = NetworkUtils.getNetworkType();

        String packageName = context.getPackageName();
        String appName = "";
        if ("com.wuye".equals(packageName)) {
            appName = "androidApp";
        }
        Request newRequest = originRequest.newBuilder()
                .addHeader("x-access-token", SpUtils.getAccessToken(context))
                .addHeader("x-platform", "Android")
                .addHeader("x-system-version", sdkInt + "")
                .addHeader("x-client-version", StringUtils.notNullToString(appVersionName))
                .addHeader("x-client-token", DeviceUtils.getDeviceUuid(context).toString())
                .addHeader("x-method-version", "1.0")
                .addHeader("x-network-type", StringUtils.notNullToString(networkType))
                .addHeader("x-user-id", String.valueOf(SpUtils.getUserId(context)))
                .addHeader("x-real-ip", StringUtils.notNullToString(ipAddress))
                .addHeader("x-app-name", appName)
                .build();

        if (TextUtils.isEmpty(SpUtils.getAccessToken(context))) {
            newRequest = newRequest.newBuilder()
                    .removeHeader("x-access-token")
                    .build();
        }
        if (SpUtils.getUserId(context) <= 0) {
            newRequest = newRequest.newBuilder()
                    .removeHeader("x-user-id")
                    .build();
        }
        return chain.proceed(newRequest);
    }
}
