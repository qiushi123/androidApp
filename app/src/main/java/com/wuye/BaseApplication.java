package com.wuye;

import android.app.Application;
import android.content.Context;

/**
 * Description: Application
 */
public class BaseApplication extends Application {


    public static Context appContext;


    public static String channel = "";

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this.getApplicationContext();
    }


}
