package com.wuguangxin.utils;


import android.content.Context;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;

import com.getkeepsafe.relinker.ReLinker;
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

/*
使用说明:
更多查看：https://github.com/Tencent/MMKV/wiki/android_tutorial_cn

MMKV kv = MMKV.defaultMMKV(); // 全局实例对象
MMKV kv = MMKV.mmkvWithID("myID"); // 根据业务不同创建不同实例对象

kv.encode("bool", true);
System.out.println("bool: " + kv.decodeBool("bool"));

kv.encode("int", Integer.MIN_VALUE);
System.out.println("int: " + kv.decodeInt("int"));

kv.encode("long", Long.MAX_VALUE);
System.out.println("long: " + kv.decodeLong("long"));

kv.encode("float", -3.14f);
System.out.println("float: " + kv.decodeFloat("float"));

kv.encode("double", Double.MIN_VALUE);
System.out.println("double: " + kv.decodeDouble("double"));

kv.encode("string", "Hello from mmkv");
System.out.println("string: " + kv.decodeString("string"));

byte[] bytes = {'m', 'm', 'k', 'v'};
kv.encode("bytes", bytes);
System.out.println("bytes: " + new String(kv.decodeBytes("bytes")));
*/

/**
 * <p>
 * MMKV快速存取工具类，代替SP，是微信团队开源的一个key-value存储组件。
 * </p>
 * <P>
 * <br/>基本要求：
 *  <ol>
 *   <li>MMKV 支持 API level 16 以上平台；
 *   <li>MMKV 需使用 NDK r16b 或以下进行编译 (通过源码引入 MMKV 的话)。
 *  </ol>
 * </p>
 * <ul>
 * <li>添加依赖库时，最好使用静态库导入：<br/>
 * implementation 'com.tencent: mmkv-static:1.2.2'
 * <li>如果是动态库，会额外占用2M内存空间，特别还要注意一些兼容问题：<br/>
 * implementation 'com.tencent:mmkv: 1.2.2'
 * <li>官方GitHub：https://github.com/Tencent/MMKV
 * <li>官方文档：https://github.com/Tencent/MMKV/wiki/android_setup_cn
 * </ul>
 * Created by wuguangxin on 2020-07-01.
 */
public class MmkvUtils {

    //    private static boolean sEncodeKey = true; // 是否加密Key再保存
//    private static boolean sEncodeValue = true; // 是否加密Value再保存
    private static String userId; // 当前用户ID
    private static String cryptKey; // 加密key

