package com.wuguangxin.utils;

import android.widget.CompoundButton;

import java.lang.reflect.Field;

/**
 * CompoundButton的操作工具类
 * 
 * Created by wuguangxin on 2018/3/1.
 */
public class CompoundButtonUtils {

    /**
     * 设置
     * @param checkBox
     * @param checked
     */
    public static void setChecked(CompoundButton checkBox, boolean checked) {
        if (checkBox == null || checkBox.isChecked() == checked) {
            return;
        }
        try {
            // 获取class对象
            Class<?> clazz = checkBox.getClass();
            while (!"CompoundButton".equals(clazz.getSimpleName())) {
                clazz = clazz.getSuperclass();
            }

            // 获取对象的属性
            Field field = clazz.getDeclaredField("mOnCheckedChangeListener");
            // 对象的属性设置为可访问
            field.setAccessible(true);
            Object value = field.get(checkBox);
            if (value != null) {
                CompoundButton.OnCheckedChangeListener listener = (CompoundButton.OnCheckedChangeListener) value;
                checkBox.setOnCheckedChangeListener(null);
                checkBox.setChecked(checked);
                checkBox.setOnCheckedChangeListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}