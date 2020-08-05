package com.wuguangxin.utils;

import android.util.Log;

/**
 * 点击View的工具类
 * Created by wuguangxin on 2019/11/1.
 */
public class ClickUtils {
    /**
     * 最小间隔还时间（毫秒）
     */
    private static final int MIN_CLICK_DELAY_TIME = 700; // 过快点击间隔限制时间（毫秒）

    /**
     * 上一次点击的时间
     */
    private static long lastClickTime;

    /**
     * 判断是否过快点击控件。
     * @return
     */
    public static boolean isFastClick() {
        long curClickTime = System.currentTimeMillis();
        boolean result = false;
        if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME) {
            Log.d("ClickUtils", "操作太快了...");
            result = true;
        }
        lastClickTime = curClickTime;
        return result;
    }
}
