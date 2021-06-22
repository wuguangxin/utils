package com.wuguangxin.utils.mmkv;

import android.text.TextUtils;

import com.tencent.mmkv.MMKV;
import com.wuguangxin.utils.mmkv.core.Config;
import com.wuguangxin.utils.mmkv.core.Editor;

/**
 * 用户配置信息，适合有注册功能的多用户存储使用。（MMKV存储）
 * Created by wuguangxin on 16/1/28
 */
public class UserConfig extends Config {

    public static Editor edit() {
        if (editor == null) {
            editor = new Editor(new Editor.Creator() {
                @Override
                public MMKV create() {
                    // 设置了用户ID，则使用用户专用的存储实例对象，否则使用默认的
                    if (!TextUtils.isEmpty(userId)) {
                        // return MMKV.mmkvWithID(userId);
                        // userId：多用户时用户ID
                        // mode：单进程 SINGLE_PROCESS_MODE(默认)，多进程 MULTI_PROCESS_MODE
                        // cryptKey：加密key
                        if (TextUtils.isEmpty(cryptKey)) {
                            return MMKV.mmkvWithID(userId, MMKV.SINGLE_PROCESS_MODE);
                        }
                        return MMKV.mmkvWithID(userId, MMKV.SINGLE_PROCESS_MODE, cryptKey);
                    }
                    if (!TextUtils.isEmpty(cryptKey)) {
                        return MMKV.defaultMMKV(MMKV.SINGLE_PROCESS_MODE, cryptKey);
                    }
                    return MMKV.defaultMMKV();
                }
            });
        }
        return editor;
    }

    /**
     * 切换用户
     * @param userId 用户唯一标识
     */
    public static void switchUser(String userId) {
        if (!TextUtils.equals(UserConfig.userId, userId)) {
            UserConfig.editor = null;
        }
        UserConfig.userId = userId;
    }

//    // ------------ 示例代码 -----------------
//    /** 设置用户名 */
//    public static boolean setUserName(String userName){
//        return edit().putString("userName", userName);
//    }
//
//    /** 获取用户名 */
//    public static String getUserName(){
//        return edit().getString("userName");
//    }

}
