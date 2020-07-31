package com.wuguangxin.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 算术工具类（加减乘除）
 *
 * Created by wuguangxin on 17/5/15.
 */
public class MathUtils {

    /**
     * 加
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal add(float value1, float value2) {
        return add(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }

    /**
     * 加
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal add(double value1, double value2) {
        return add(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }

    /**
     * 减
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal sub(float value1, float value2) {
        return sub(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }

    /**
     * 减
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal sub(double value1, double value2) {
        return sub(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }

    /**
     * 乘
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal mul(float value1, float value2) {
        return mul(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }

    /**
     * 乘
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal mul(double value1, double value2) {
        return mul(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""));
    }


    /**
     * 除
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(float value1, float value2) {
        return div(value1, value2, 2);
    }


    /**
     * 除
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(float value1, float value2, int scale) {
        return div(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""), scale);
    }

    /**
     * 除
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(double value1, double value2) {
        return div(value1, value2, 2);
    }


    /**
     * 除
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(double value1, double value2, int scale) {
        return div(toBigDecimal(value1 + ""), toBigDecimal(value2 + ""), scale);
    }

    /**
     * 加（自动将null转为0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        if(value1 == null) value1 = BigDecimal.ZERO;
        if(value2 == null) value2 = BigDecimal.ZERO;
        return value1.add(value2);
    }

    /**
     * 加（自动将null转为0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal add(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) value1 = "0";
        if (TextUtils.isEmpty(value2)) value2 = "0";
        BigDecimal bigValue1 = toBigDecimal(value1);
        BigDecimal bigValue2 = toBigDecimal(value2);
        return bigValue1.add(bigValue2);
    }

    /**
     * 减（自动将null转为0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal sub(BigDecimal value1, BigDecimal value2) {
        if(value1 == null) value1 = BigDecimal.ZERO;
        if(value2 == null) value2 = BigDecimal.ZERO;
        return value1.subtract(value2);
    }

    /**
     * 减（自动将null转为0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal sub(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) value1 = "0";
        if (TextUtils.isEmpty(value2)) value2 = "0";
        BigDecimal bigValue1 = toBigDecimal(value1);
        BigDecimal bigValue2 = toBigDecimal(value2);
        return bigValue1.subtract(bigValue2);
    }


    /**
     * 乘
     * （自动将null转为0，其中一个数为0，则返回0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal mul(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) value1 = "0";
        if (TextUtils.isEmpty(value2)) value2 = "0";
        BigDecimal bigValue1 = toBigDecimal(value1);
        BigDecimal bigValue2 = toBigDecimal(value2);
        return mul(bigValue1, bigValue2);
    }


    /**
     * 乘
     * （自动将null转为0，其中一个数为0，则返回0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal mul(BigDecimal value1, BigDecimal value2) {
        if (value1 == null || value2 == null) return BigDecimal.ZERO;
        if (value1.equals(BigDecimal.ZERO) || value2.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return value1.multiply(value2);
    }

    /**
     * 除
     * （自动将null转为0，其中一个value为0，则返回0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(String value1, String value2) {
        if (TextUtils.isEmpty(value1)) value1 = "0";
        if (TextUtils.isEmpty(value2)) value2 = "0";
        BigDecimal bigValue1 = toBigDecimal(value1);
        BigDecimal bigValue2 = toBigDecimal(value2);
        return div(bigValue1, bigValue2, 2);
    }

    /**
     * 除
     * （自动将null转为0，其中一个value为0，则返回0）
     * @param value1
     * @param value2
     * @param scale 保留小数位数
     * @return
     */
    public static BigDecimal div(String value1, String value2, int scale) {
        if (TextUtils.isEmpty(value1)) value1 = "0";
        if (TextUtils.isEmpty(value2)) value2 = "0";
        BigDecimal bigValue1 = toBigDecimal(value1);
        BigDecimal bigValue2 = toBigDecimal(value2);
        return div(bigValue1, bigValue2, scale);
    }

    /**
     * 除
     * （自动将null转为0，其中一个value为0，则返回0）
     * @param value1
     * @param value2
     * @return
     */
    public static BigDecimal div(BigDecimal value1, BigDecimal value2) {
        if (value1 == null || value2 == null) return BigDecimal.ZERO;
        if (value1.equals(BigDecimal.ZERO) || value2.equals(BigDecimal.ZERO)) return BigDecimal.ZERO;
        return div(value1, value2, 2);
    }

    /**
     * 除
     * （自动将null转为0，其中一个value为0，则返回0）
     * @param value1
     * @param value2
     * @param scale 保留小数位数
     * @return
     */
    public static BigDecimal div(BigDecimal value1, BigDecimal value2, int scale) {
        if (value1 == null || value2 == null) return BigDecimal.ZERO;
        if (value1.doubleValue() == 0||value2.doubleValue()==0) return BigDecimal.ZERO;
        return value1.divide(value2, scale, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * 判断 value 是否为 null，如果为 null 返回 BigDecimal.ZERO
     * @param value BigDecimal值
     * @return
     */
    public static BigDecimal checkNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static BigDecimal toBigDecimal(String value) {
        if(TextUtils.isEmpty(value)) {
            return BigDecimal.ZERO;
        }
        value = value.trim();
        value = value.replace(",", "");
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * 求余
     * @param value BigDecimal值 被除数
     * @param divisor BigDecimal值 除数
     * @return
     */
    public static BigDecimal divAndRemainder(BigDecimal value, BigDecimal divisor) {
        BigDecimal[] results = value.divideAndRemainder(divisor);
        return results == null ? BigDecimal.ZERO : results[1];
    }
}
