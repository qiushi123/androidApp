package com.wuye.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


/**
 * Description: 获取设备信息的工具类
 * Copyright  : Copyright (c) 2016
 * Company    : 年糕妈妈
 * Author     : 段宇鹏
 * Date       : 10/19/16
 */
public class DeviceUtils {

    public static String PLANTFORM_OS = "Android";
    public static String BRAND_MIUI = "Xiaomi"; // 小米机型
    public static String BRAND_HUAWEI = "honor";   // 华为机型
    public static String BRAND_SAMSUNG = "Samsung";   // 三星机型

    /**
     * 获取设备的操作系统
     *
     * @return
     */
    public static String getPlateForm() {
        return PLANTFORM_OS;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getPhoneBrand() {
        return Build.BRAND;
    }


    /**
     * 获取当前app版本号
     *
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        int versionCode = -1;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获得IP地址，分为两种情况，一是wifi下，二是移动网络下，得到的ip地址是不一样的
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //调用方法将int转换为地址字符串
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    private static final String PREFS_FILE = "device_id.xml";
    private static final String PREFS_DEVICE_ID = "device_id";

    /**
     * 获取设备唯一标识
     *
     * @param context
     * @return
     */
    public static UUID getDeviceUuid(Context context) {
        UUID uuid;
        final String id=  SpUtils.getUUId(context);

//        final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
//        final String id = prefs.getString(PREFS_DEVICE_ID, null);
        if (!TextUtils.isEmpty(id)&&!"null".equals(id)) {
            // Use the ids previously computed and stored in the prefs file
            uuid = UUID.fromString(id);

        } else {
            // 否则,获取 设备启动时候生成的数字
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                // 977xx 是厂商定制时候的一个bug
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    // 否则使用device_id
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            SpUtils.setUUId(context,uuid.toString());
//            prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();

        }
        return uuid;
    }


    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";  // 小米
    private static final String KEY_EMUI_VERSION_NAME = "ro.build.version.emui";    // 华为
    private static final String KEY_FLYME_DISPLAY = "ro.build.display.id";  // 版本号(魅族）

    /**
     * 判断是不是小米手机
     *
     * @return true是
     */
    public static boolean isMIUI() {
        String systemProperty = getSystemProperty(KEY_MIUI_VERSION_NAME, "");
        return !TextUtils.isEmpty(systemProperty);
    }

    /**
     * 获取小米手机的版本
     *
     * @return
     */
    public static String getMIUIVersion() {
        if (isMIUI()) {
            return getSystemProperty(KEY_MIUI_VERSION_NAME, "");
        } else {
            return "";
        }
    }

    /**
     * 判断手机版本是否大于6
     *
     * @return true大于
     */
    public static boolean isMIUI6Later() {
        String miuiVersion = getMIUIVersion();
        if (!TextUtils.isEmpty(miuiVersion)) {
            Integer versionNum = Integer.valueOf(miuiVersion.substring(1));
            if (versionNum != null) {
                return versionNum > 6;
            }
        }
        return false;
    }


    public static String getFlymeOSVersion() {
        return isFlymeOS() ? getFlymeOSFlag() : "";
    }

    public static boolean isFlymeOS() {
        return getFlymeOSFlag().toLowerCase().contains("flyme");
    }

    /**
     * 判断FlymeOS大于4的版本
     */
    public static boolean isFlymeOS4Later(){
        String flymeOSVersion = getFlymeOSVersion();
        int versionNum;
        if(!TextUtils.isEmpty(flymeOSVersion)){
            if (flymeOSVersion.toLowerCase().contains("os")) {
                versionNum = Integer.valueOf(flymeOSVersion.substring(9, 10));
            } else {
                versionNum = Integer.valueOf(flymeOSVersion.substring(6, 7));
            }
            return versionNum >=4 ;
        }
        return false;
    }

    /**
     * 获取魅族手机标志
     *
     * @return
     */
    public static String getFlymeOSFlag() {
        return getSystemProperty(KEY_FLYME_DISPLAY, "");
    }

    /**
     * 获取手机系统属性
     *
     * @param key   param1
     * @param value param2
     * @return getXXX()
     */
    public static String getSystemProperty(String key, String value) {
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", String.class, String.class);
            Object getSystemProperty = method.invoke(clazz, key, value);
            return (String) getSystemProperty;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getDeviceDesc(Context context) {
        try{
            String s= Build.BRAND+"/"+android.os.Build.MODEL+"/系统版本:"+Build.VERSION.RELEASE+"/APP版本:"+getAppVersionName(context);
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 判断微信
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPhoneSystem(Context context) {
        try{
            String s= Build.VERSION.RELEASE;
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

    public static String getPhoneModel(Context context) {
        try{
            String s= Build.BRAND+" "+android.os.Build.MODEL;
            return s;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

}
