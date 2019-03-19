package com.wuye.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wuye.api.bean.UserBean;

import static android.content.Context.MODE_PRIVATE;

/**
 * 2019/3/18 20:16
 * author: qcl
 * desc:保存登录信息的sp工具类
 * wechat:2501902696
 */
public class SpUtils {
    private final static String SP_ROOT_NAME = "wuye";

    public final static String SP_USER_INFO = "sp_user_info";
    public final static String SP_USER_TOKEN = "sp_user_token";
    public final static String SP_USER_ID = "sp_user_id";
    public final static String SP_UUID = "sp_uuid";

    /*
     * 存储和获取userbean
     * */
    public static void saveUserInfo(Context context, UserBean bean) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SP_USER_INFO, new Gson().toJson(bean));
            editor.apply();
        }
    }

    public static UserBean getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(SP_USER_INFO, null);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, UserBean.class);
        }
        return null;
    }

    /*
     * 存储和获取user-token
     * */
    public static void setAccessToken(Context context, String token) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SP_USER_TOKEN, token);
            editor.apply();
        }
    }

    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SP_USER_TOKEN, "");
    }

    /*
     * 存储和获取userid
     * */
    public static void setUserId(Context context, long id) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(SP_USER_ID, id);
            editor.apply();
        }
    }

    public static long getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
        return sharedPreferences.getLong(SP_USER_ID, -1);
    }
    /*
     * 存储和获取uuid
     * */
    public static void setUUId(Context context, String uuid) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SP_UUID, uuid);
            editor.apply();
        }
    }

    public static String getUUId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_ROOT_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(SP_UUID, "");
    }
}
