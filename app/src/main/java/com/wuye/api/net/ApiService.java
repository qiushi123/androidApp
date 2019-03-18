package com.wuye.api.net;

import com.wuye.api.params.LoginReqParams;
import com.wuye.api.response.BaseResponse;
import com.wuye.api.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 2019/3/18 16:12
 * author: qcl
 * desc:
 * wechat:2501902696
 */
public interface ApiService {

    @POST("/wuye/login")
    Observable<BaseResponse<LoginResponse>> login(@Body LoginReqParams req);

}
