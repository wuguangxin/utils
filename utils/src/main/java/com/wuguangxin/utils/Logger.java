package com.wuguangxin.utils;

import android.content.Context;
import android.util.Log;

/**
 * 日志打印工具类
 * Created by wuguangxin on 14/10/11
 */
public class Logger {
    private final static String TAG = Logger.class.getSimpleName();
    private static String TAG_PREFIX = "";
    private static boolean mDebug = false;

    /**
     * 是否是DEBUG
     *
     * @return 是否是DEBUG
     */
    public static boolean isDebug() {
        return mDebug;
    }

    /**
     * 设置Logger的debug模式，true则打印日志，false则不打印。
     *
     * @param debug debug
     */
    public static void setDebug(boolean debug) {
        mDebug = debug;
    }

    /**
     * 设置Tag的前缀，可以给整个项目加个前缀，便于过滤项目以外的日志。
     *
     * @param tagPrefix 前缀
     */
    public static void setTagPrefix(String tagPrefix) {
        TAG_PREFIX = tagPrefix;
    }

    // *****************************************************************************


    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    // *****************************************************************************
    public static void i(Context context, String msg) {
        i(context.getClass(), msg);
    }

    public static void d(Context context, String msg) {
        d(context.getClass(), msg);
    }

    public static void e(Context context, String msg) {
        e(context.getClass(), msg);
    }

    public static void w(Context context, String msg) {
        w(context.getClass(), msg);
    }

    public static void v(Context context, String msg) {
        v(context.getClass(), msg);
    }

    // *****************************************************************************
    public static void i(Class clazz, String msg) {
        i(clazz.getSimpleName(), msg);
    }

    public static void d(Class clazz, String msg) {
        d(clazz.getSimpleName(), msg);
    }

    public static void e(Class clazz, String msg) {
        e(clazz.getSimpleName(), msg);
    }

    public static void w(Class clazz, String msg) {
        w(clazz.getSimpleName(), msg);
    }

    public static void v(Class clazz, String msg) {
        v(clazz.getSimpleName(), msg);
    }

    // *****************************************************************************
    public static void i(String tag, String msg) {
        printLog(tag, msg, Log.INFO);
    }

    public static void d(String tag, String msg) {
        printLog(tag, msg, Log.DEBUG);
    }

    public static void e(String tag, String msg) {
        printLog(tag, msg, Log.ERROR);
    }

    public static void w(String tag, String msg) {
        printLog(tag, msg, Log.WARN);
    }

    public static void v(String tag, String msg) {
        printLog(tag, msg, Log.VERBOSE);
    }

    private static void printLog(String tag, String msg, int type) {
        if (!mDebug) {
            return;
        }
        if (tag == null) tag = TAG;
        tag = String.format("%s%s", TAG_PREFIX, tag);
        switch (type) {
        case Log.VERBOSE:
            Log.v(tag, msg);
            break;
        case Log.DEBUG:
            Log.d(tag, msg);
            break;
        case Log.INFO:
            Log.i(tag, msg);
            break;
        case Log.WARN:
            Log.w(tag, msg);
            break;
        case Log.ERROR:
            Log.e(tag, msg);
            break;
        }
    }
}
