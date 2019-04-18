package com.wuye.api.bean;

/**
 * 2019/3/19 20:40
 * author: qcl
 * desc:
 * wechat:2501902696
 */
public class BaoXiuBean {
    public Long baoxiuId;
    public String name;
    public String phone;
    public String address;
    public String content;
    public Integer baoxiuType;//0待维修，1已接单，2已处理待支付，3已支付待评价，4已完成
    public Integer price;//维修价格
    public String comment;//评价信息
}
