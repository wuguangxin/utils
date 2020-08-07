package com.wuguangxin.utils.demo;

import android.app.Application;
import android.content.Context;

import com.wuguangxin.utils.DES;
import com.wuguangxin.utils.Logger;
import com.wuguangxin.utils.MD5;
import com.wuguangxin.utils.MmkvUtils;

import java.lang.ref.WeakReference;

public class App extends Application {
    private static WeakReference<App> mApp;
    public Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApp = new WeakReference<>(this);
        DES.setKey(Constants.DES_KEY);
        MD5.setKey(Constants.MD5_KEY);
        MmkvUtils.init(this, Constants.MMKV_KEY);  // 初始化缓存MMKV
        Logger.setDebug(Constants.LOG_DEBUG);   // 日志debug模式
        Logger.setTagPrefix("wgx/");            // 设置日志Tag前缀，便于过滤

//         StrictModeUtils.start(true); // 开始使用严格策略模式来检查程序性能
    }

    public Context getContext() {
        return mContext;
    }

    public static App getApplication() {
        return mApp.get();
    }

    /**
     * 退出程序
     */
    public static void exitApp() {
        Logger.i(App.class.getSimpleName(), "退出程序");
//        ActivityTask.getInstance().clearTask();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
