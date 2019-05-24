package com.wuye.api.bean;

/**
 * 2019/3/19 20:40
 * author: qcl
 * desc:
 * wechat:2501902696
 */
public class ShuiDianBean {
    public long id;//主键
    public long userId;//用户id
    public Integer shuidian;//水电费
    public Integer type;//0待支付，1已支付
    public String createTime;//配送时间
}
