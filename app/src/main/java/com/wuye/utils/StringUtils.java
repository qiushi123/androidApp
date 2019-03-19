package com.wuye.utils;

/**
 * Description: 字符串工具类
 * Copyright  : Copyright (c) 2017
 * Company    : 年糕妈妈
 * Author     : 段宇鹏
 * Date       : 2017/7/31
 */
public class StringUtils {

    /**
     * 如果字符串为null，返回空字符串
     */
    public static String notNullToString(String str) {
        return str == null ? "" : str;
    }

    /**
     * 判断字符串
     *
     * @param str
     * @return
     */
    @Deprecated
    public static boolean isSpace(String str) {
        return str == null || str.trim().length() == 0;
    }
    

}
