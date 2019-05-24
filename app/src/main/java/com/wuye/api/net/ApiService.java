package com.wuye.api.net;

import com.wuye.api.bean.BaoXiuBean;
import com.wuye.api.bean.GongGaoBean;
import com.wuye.api.bean.ShuiDianBean;
import com.wuye.api.bean.TouSuBean;
import com.wuye.api.bean.UserBean;
import com.wuye.api.params.BaoXiuReqParams;
import com.wuye.api.params.LoginReqParams;
import com.wuye.api.response.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 2019/3/18 16:12
 * author: qcl
 * desc:
 * wechat:2501902696
 */
public interface ApiService {

    @POST("/wuye/login")
    Observable<BaseResponse<UserBean>> login(@Body LoginReqParams req);

    @POST("/wuye/register")
    Observable<BaseResponse<UserBean>> register(@Body LoginReqParams req);

    @FormUrlEncoded
    @POST("/wuye/changeAddress")
    Observable<BaseResponse<UserBean>> changeAddress(@Field("userPhone") String userPhone,
                                                     @Field("address") String address);


    @POST("/wuye/submit")
    Observable<BaseResponse<BaoXiuBean>> submit(@Body BaoXiuReqParams req);

    @POST("/tousu/add")
    Observable<BaseResponse<TouSuBean>> tousu(@Body BaoXiuReqParams req);

    @POST("/wuye/baoxiuChange")
    Observable<BaseResponse<BaoXiuBean>> change(@Body BaoXiuReqParams req);

    @FormUrlEncoded
    @POST("/wuye/baoxiuList")
    Observable<BaseResponse<List<BaoXiuBean>>> baoxiuList(@Field("baoxiuType") Integer baoxiuType);

    @GET("/gonggao/list")
    Observable<BaseResponse<List<GongGaoBean>>> gonggaoList();

    @FormUrlEncoded
    @POST("/shuidian/getUserShuiDianList")
    Observable<BaseResponse<List<ShuiDianBean>>> getUserShuiDianList(@Field("userPhone") String userPhone);

    @FormUrlEncoded
    @POST("/shuidian/changShuiDian")
    Observable<BaseResponse<ShuiDianBean>> changShuiDian(@Field("id") long id);

}
