package com.wuye.api.net;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 2019/3/18 16:08
 * author: qcl
 * desc:服务的工厂类
 */
public class ServiceFactory {
    private static String BASE_URL = "http://20.86.11.248:8080";
    public static int NET_SUCCESS_CODE = 100;//请求成功返回的code
    private static ServiceFactory serviceFactory;

    private ApiService apiService;//登录模块

    public static ServiceFactory getServiceFactory() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }


    public ApiService getApiService(Context context) {
        if (apiService == null) {
            synchronized (ServiceFactory.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(OkHttpClientFactory.getDefaultOkClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    apiService = retrofit.create(ApiService.class);
                }
            }
        }
        return apiService;
    }
}
