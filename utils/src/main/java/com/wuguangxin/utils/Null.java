package com.wuguangxin.utils;

import java.lang.reflect.ParameterizedType;

/**
 * 对象非null判断
 * Created by wuguangxin on 17/5/18.
 */
public class Null {

    /**
     * 判断对象是否为null，为null的话直接抛出一个异常
     * @param obj 对象
     * @param <T> 对象为泛型类
     * @return 返回原对象
     */
    public static <T> T check(T obj) {
        if (obj == null) {
            throw new NullPointerException("obj is null");
        }
        return obj;
    }

    /**
     * 判断对象是否为null，为null的话尝试创建一个Clazz<T>类型对象返回
     * @param obj 对象
     * @param clazz 默认返回的类
     * @param <T> 对象为泛型类
     * @return 返回原对象
     */
    public static <T> T check(T obj, Class<T> clazz) {
        if (obj == null && clazz != null) {
            try {
                // 关于反射调用构造器：https://blog.csdn.net/newbie0107/article/details/89763398

                // 该反射只能反射无参公有构造函数
                T result = clazz.newInstance();

                if (result == null) {
                    //利用Constructor.newInstance()反射无参构造方法
                    result = clazz.getDeclaredConstructor().newInstance();
                }

                if (result == null) {
                    result = (T) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
                }

                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    /**
     * 判断对象是否为null，为null的话创建一个clazz对象返回
     * @param obj 对象
     * @param def 默认对象
     * @param <T> 对象为泛型类
     * @return 返回原对象
     */
    public static <T> T check(T obj, T def) {
        return obj == null ? def : obj;
    }
}
