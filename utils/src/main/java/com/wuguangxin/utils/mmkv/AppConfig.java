package com.wuguangxin.utils.mmkv;

import android.text.TextUtils;

import com.tencent.mmkv.MMKV;
import com.wuguangxin.utils.mmkv.core.Config;
import com.wuguangxin.utils.mmkv.core.Editor;

/**
 * MMKV存储。适用于存储APP的配置数据，和用户无关。
 * Created by wuguangxin on 16/1/28
 */
public class AppConfig extends Config {

    private static Editor edit() {
        if (editor == null) {
            editor = new Editor(new Editor.Creator() {
                @Override
                public MMKV create() {
                    if (!TextUtils.isEmpty(cryptKey)) {
                        return MMKV.defaultMMKV(MMKV.SINGLE_PROCESS_MODE, cryptKey);
                    }
                    return MMKV.defaultMMKV();
                }
            });
        }
        return editor;
    }

//    // ------------ 示例代码 -----------------
//    /** 设置最后一次登录的用户名 */
//    public static boolean setLastUserName(String userName){
//        return edit().putString("lastUserName", userName);
//    }
//
//    /** 获取最后一次登录的用户名 */
//    public static String getLastUserName(){
//        return edit().getString("lastUserName", null);
//    }

}
