package com.wuye.api.net;

import android.content.Context;

import com.wuye.api.params.LoginReqParams;
import com.wuye.api.bean.UserBean;

import io.reactivex.Observable;

/**
 * 2019/3/18 16:31
 * author: qcl
 * desc:
 * wechat:2501902696
 */
public class BaseModel {

    public Observable<UserBean> login(Context context, LoginReqParams params) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .login(params)
                .compose(RxHelper.<UserBean>handleResult());
    }

    public Observable<UserBean> register(Context context, LoginReqParams params) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .register(params)
                .compose(RxHelper.<UserBean>handleResult());
    }
}
