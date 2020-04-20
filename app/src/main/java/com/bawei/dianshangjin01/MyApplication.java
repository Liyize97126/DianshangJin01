package com.bawei.dianshangjin01;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * 应用总初始化类
 */
public class MyApplication extends Application {
    private static Context context;
    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //初始化第三方扫码工具
        ZXingLibrary.initDisplayOpinion(this);
    }
}