    /**
     * 初始化，在 Application.onCreate()中调用。
     *
     * @param context Application上下文
     */
    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            MMKV.initialize(context);
        } else {
            // 一些 Android 设备（API 19）在安装/更新 APK 时可能出错（ReLinker的说法是如果您的应用程序包含本机库，
            // 并且您的最低SDK低于API 23）, 导致 libmmkv.so 找不到。
            // 然后就会遇到 java.lang.UnsatisfiedLinkError 之类的 crash。
            // 有个开源库 ReLinker (https://github.com/KeepSafe/ReLinker）专门解决这个问题，用它来加载 MMKV：
            // 下面定义的rootDir的目录也是MMKV的原始目录，使用他即可
            String rootDir = context.getFilesDir().getAbsolutePath() + "/mmkv";
            MMKV.initialize(rootDir, libName -> ReLinker.loadLibrary(context, libName));
        }
    }

    /**
     * 切换用户
     *
     * @param userId
     */
    public static void switchUser(String userId) {
        MmkvUtils.userId = userId;
    }

    private static MMKV build() {
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
        if (TextUtils.isEmpty(cryptKey)) {
            return MMKV.defaultMMKV();
        }
        return MMKV.defaultMMKV(MMKV.SINGLE_PROCESS_MODE, cryptKey);
    }

    /**
     * 重置加密（AES）秘钥
     *
     * @param encryptKey 秘钥
     * @return
     */
    public static boolean reKey(String encryptKey) {
        return build().reKey(encryptKey);
    }

    /**
     * 清除加密，并使数据明文。
     *
     * @return
     */
    public static boolean clearKey() {
        return build().reKey(null); // 设置null即可
    }

    /** 删除指定key对应的value */
    public static void removeValueByKey(String key) {
        build().removeValueForKey(key);
    }

    /** 删除指定多个key对应的value */
    public static void removeValuesForKeys(String... keys) {
        build().removeValuesForKeys(keys);
    }

    /** 是否包含指定的key */
    public static boolean containsKey(String key) {
        return build().containsKey(key);
    }

    // ########################### 基本类型操作区1 ###################################################

    // int
    public static boolean put(String key, int value) {
        return putInt(key, value);
    }

    // long
    public static boolean put(String key, long value) {
        return putLong(key, value);
    }

    // float
    public static boolean put(String key, float value) {
        return putFloat(key, value);
    }

    // byte
    public static boolean put(String key, byte[] value) {
        return putBytes(key, value);
    }

    // double
    public static boolean put(String key, double value) {
        return putDouble(key, value);
    }

    // boolean
    public static boolean put(String key, boolean value) {
        return putBoolean(key, value);
    }

    // String
    public static boolean put(String key, String value) {
        return putString(key, value);
    }

    // Set<String>
    public static boolean put(String key, Set<String> value) {
        return putSet(key, value);
    }

    // String[]
    public static boolean put(String key, String[] value) {
        return putStringArray(key, value);
    }

    // Parcelable
    public static boolean put(String key, Parcelable value) {
        return putParcelable(key, value);
    }

    // Serializable
    public static boolean put(String key, Serializable value) {
        return putBean(key, value);
    }


    public static int get(String key, int defValue) {
        return getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getFloat(key, defValue);
    }

    public static byte[] get(String key, byte[] defValue) {
        return getBytes(key, defValue);
    }

    public static double get(String key, double defValue) {
        return getDouble(key, defValue);
    }

    public static boolean get(String key, boolean defValue) {
        return getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getString(key, defValue);
    }

    public static Set<String> get(String key, Set<String> defValue) {
        return getSet(key, defValue);
    }

    public static String[] get(String key, String[] defValue) {
        return getStringArray(key, defValue);
    }

    public static <T extends Parcelable> T get(String key, Class<T> defValue) {
        return getParcelable(key, defValue);
    }

    // ########################### 存储指定的基本数据类型 ############################################

    // ----------String

    public static boolean putString(String key, String value) {
        return build().encode(key, value);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String defValue) {
        return build().decodeString(key, defValue);
    }

    // ----------int

    public static boolean putInt(String key, int value) {
        return build().encode(key, value);
    }

    public static int getInt(String key) {
        return build().decodeInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        return build().decodeInt(key, defValue);
    }

    // ----------long

    public static boolean putLong(String key, long value) {
        return build().encode(key, value);
    }

    public static long getLong(String key) {
        return getLong(key, 0L);
    }

    public static long getLong(String key, long defValue) {
        return build().decodeLong(key, defValue);
    }

    // ----------boolean

    public static boolean putBoolean(String key, boolean value) {
        return build().encode(key, value);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return build().decodeBool(key, defValue);
    }

    // ----------float

    public static boolean putFloat(String key, float value) {
        return build().encode(key, value);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0.0F);
    }

    public static float getFloat(String key, float defValue) {
        return build().decodeFloat(key, defValue);
    }

    // ----------double

    public static boolean putDouble(String key, double value) {
        return build().encode(key, value);
    }

    public static double getDouble(String key) {
        return getDouble(key, 0.0D);
    }

    public static double getDouble(String key, double defValue) {
        return build().decodeDouble(key, defValue);
    }

    // ----------byte

    public static boolean putBytes(String key, byte[] value) {
        return build().encode(key, value);
    }

    public static byte[] getBytes(String key) {
        return getBytes(key, (byte[]) null);
    }

    public static byte[] getBytes(String key, byte[] defValue) {
        return build().decodeBytes(key, defValue);
    }

    // ----------Set<String>

    public static boolean putSet(String key, Set<String> value) {
        return build().encode(key, value);
    }

    public static Set<String> getSet(String key) {
        return getSet(key, (Set<String>) null);
    }

    public static Set<String> getSet(String key, Set<String> defValue) {
        return build().decodeStringSet(key, defValue);
    }

    // ---------- Set<String>

    public static boolean putStringSet(String key, Set<String> value) {
        return build().encode(key, value);
    }

    public static Set<String> getStringSet(String key) {
        return getStringSet(key, null);
    }

    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return build().getStringSet(key, defValue);
    }

    // ---------- String[]

    public static boolean putStringArray(String key, String[] value) {
        Set<String> set = new HashSet<>(); // 转为set来存储
        Collections.addAll(set, value);
        return build().encode(key, set);
    }

    public static String[] getStringArray(String key) {
        return getStringArray(key, null);
    }

    public static String[] getStringArray(String key, String[] defValue) {
        Set<String> set = getSet(key);
        if (set != null) {
            return (String[]) set.toArray(new String[0]);
        }
        return defValue;
    }

    // ########################### 存储对象数据类型 #################################################

    // ------- Parcelable

    public static boolean putParcelable(String key, Parcelable value) {
        return build().encode(key, value);
    }

    public static <T extends Parcelable> T getParcelable(String key) {
        return build().decodeParcelable(key, null);
    }

    public static <T extends Parcelable> T getParcelable(String key, Class<T> defValue) {
        return build().decodeParcelable(key, defValue);
    }

    // ------- Serializable

    public static <T extends Serializable> boolean putBean(String key, T value) {
        return putString(key, new Gson().toJson(value));
    }

    public static <T> T getBean(String key, Type type) {
        String json = getString(key);
        return TextUtils.isEmpty(json) ? null : new Gson().fromJson(json, type);
    }

    // ------- BigDecimal

    public static boolean putBigDecimal(String key, BigDecimal value) {
        return putString(key, value == null ? BigDecimal.ZERO.toString() : value.toString());
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

    // ------- JSONObject

    public static boolean putJSONObject(String key, JSONObject value) {
        return putString(key, value == null ? null : value.toString());
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

    // ------- JSONArray

    public static boolean putJSONArray(String key, JSONArray value) {
        return putString(key, value == null ? null : value.toString());
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

    /**
     * 用于存储List集合
     *
     * @param key 存储的key
     * @param list 数据List
     * @return 保存结果
     */
    public static <T> boolean putList(String key, List<T> list) {
        return putString(key, new Gson().toJson(list));
    }

    /**
     * 获取List集合
     *
     * @param key key
     * @param type 数据类型Type
     * @return 对应的Lis集合
     */
    public static <T> List<T> getList(String key, Type type) {
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
    public static <K, V> boolean putMap(String key, Map<K, V> map) {
        return putString(key, new Gson().toJson(map));
    }

    /**
     * 用于获取List集合
     *
     * @param key key
     * @param type 数据类型Type
     * @return 对应的Lis集合
     */
    public static <K, V> Map<K, V> getMap(String key, Type type) {
        String json = getString(key);
        if (!TextUtils.isEmpty(json)) {
            return new Gson().fromJson(json, type);
        }
        return null;
    }


    //=============================================================================================

    /**
     * md5加密
     */
    /*private static String encodeKey(String text) {
        // mmkv内部已实现AES加密解密机制。
        // 查看：https://github.com/Tencent/MMKV/wiki/android_advance_cn#%E5%8A%A0%E5%AF%86

        try {
            return sEncodeKey && !TextUtils.isEmpty(text) ? MD5.encode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }*/

    /**
     * 给Value加密
     */
    /*private static String encodeValue(String text) {
        // mmkv内部已实现AES加密解密机制。
        // 查看：https://github.com/Tencent/MMKV/wiki/android_advance_cn#%E5%8A%A0%E5%AF%86

        try {
            return sEncodeValue && !TextUtils.isEmpty(text) ? DES.encode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }*/

    /**
     * 给Value解密
     */
    /*private static String decodeValue(String text) {
        // mmkv内部已实现AES加密解密机制。
        // 查看：https://github.com/Tencent/MMKV/wiki/android_advance_cn#%E5%8A%A0%E5%AF%86

        try {
            return sEncodeValue && !TextUtils.isEmpty(text) ? DES.decode(text) : text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }*/


//    /**
//     * 解析集合
//     *
//     * @param list
//     * @return
//     */
//    public static <T> String ToJsonByTObject(List<T> list) {
//        //json格式  [{"":"","":""},{}]
//        StringBuffer sb = new StringBuffer("[");
//        if (list != null) {
//            try {
//                //获取集合类型
//                int j = list.size();
//                for (int i = 0; i < j; i++) {
//                    Class<?> c = Class.forName(list.get(i).getClass().getName());
//                    Field[] fs = c.getDeclaredFields();
//                    sb.append("{");
//                    for (int k = 0; k < fs.length; k++) {
//                        Object objs = list.get(i);
//                        StringBuffer suf = new StringBuffer("get");
//                        suf.append(captureName(fs[k].getName()));
//                        Method me = c.getDeclaredMethod(suf.toString());
//                        Object o = me.invoke(objs);
//                        //判断属性值是否为空
//                        if (o != null) {
//                            String str = fs[k].getType().getName();
//                            //注解判断属性是否是本地类
//                            Entity entity = fs[k].getAnnotation(Entity.class);
//                            if (entity != null) {
//                                //判断是否是对象
//                                //if(isMyObject(str)){
//                                analysisObject(str, sb, fs, k, o);
//                            } else {
//                                if (k != 0) {
//                                    sb.append(",");
//                                }
//                                sb.append("\"").append(fs[k].getName()).append("\":\"").append(o).append("\"");
//                            }
//                        }
//                    }
//                    sb.append("}");
//                    if (i != j - 1) {
//                        sb.append(",");
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            sb.append("]");
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 解析属性为对象的本地类，p_name
//     *
//     * @param str
//     */
//    public static void analysisObject(String str, StringBuffer sb, Field[] fs, int k, Object o) {
//        try {
//            String content = getFirstA(fs[k].getName());
//            Class<?> c1 = Class.forName(str);
//            Field[] f1 = c1.getDeclaredFields();
//            for (int l = 0; l < f1.length; l++) {
//                StringBuffer suf = new StringBuffer("get");
//                suf.append(captureName(f1[l].getName()));
//                Method me = c1.getDeclaredMethod(suf.toString());
//                Object ob = me.invoke(o);
//                if (ob != null) {
//                    String str1 = f1[l].getType().getName();
//                    Entity entity = f1[l].getAnnotation(Entity.class);
//                    if (entity != null) {
//                        //判断是否是对象
//                        //if(isMyObject(str)){
//                        analysisObject(str1, sb, f1, l, ob);
//                    } else {
//                        sb.append(",");
//                        sb.append("\"").append(content).append(f1[l].getName()).append("\":\"").append(ob).append("\"");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 字符串第一个字符大写
//     *
//     * @param name
//     * @return
//     */
//    public static String captureName(String name) {
//        char[] cs = name.toCharArray();
//        //字符a对应数字97
//        cs[0] -= 32;
//        return String.valueOf(cs);
//    }
//
//    /**
//     * 判断是否是本地类，通过endswith获取字符串后缀，然后判断是java类型或者对象
//     *
//     * @param str
//     * @return
//     */
//    private static boolean isMyObject(String str) {
//        boolean state = false;
//        if (!str.endsWith("Object")
//                && !str.endsWith("String")
//                && !str.endsWith("Boolean")
//                && !str.endsWith("Integer")
//                && !str.endsWith("Double")
//                && !str.endsWith("Float")
//                && !str.endsWith("Long")
//                && !str.endsWith("Char")
//                && !str.endsWith("Byte")
//                && !str.endsWith("Date")) {
//            state = true;
//        }
//        return state;
//    }
//
//    /**
//     * 首字母小写
//     *
//     * @param str
//     * @return
//     */
//    public static String getFirstA(String str) {
//        String name = str.toLowerCase();
//        char[] cs = name.substring(0, 1).toCharArray();
//        return String.valueOf(cs[0]) + "_";
//    }
}

