package com.wuguangxin.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额格式化工具类
 * <p>Created by wuguangxin on 14/6/2 </p>
 */
public class MoneyUtils {
    /**
     * BigDecimal bd = new BigDecimal(123456789);
     * System.out.println(formatString(",###,###", bd)); 	  //out: 123,456,789
     * System.out.println(formatString("##,####,###", bd));  //out: 123,456,789
     * System.out.println(formatString("######,###", bd));	  //out: 123,456,789
     * System.out.println(formatString("#,##,###,###", bd)); //out: 123,456,789
     * System.out.println(formatString(",###,###.00", bd));  //out: 123,456,789.00
     * System.out.println(formatString(",###,##0.00", bd));  //out: 123,456,789.00
     * BigDecimal bd1 = new BigDecimal(0);
     * System.out.println(formatString(",###,###", bd1)); 	  //out: 0
     * System.out.println(formatString(",###,###.00", bd1)); //out: .00
     * System.out.println(formatString(",###,##0.00", bd1)); //out: 0.00
     */
    private static final String REG = "######.######";


    /**
     * 格式化金额（小数点后的0会去掉，如1.0，返回1，1.010，返回1.01）
     * @param value 字符串金额
     * @return
     */
    public static String format(String value) {
        return format(formatBigDecimal(value));
    }

    /**
     * 格式化金额
     * @param value 金额
     * @return
     */
    public static String format(Number value) {
        return format(value, null);
    }

    /**
     * 格式化金额
     * @param value 金额
     * @param suffix 后缀，比如"元"
     * @return
     */
    public static String format(String value, String suffix) {
        return format(formatBigDecimal(value), suffix);
    }

    /**
     * 格式化金额
     *
     * @param value 金额
     * @param suffix 后缀，比如"元"
     * @return
     */
    public static String format(Number value, String suffix) {
        if (value == null) value = BigDecimal.ZERO;
        if (suffix == null) suffix = "";
        return new DecimalFormat(REG).format(value) + suffix;
    }

    /**
     * 格式化金额
     *
     * @param value 数值
     * @param suffix 后缀，比如"元"
     * @param emptyValue 如果格式化后的金额为0，则返回该值
     * @return
     */
    public static String format(Number value, String suffix, String emptyValue) {
        if (isZero(value)) return emptyValue;
        if (suffix == null) suffix = "";
        return new DecimalFormat(REG).format(value) + suffix;
    }

    /**
     * 格式化金额
     *
     * @param value 字符串数值
     * @param unit 附加文字，比如"元"
     * @param emptyValue 如果格式化后的金额为0，则返回该值
     * @return
     */
    public static String format(String value, String unit, String emptyValue) {
        BigDecimal bd = formatBigDecimal(value);
        if (isZero(bd)) return emptyValue;
        if (TextUtils.isEmpty(unit)) unit = "";
        DecimalFormat format = new DecimalFormat(REG);
        return format.format(bd) + unit;
    }

    /**
     * 去小数.00 或 .0（比如1.90，清除后是1.9）
     *
     * @param value 数值字符串
     * @return
     */
    public static String clearZero(String value) {
        if (value != null && value.indexOf(".") > 0) {
            value = value.replaceAll("0+?$", "");//去掉后面无用的零
            value = value.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return value;
    }

    /**
     * 将数字型货币转换为中文型货币
     *
     * @param number 数字
     * @return 金额字符串
     */
    public static String formatCN(Number number) {
        if (number.doubleValue() == 0) {
            return "零元整";
        }
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
        String unit = "仟佰拾兆仟佰拾亿仟佰拾万仟佰拾元角分";
        // 注意 如果是0.01，这里会处理为 .01，千万别格式化为 0.01，即reg不能写为 "#.00"
        String s = new DecimalFormat("#.00").format(number);
        s = s.replaceAll("\\.", "");
        int l = unit.length();
        StringBuffer sb = new StringBuffer(unit);
        for (int i = s.length() - 1; i >= 0; i--) {
            sb = sb.insert(l - s.length() + i, digit[(s.charAt(i) - 0x30)]);
        }
        s = sb.substring(l - s.length(), l + s.length());
        s = s.replaceAll("零[拾佰仟]", "零").replaceAll("零{2,}", "零").replaceAll("零([兆万元])", "$1").replaceAll("零[角分]", "");
        if (s.endsWith("角")) {
            s += "零分";
        }
        if (!s.contains("角") && !s.contains("分") && s.contains("元")) {
            s += "整";
        }
        if (s.contains("分") && !s.contains("整") && !s.contains("角")) {
            s = s.replace("元", "元零");
        }
        return s;
    }

    /**
     * 校验字符串是否是规范的金额格式字符串
     *
     * @param str
     * @return
     */
    private static String checkArgumentFormat(String str) {
        if (!TextUtils.isEmpty(str) && str.split("\\.").length > 2) {
            throw new IllegalArgumentException(String.format("\"%s\" ", "不是合法的金额格式，请检查"));
        }
        return str;
    }

    /**
     * 判断是否为0
     * @param value
     * @return
     */
    public static boolean isZero(Number value) {
        if (value == null) return true;
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) == 0;
    }

    /**
     * 将符合数值格式的对象格式化为 BigDecimal 对象
     *
     * @param number
     * @return
     */
    public static BigDecimal formatBigDecimal(Object number) {
        if (number == null) {
            return BigDecimal.ZERO;
        }
        try {
            if (number instanceof Number) {
                return new BigDecimal(number.toString());
            } else if (number instanceof String) {
                String str = ((String) number).replaceAll(",", "");
                return new BigDecimal(str);
            } else {
                return BigDecimal.ZERO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 计算收益
     * @param money 本金
     * @param income 利率
     * @param duration 期限
     * @param ratio 系数，如果是按年算，则为当年总天数，按月算，则为12（个月）
     * @return
     */
    public static BigDecimal getProfit(BigDecimal money, BigDecimal income, int duration, int ratio) {
        boolean isZero = money == null || money.compareTo(BigDecimal.ZERO) == 0;
        if (isZero) {
            return BigDecimal.ZERO;
        } else {
            return money
                    .multiply(income)
                    .multiply(BigDecimal.valueOf(duration))
                    .divide(BigDecimal.valueOf(ratio * 100L), 2, BigDecimal.ROUND_DOWN);
        }
    }
}