package com.wuguangxin.utils;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
使用说明:
MMKV kv = MMKV.defaultMMKV();
kv.encode("bool", true);
boolean bValue = kv.decodeBool("bool");
kv.encode("int", Integer.MIN_VALUE);
int iValue = kv.decodeInt("int");
kv.encode("string", "Hello from mmkv");
String str = kv.decodeString("string");
*/

/**
 * MMKV快速存取工具类
 * Created by wuguangxin on 2020-07-01.
 */
public class MmkvUtils {

    private static boolean sEncodeKey = true; // 是否加密Key再保存
    private static boolean sEncodeValue = true; // 是否加密Value再保存

    /**
     * 初始化，在 Application.onCreate()中调用。
     *
     * @param content
     */
    public static void init(Context content) {
        MMKV.initialize(content);
    }

    private static MMKV build() {
        return MMKV.defaultMMKV();
    }

    // ########################### 基本类型操作区1 ###################################################

    public static boolean put(String key, String value) {
        return putString(key, value);
    }

    public static boolean put(String key, Integer value) {
        return putInt(key, value);
    }

    public static boolean put(String key, Long value) {
        return putLong(key, value);
    }

    public static boolean put(String key, Float value) {
        return putFloat(key, value);
    }

    public static boolean put(String key, Boolean value) {
        return putBoolean(key, value);
    }

    public static String get(String key, String defValue) {
        return getString(key, defValue);
    }

    public static int get(String key, Integer defValue) {
        return getInt(key, defValue);
    }

    public static long get(String key, Long defValue) {
        return getLong(key, defValue);
    }

    public static boolean get(String key, Boolean defValue) {
        return getBoolean(key, defValue);
    }

    public static float get(String key, Float defValue) {
        return getFloat(key, defValue);
    }

    // ########################### 存储指定的基本数据类型 ############################################

    public static boolean putString(String key, String value) {
        return build().encode(encodeKey(key), encodeValue(value));
    }


    public static String getString(String key, String defValue) {
        String value = build().decodeString(encodeKey(key), null);
        return TextUtils.isEmpty(value) ? defValue : decodeValue(value);
    }

    public static boolean putInt(String key, Integer value) {
        return build().encode(encodeKey(key), value);
    }

    public static int getInt(String key, Integer defValue) {
        return build().decodeInt(encodeKey(key), defValue);
    }

    public static boolean putLong(String key, Long value) {
        return build().encode(encodeKey(key), value);
    }

    public static long getLong(String key, Long defValue) {
        return build().decodeLong(encodeKey(key), defValue);
    }

    public static boolean putBoolean(String key, Boolean value) {
        return build().encode(encodeKey(key), value);
    }

    public static boolean getBoolean(String key, Boolean defValue) {
        return build().decodeBool(encodeKey(key), defValue);
    }

    public static boolean putFloat(String key, Float value) {
        return build().encode(encodeKey(key), value);
    }

    public static float getFloat(String key, Float defValue) {
        return build().decodeFloat(encodeKey(key), defValue);
    }

    // ########################### 存储对象数据类型 #################################################

    public static boolean putBigDecimal(String key, BigDecimal value) {
        if (value == null) value = BigDecimal.ZERO;
        return putString(key, value.toString());
    }

    public static boolean putJSONObject(String key, JSONObject value) {
        String strValue = null;
        if (value != null) {
            strValue = value.toString();
        }
        return putString(key, strValue);
    }

    public static boolean putJSONArray(String key, JSONArray value) {
        String strValue = null;
        if (value != null) {
            strValue = value.toString();
        }
        return putString(key, strValue);
    }

    public static BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    public static BigDecimal getBigDecimal(String key, BigDecimal defValue) {
        String valueStr = getString(key, null);
        if (!TextUtils.isEmpty(valueStr)) {
            try {
                return new BigDecimal(valueStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    public static JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    public static JSONObject getJSONObject(String key, JSONObject defValue) {
        String strValue = getString(key, null);
        if (!TextUtils.isEmpty(strValue)) {
            try {
                return new JSONObject(strValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    public static JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    public static JSONArray getJSONArray(String key, JSONArray defValue) {
        String strValue = getString(key, null);
        if (!TextUtils.isEmpty(strValue)) {
            try {
                return new JSONArray(strValue);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return defValue;
    }

    public static boolean putStringSet(String key, Set<String> value) {
        return build().encode(encodeKey(key), value);
    }

    public static Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return build().getStringSet(encodeKey(key), defValue);
    }

    /**
     * 用于保存集合
     *
     * @param key key
     * @param list 数据
     * @return 保存结果
     */
    public static <T> boolean putList(String key, List<T> list) {
        JsonArray array = new JsonArray();
        if (list != null && !list.isEmpty()) {
            T t = list.get(0);
            if (t != null) {
                String type = t.getClass().getSimpleName();
                try {
                    switch (type) {
                    case "Boolean":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Boolean) list.get(i));
                        }
                        break;
                    case "Long":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Long) list.get(i));
                        }
                        break;
                    case "Float":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Float) list.get(i));
                        }
                        break;
                    case "String":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((String) list.get(i));
                        }
                        break;
                    case "Integer":
                        for (int i = 0; i < list.size(); i++) {
                            array.add((Integer) list.get(i));
                        }
                        break;
                    default:
                        // TODO 这里万一是别的对象呢？待处理
                        Gson gson = new Gson();
                        for (int i = 0; i < list.size(); i++) {
                            JsonElement obj = gson.toJsonTree(list.get(i));
                            array.add(obj);
                        }
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return putString(key, array.toString());
    }

    /**
     * 获取保存的List
     *
     * @param key key
     * @return 对应的Lis集合
     */
    public static <T> List<T> getList(String key, Class<T> cls) {
        List<T> list = new ArrayList<>();
        String json = getString(key, null);
        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            try {
                JsonArray array = JsonParser.parseString(json).getAsJsonArray();
                for (JsonElement elem : array) {
                    list.add(gson.fromJson(elem, cls));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    //=============================================================================================

    /**
     * md5加密
     */
    private static String encodeKey(String text) {
        try {
            return sEncodeKey && !TextUtils.isEmpty(text) ? MD5.encode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * DES加密
     */
    private static String encodeValue(String text) {
        try {
            return sEncodeValue && !TextUtils.isEmpty(text) ? DES.encode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * DES解密
     */
    private static String decodeValue(String text) {
        try {
            return sEncodeValue && !TextUtils.isEmpty(text) ? DES.decode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}

