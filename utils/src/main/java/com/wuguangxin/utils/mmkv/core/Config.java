package com.wuguangxin.utils.mmkv.core;

/**
 * App配置信息-MMKV存储
 * Created by wuguangxin on 16/1/28
 */
public abstract class Config {

    protected static String userId; // 当前用户ID
    protected static String cryptKey; // 加密key
    protected static Editor editor;

    /**
     * 设置AES加密KEY
     * @param cryptKey AES加密KEY
     */
    public static void setCryptKey(String cryptKey) {
        Config.cryptKey = cryptKey;
    }
}
