package com.wuye.api.net;

import android.content.Context;

import com.wuye.api.bean.BaoXiuBean;
import com.wuye.api.bean.GongGaoBean;
import com.wuye.api.bean.ShuiDianBean;
import com.wuye.api.bean.TouSuBean;
import com.wuye.api.bean.UserBean;
import com.wuye.api.params.BaoXiuReqParams;
import com.wuye.api.params.LoginReqParams;

import java.util.List;

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

    //修改用户信息
    public Observable<UserBean> changeUserInfo(Context context, String userPhone, String address) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .changeAddress(userPhone, address)
                .compose(RxHelper.<UserBean>handleResult());
    }


    //提交报修信息
    public Observable<BaoXiuBean> submit(Context context, BaoXiuReqParams params) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .submit(params)
                .compose(RxHelper.<BaoXiuBean>handleResult());
    }

    //提交投诉信息
    public Observable<TouSuBean> tousu(Context context, BaoXiuReqParams params) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .tousu(params)
                .compose(RxHelper.<TouSuBean>handleResult());
    }

    //修改报修信息
    public Observable<BaoXiuBean> change(Context context, BaoXiuReqParams params) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .change(params)
                .compose(RxHelper.<BaoXiuBean>handleResult());
    }

    //查询报修列表
    public Observable<List<BaoXiuBean>> baoxiuList(Context context, Integer baoxiuType) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .baoxiuList(baoxiuType)
                .compose(RxHelper.<List<BaoXiuBean>>handleResult());
    }

    //查询公告列表
    public Observable<List<GongGaoBean>> gonggaoList(Context context) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .gonggaoList()
                .compose(RxHelper.<List<GongGaoBean>>handleResult());
    }

    //查询水电费列表
    public Observable<List<ShuiDianBean>> shuidianList(Context context, String userPhone) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .getUserShuiDianList(userPhone)
                .compose(RxHelper.<List<ShuiDianBean>>handleResult());
    }

    //交水电费
    public Observable<ShuiDianBean> shuidianChange(Context context, long id) {
        return ServiceFactory.getServiceFactory()
                .getApiService(context)
                .changShuiDian(id)
                .compose(RxHelper.<ShuiDianBean>handleResult());
    }
}
