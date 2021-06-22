package com.wuguangxin.utils.mmkv.core;

import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Editor {

    /**
     * 创建MMKV实例
     */
    public interface Creator {
        default MMKV create() {
            return MMKV.defaultMMKV();
        }
    }

    private Creator creator;

    public Creator getCreator() {
        return creator;
    }

    private Editor() { }

    public Editor(Creator creator) {
        if (creator == null)
            throw new IllegalArgumentException("creator not be null");
        this.creator = creator;
    }

    /**
     * 重置加密（AES）秘钥
     *
     * @param encryptKey 秘钥
     * @return
     */
    public boolean reKey(String encryptKey) {
        return getCreator().create().reKey(encryptKey);
    }

    /**
     * 清除加密，并使数据明文。
     *
     * @return
     */
    public boolean clearKey() {
        return getCreator().create().reKey(null); // 设置null即可
    }

    /** 删除指定key对应的value */
    public void removeValueByKey(String key) {
        getCreator().create().removeValueForKey(key);
    }

    /** 删除指定多个key对应的value */
    public void removeValuesForKeys(String... keys) {
        getCreator().create().removeValuesForKeys(keys);
    }

    /** 是否包含指定的key */
    public boolean containsKey(String key) {
        return getCreator().create().containsKey(key);
    }

    // ########################### 基本类型操作区1 ###################################################

    // int
    public boolean put(String key, int value) {
        return putInt(key, value);
    }

    // long
    public boolean put(String key, long value) {
        return putLong(key, value);
    }

    // float
    public boolean put(String key, float value) {
        return putFloat(key, value);
    }

    // byte
    public boolean put(String key, byte[] value) {
        return putBytes(key, value);
    }

    // double
    public boolean put(String key, double value) {
        return putDouble(key, value);
    }

    // boolean
    public boolean put(String key, boolean value) {
        return putBoolean(key, value);
    }

    // String
    public boolean put(String key, String value) {
        return putString(key, value);
    }

    // Set<String>
    public boolean put(String key, Set<String> value) {
        return putSet(key, value);
    }

    // String[]
    public boolean put(String key, String[] value) {
        return putStringArray(key, value);
    }

    // Parcelable
    public boolean put(String key, Parcelable value) {
        return putParcelable(key, value);
    }

    // Serializable
    public boolean put(String key, Serializable value) {
        return putBean(key, value);
    }


    public int get(String key, int defValue) {
        return getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return getLong(key, defValue);
    }

    public float get(String key, float defValue) {
        return getFloat(key, defValue);
    }

    public byte[] get(String key, byte[] defValue) {
        return getBytes(key, defValue);
    }

    public double get(String key, double defValue) {
        return getDouble(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return getBoolean(key, defValue);
    }

    public String get(String key, String defValue) {
        return getString(key, defValue);
    }

    public Set<String> get(String key, Set<String> defValue) {
        return getSet(key, defValue);
    }

    public String[] get(String key, String[] defValue) {
        return getStringArray(key, defValue);
    }

    public <T extends Parcelable> T get(String key, Class<T> defValue) {
        return getParcelable(key, defValue);
    }

    // ########################### 存储指定的基本数据类型 ############################################

    // ----------String

    public boolean putString(String key, String value) {
        return getCreator().create().encode(key, value);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defValue) {
        return getCreator().create().decodeString(key, defValue);
    }

    // ----------int

    public boolean putInt(String key, int value) {
        return getCreator().create().encode(key, value);
    }

    public int getInt(String key) {
        return getCreator().create().decodeInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return getCreator().create().decodeInt(key, defValue);
    }

    // ----------long

    public boolean putLong(String key, long value) {
        return getCreator().create().encode(key, value);
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long defValue) {
        return getCreator().create().decodeLong(key, defValue);
    }

    // ----------boolean

    public boolean putBoolean(String key, boolean value) {
        return getCreator().create().encode(key, value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getCreator().create().decodeBool(key, defValue);
    }

    // ----------float

    public boolean putFloat(String key, float value) {
        return getCreator().create().encode(key, value);
    }

    public float getFloat(String key) {
        return getFloat(key, 0.0F);
    }

    public float getFloat(String key, float defValue) {
        return getCreator().create().decodeFloat(key, defValue);
    }

    // ----------double

    public boolean putDouble(String key, double value) {
        return getCreator().create().encode(key, value);
    }

    public double getDouble(String key) {
        return getDouble(key, 0.0D);
    }

    public double getDouble(String key, double defValue) {
        return getCreator().create().decodeDouble(key, defValue);
    }

    // ----------byte

    public boolean putBytes(String key, byte[] value) {
        return getCreator().create().encode(key, value);
    }

    public byte[] getBytes(String key) {
        return getBytes(key, (byte[]) null);
    }

    public byte[] getBytes(String key, byte[] defValue) {
        return getCreator().create().decodeBytes(key, defValue);
    }

    // ----------Set<String>

    public boolean putSet(String key, Set<String> value) {
        return getCreator().create().encode(key, value);
    }

    public Set<String> getSet(String key) {
        return getSet(key, (Set<String>) null);
    }

    public Set<String> getSet(String key, Set<String> defValue) {
        return getCreator().create().decodeStringSet(key, defValue);
    }

    // ---------- Set<String>

    public boolean putStringSet(String key, Set<String> value) {
        return getCreator().create().encode(key, value);
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return getCreator().create().getStringSet(key, defValue);
    }

    // ---------- String[]

    public boolean putStringArray(String key, String[] value) {
        Set<String> set = new HashSet<>(); // 转为set来存储
        Collections.addAll(set, value);
        return getCreator().create().encode(key, set);
    }

    public String[] getStringArray(String key) {
        return getStringArray(key, null);
    }

    public String[] getStringArray(String key, String[] defValue) {
        Set<String> set = getSet(key);
        if (set != null) {
            return (String[]) set.toArray(new String[0]);
        }
        return defValue;
    }

    // ########################### 存储对象数据类型 #################################################

    // ------- Parcelable

    public boolean putParcelable(String key, Parcelable value) {
        return getCreator().create().encode(key, value);
    }

    public <T extends Parcelable> T getParcelable(String key) {
        return getCreator().create().decodeParcelable(key, null);
    }

    public <T extends Parcelable> T getParcelable(String key, Class<T> defValue) {
        return getCreator().create().decodeParcelable(key, defValue);
    }

    // ------- Serializable

    public <T extends Serializable> boolean putBean(String key, T value) {
        return putString(key, new Gson().toJson(value));
    }

    public <T> T getBean(String key, Type type) {
        String json = getString(key);
        return TextUtils.isEmpty(json) ? null : new Gson().fromJson(json, type);
    }

    // ------- BigDecimal

    public boolean putBigDecimal(String key, BigDecimal value) {
        return putString(key, value == null ? BigDecimal.ZERO.toString() : value.toString());
    }

    public BigDecimal getBigDecimal(String key) {
        return getBigDecimal(key, null);
    }

    public BigDecimal getBigDecimal(String key, BigDecimal defValue) {
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

    // ------- JSONObject

    public boolean putJSONObject(String key, JSONObject value) {
        return putString(key, value == null ? null : value.toString());
    }

    public JSONObject getJSONObject(String key) {
        return getJSONObject(key, null);
    }

    public JSONObject getJSONObject(String key, JSONObject defValue) {
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

    // ------- JSONArray

    public boolean putJSONArray(String key, JSONArray value) {
        return putString(key, value == null ? null : value.toString());
    }

    public JSONArray getJSONArray(String key) {
        return getJSONArray(key, null);
    }

    public JSONArray getJSONArray(String key, JSONArray defValue) {
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

    /**
     * 用于存储List集合
     *
     * @param key 存储的key
     * @param list 数据List
     * @return 保存结果
     */
    public <T> boolean putList(String key, List<T> list) {
        return putString(key, new Gson().toJson(list));
    }

    /**
     * 获取List集合
     *
     * @param key key
     * @param type 数据类型Type
     * @return 对应的Lis集合
     */
    public <T> List<T> getList(String key, Type type) {
        String json = getString(key);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, type);
        }
        return null;
    }

    /**
     * 保存Map集合
     *
     * @param key key
     * @param map 数据Map
     * @return 保存结果
     */
    public <K, V> boolean putMap(String key, Map<K, V> map) {
        return putString(key, new Gson().toJson(map));
    }

    /**
     * 用于获取List集合
     *
     * @param key key
     * @param type 数据类型Type
     * @return 对应的Lis集合
     */
    public <K, V> Map<K, V> getMap(String key, Type type) {
        String json = getString(key);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, type);
        }
        return null;
    }
}

