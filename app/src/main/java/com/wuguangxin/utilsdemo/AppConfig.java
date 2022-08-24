package com.wuguangxin.utilsdemo;

import com.wuguangxin.utils.mmkv.EditorProxy;
import com.wuguangxin.utils.mmkv.Config;

/**
 * MMKV存储。适用于存储APP的配置数据
 * Created by wuguangxin on 16/1/28
 */
public class AppConfig extends Config {

    private AppConfig() {}
    private static AppConfig instance;
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public EditorProxy setLastUserName(String userName){
        return putString("lastUserName", userName);
    }

    public String getLastUserName() {
        return getString("lastUserName");
    }
}
