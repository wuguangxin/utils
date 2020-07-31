package com.wuguangxin.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 *
 * 金额格式化工具类
 *
 * Created by wuguangxin on 17/4/5
 */
public class IncomeUtils {

    /**
     * 格式化收益
     * @param value 数值
     * @return
     */
    public static String format(Number value) {
        if(value == null) value = BigDecimal.ZERO;
        return MoneyUtils.format(value);
    }

    /**
     * 格式化收益
     * @param value 数值
     * @return
     */
    public static String format(String value) {
        if(TextUtils.isEmpty(value)) value = "0";
        return MoneyUtils.format(value);
    }

    /**
     * 格式化收益
     * @param number 数值
     * @param unit 单位
     * @return
     */
    public static String format(BigDecimal number, String unit) {
        if(number == null) number = BigDecimal.ZERO;
        return MoneyUtils.format(number, unit);
    }
}
