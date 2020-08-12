package com.wuguangxin.utils;

import android.text.TextUtils;

import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.WeakHashMap;

/**
 * 日期时间工具类。
 * Created by wuguangxin on 15/3/31.
 */
public class DateUtils {

    private static final long  ONE_SECOND = 1000;            //一秒钟的毫秒数
    private static final long  ONE_MINUTE = 60 * ONE_SECOND; //一分钟的毫秒数
    private static final long  ONE_HOUR   = 60 * ONE_MINUTE; //一小时的毫秒数
    private static final long  ONE_DAY    = 24 * ONE_HOUR;   //一天的毫秒数

    /* ****************************************************************************
     * 获取 格式化实例对象
     * ****************************************************************************/

    /** 使用弱引用Map来存缓存SimpleDateFormat实例对象。*/
    private static WeakHashMap<String, SimpleDateFormat> sFormatWeakHashMap = new WeakHashMap<>();

    /**
     * 获取格式化对象。
     * @param pattern 参考 yyyy-MM-dd HH:mm:ss
     */
    public static SimpleDateFormat getFormat(String pattern) {
        SimpleDateFormat format = sFormatWeakHashMap.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern, Locale.getDefault());
            sFormatWeakHashMap.put(pattern, format);
        }
        return format;
    }

    public interface Format {
        String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
        String FORMAT_DATE_2 = "yyyy-MM-dd HH:mm";
        String FORMAT_DATE_SHORT = "yyyy-MM-dd";
        String FORMAT_MIDDLE = "MM-dd HH:mm";
        String FORMAT_TIME = "HH:mm:ss";
        String FORMAT_TIME_SHORT = "HH:mm";
    }

    public static SimpleDateFormat getFormatDate() {
        return getFormat(Format.FORMAT_DATE);
    }

    public static SimpleDateFormat getFormatDate2() {
        return getFormat(Format.FORMAT_DATE_2);
    }

    public static SimpleDateFormat getFormatDateShort() {
        return getFormat(Format.FORMAT_DATE_SHORT);
    }

    public static SimpleDateFormat getFormatTime() {
        return getFormat(Format.FORMAT_TIME);
    }

    public static SimpleDateFormat getFormatTimeShort() {
        return getFormat(Format.FORMAT_TIME_SHORT);
    }

    public static SimpleDateFormat getFormatMiddle() {
        return getFormat(Format.FORMAT_MIDDLE);
    }

    /* *****************************************************************************************
     *          get 无参
     *******************************************************************************************/

    /**
     * 获取系统长日期Date，格式：yyyy-MM-dd HH:mm:ss。
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取系统长日期String，格式：yyyy-MM-dd HH:mm:ss。
     */
    public static String getDateString() {
        return formatDate(getDate(), getFormatDate());
    }

    /**
     * 获取系统短日期Date，格式：yyyy-MM-dd。
     */
    public static Date getDateShort() {
        return formatDateShort(getDate());
    }

    /**
     * 获取系统短日期String，格式：yyyy-MM-dd。
     */
    public static String getDateShortString() {
        return formatDate(getDate(), getFormatDateShort());
    }

    /**
     * 获取网络长日期String，格式：yyyy-MM-dd HH:mm:ss。
     */
    public static String getDateStringFromNet() {
        return formatDate(getTimestampFromNet(null), getFormatDate());
    }

    /**
     * 获取网络长日期时间戳，默认时间源 http://www.beijing-time.org
     */
    public static long getTimestampFromNet() {
        return getTimestampFromNet(null);
    }

    /**
     * 获取指定服务器网络时间戳。
     * @param webUrl 任何一个网站域名或网络IP，如 http://www.beijing-time.org
     */
    public static long getTimestampFromNet(String webUrl) {
        if (TextUtils.isEmpty(webUrl)) {
            webUrl = "http://www.beijing-time.org";
        }
        try {
            URL url = new URL(webUrl);//取得资源对象
            java.net.URLConnection conn = url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(2000);
            conn.connect();
            return conn.getDate(); //取得网站日期时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    /* *****************************************************************************************
     *          format to String
     * ****************************************************************************************/

    /**
     * Date => String：yyyy-MM-dd HH:mm:ss
     * @param date Date
     */
    public static String formatDate(Date date) {
        return formatDate(date, getFormatDate());
    }

    /**
     * 时间戳 => String：yyyy-MM-dd HH:mm:ss
     * @param timestamp long类型时间戳
     */
    public static String formatDateString(long timestamp) {
        return formatDate(formatDate(timestamp), getFormatDate());
    }

    /**
     * 时间戳 => String：yyyy-MM-dd HH:mm:ss
     * @param timestamp String类型时间戳
     */
    public static String formatDateString(String timestamp) {
        if (timestamp == null || timestamp.length() != 13) { // 毫秒值是13位
            return null;
        }
        return formatDate(Long.parseLong(timestamp), getFormatDate());
    }

    /**
     * Date => String：yyyy-MM-dd HH:mm:ss
     * @param date Date
     */
    public static String formatDateString(Date date) {
        return formatDate(date, getFormatDate());
    }

    /**
     * 时间戳 => String：yyyy-MM-dd HH:mm
     * @param timestamp 时间戳
     */
    public static String formatDateNoSecondString(long timestamp) {
        return formatDate(formatDate(timestamp), getFormatDate2());
    }

    /**
     * 时间戳 => String：yyyy-MM-dd
     * @param timestamp long类型时间戳
     */
    public static String formatDateShortString(long timestamp) {
        return formatDate(formatDate(timestamp), getFormatDateShort());
    }

    /**
     * Date => String：yyyy-MM-dd
     * @param date 日期对象
     */
    public static String formatDateShortString(Date date) {
        return formatDate(date, getFormatDateShort());
    }

    /**
     * 时间戳 => String：自定义
     * @param timestamp 时间戳
     * @param format 格式化对象 SimpleDateFormat
     */
    public static String formatDate(long timestamp, SimpleDateFormat format) {
        return formatDate(DateUtils.formatDate(timestamp), format);
    }

    /**
     * Date => String：自定义
     * @param date Date对象
     * @param format 格式化对象 SimpleDateFormat
     */
    public static String formatDate(Date date, SimpleDateFormat format) {
        return (date == null || format == null) ? null : format.format(date);
    }

    /* *****************************************************************************************
     *          format to Date
     *******************************************************************************************/

    /**
     * 时间戳 => Date：yyyy-MM-dd HH:mm:ss
     * @param timestamp 时间戳
     */
    public static Date formatDate(long timestamp) {
        return timestamp <= 0 ? null : new Date(timestamp);
    }

    /**
     * 时间戳 => Date：yyyy-MM-dd
     * @param timestamp 时间戳
     */
    public static Date formatDateShort(long timestamp) {
        return formatDate(formatDateString(formatDate(timestamp)), getFormatDateShort());
    }

    /**
     * String => Date：yyyy-MM-dd
     * @param date 日期字符串
     */
    public static Date formatDateShort(String date) {
        return formatDate(date, getFormatDateShort());
    }

    /**
     * Date => Date：yyyy-MM-dd
     * @param date 日期对象
     */
    public static Date formatDateShort(Date date) {
        final SimpleDateFormat format = getFormatDateShort();
        String dateShortString = formatDate(date, format); // 转为短日期形式
        return formatDate(dateShortString, format); // 再解析为Date
    }

    /**
     * 时间戳 => Date：自定义Format
     * @param timestamp 时间戳
     * @param format 参考：yyyy-MM-dd HH:mm:ss
     */
    public static Date formatDate(long timestamp, String format) {
        final String dateString = formatDate(formatDate(timestamp), getFormat(format));
        return formatDate(dateString, format);
    }

    /**
     * String => Date：自定义SimpleDateFormat
     * @param date 标准日期字符串
     * @param format 参考：yyyy-MM-dd HH:mm:ss
     */
    public static Date formatDate(String date, String format) {
        return formatDate(date, getFormat(format), null);
    }

    /**
     * String => Date：yyyy-MM-dd HH:mm:ss，自定义ParsePosition
     * @param date 标准日期字符串
     * @param position 解析索引位置
     */
    public static Date formatDate(String date, ParsePosition position) {
        return formatDate(date, null, position);
    }

    /**
     * String => Date：自定义SimpleDateFormat
     * @param date 标准日期字符串
     */
    public static Date formatDate(String date, SimpleDateFormat format) {
        return formatDate(date, format, null);
    }

    /**
     * String => Date：yyyy-MM-dd HH:mm:ss
     * @param data 日期字符串，参考：yyyy-MM-dd HH:mm:ss
     */
    public static Date formatDate(String data) {
        return formatDate(data, getFormatDate(), new ParsePosition(0));
    }

    /**
     * String => Date：自定义SimpleDateFormat，自定义ParsePosition
     * @param date 必须符合以下几种格式，如果传入的时间格式不正确，则返回0。
     * 1：yyyy-MM
     * 2：yyyy-MM-dd
     * 3：yyyy-MM-dd HH:mm
     * 4：yyyy-MM-dd HH:mm:ss
     * 5：yyyy/MM/dd HH:mm:ss
     * 6：yyyy.MM.dd HH:mm:ss
     * 7：yyyy年MM月dd日HH时mm分ss秒。
     * @param format 格式化对象
     * @param position 指定解析索引位置
     */
    public static Date formatDate(String date, SimpleDateFormat format, ParsePosition position) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        try {
            if (date.contains(".")) date = date.replaceAll("\\.", "-");
            if (date.contains("/")) date = date.replaceAll("/", "-");
            if (date.contains("年")) date = date.replaceAll("年", "-");
            if (date.contains("月")) date = date.replaceAll("月", "-");
            if (date.contains("日")) date = date.replaceAll("日", " ");
            if (date.contains("时")) date = date.replaceAll("时", ":");
            if (date.contains("分")) date = date.replaceAll("分", ":");
            if (date.contains("秒")) date = date.replaceAll("秒", "");
            date = date.trim(); // 去除首尾空格

            if (format == null) {
                int length = date.length();
                switch (length) {
                case 7: format = getFormat("yyyy-MM"); break;
                case 10: format = getFormat("yyyy-MM-dd"); break;
                case 16: format = getFormat("yyyy-MM-dd "); break;
                case 19:
                default: format = getFormatDate(); break;
                }
            }

            if (position != null) {
                return format.parse(date, position);
            } else {
                return format.parse(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* *****************************************************************************************
     *          format to 时间戳
     *******************************************************************************************/

    /**
     * String => 时间戳
     * @param dateString 标准日期字符串
     */
    public static long formatTimestamp(String dateString) {
        return formatTimestamp(formatDate(dateString, getFormatDate()));
    }

    /**
     * Date => 时间戳
     * @param date 标准日期字符串
     */
    public static long formatTimestamp(Date date) {
        return date == null ? 0 : date.getTime();
    }

    /* *****************************************************************************************
     *          其他
     *******************************************************************************************/
    /**
     * 计算两个日期间隔几天
     * @param startDate 开始日期 long
     * @param endTate 结束日期 long
     */
    public static long getIntervalDays(long startDate, long endTate) {
        return getIntervalDays(formatDateShort(endTate), formatDateShort(startDate));
    }

    /**
     * 计算两个日期间隔几天
     * @param startDate 开始日期 Date
     * @param endTate 结束日期 Date
     */
    public static long getIntervalDays(Date startDate, Date endTate) {
        ObjectUtils.requireNonNull(startDate, "[startDate] must not be null");
        ObjectUtils.requireNonNull(endTate, "[endTate] must not be null");
        return Math.abs(endTate.getTime() - startDate.getTime());
    }

    /**
     * 获取两个日期的间隔(天时分秒)，如："01天22时33分51秒" 或 "01:22:33:51"。
     * @param startTime 开始日期 long
     * @param endTime 结束日期 long
     * @param isFormat true：00天11时22分33秒，false：00:11:22:33
     */
    public static String diffDays(long startTime, long endTime, boolean isFormat) {
        return diffDays(getIntervalDays(startTime, endTime), isFormat);
    }

    /**
     * 获取两个日期的间隔天数（精确到天，会把传入的两个时间戳去掉时分秒）
     * @param startTime 开始日期
     * @param endTime 结束日期
     */
    public static int diffDays(long startTime, long endTime) {
        return diffDays(formatDateShort(startTime), formatDateShort(endTime));
    }

    /**
     * 比较两个日期相差的天数，精确到天，时分秒去掉再比较。
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    public static int diffDays(Date startDate, Date endDate) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(startDate);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(endDate);
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);

            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);
            if (year1 != year2) {
                // 同一年
                int timeDistance = 0;
                for (int i = year1; i < year2; i++) {
                    // 是否是闰年，run暖按366天算，否则按365天
                    boolean isLeapYear = i % 4 == 0 && i % 100 != 0 || i % 400 == 0;
                    if (isLeapYear) {
                        timeDistance += 366;
                    } else {
                        timeDistance += 365;
                    }
                }
                return timeDistance + (day2 - day1);
            } else {
                //不同年
                return day2 - day1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将指定毫秒值格式化为："00天00时00分00秒" 或 "00:00:00:00"
     * @param countTime 多少毫秒
     * @param isFormat true：00天00时00分00秒，false：00:00:00:00
     */
    public static String diffDays(long countTime, boolean isFormat) {
        StringBuilder dateBuilder = new StringBuilder();
        long diff = new java.sql.Date(countTime).getTime();
        int day = (int) (diff / ONE_DAY);                        //天
        int hour = (int) (diff % ONE_DAY / ONE_HOUR);                    //时
        int min = (int) (diff % ONE_DAY % ONE_HOUR / ONE_MINUTE);                //分
        int sec = (int) (diff % ONE_DAY % ONE_HOUR % ONE_MINUTE / ONE_SECOND);        //秒
        if (day + hour + min + sec < 0) {
            return null;
        }
        String dS = day < 10 ? "0" + day : "" + day;
        String hS = hour < 10 ? "0" + hour : "" + hour;
        String mS = min < 10 ? "0" + min : "" + min;
        String sS = sec < 10 ? "0" + sec : "" + sec;
        if (isFormat) {
            dateBuilder.append(dS).append("天");
            dateBuilder.append(hS).append("时");
            dateBuilder.append(mS).append("分");
            dateBuilder.append(sS).append("秒");
        } else {
            dateBuilder.append(dS).append(":");
            dateBuilder.append(hS).append(":");
            dateBuilder.append(mS).append(":");
            dateBuilder.append(sS).append(":");
        }
        return dateBuilder.toString();
    }

    /**
     * 获取两个时间的间隔(00分00秒)
     *
     * @param startTime 开始时间戳
     * @param endTime 结束时间戳
     * @return 返回如：33:51
     */
    public static String diffMinuteSecond(long startTime, long endTime) {
        long diff = new java.sql.Date(endTime).getTime() - new java.sql.Date(startTime).getTime();
        long min = diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;  //分
        long sec = diff % ONE_DAY % ONE_HOUR % ONE_MINUTE / ONE_SECOND; //秒
        if (min + sec < 0) {
            return "00:00";
        }
        StringBuilder buffer = new StringBuilder();
        if (min < 10) {
            buffer.append(0);
        }
        buffer.append(min);
        buffer.append(":");
        if (sec < 10) {
            buffer.append(0);
        }
        buffer.append(sec);
        return buffer.toString();
    }

    /**
     * 格式化指定时间毫秒数（如：00时00分00秒）
     *
     * @param totalTime 总毫秒数
     * @param format 格式化类型，例如：%s时%s分%s秒，则返回结果例如：00时00分00秒
     * @return 返回如: 00时00分00秒
     */
    public static String formatHourMinuteSecond(long totalTime, String format) {
        StringBuilder dateBuilder = new StringBuilder();
        long diff = new java.sql.Date(totalTime).getTime();
        long hour = floorModHour(diff); //时
        long min = floorModMinute(diff); //分
        long sec = floorModSecond(diff); //秒
        if (hour + min + sec < 0) {
            return null;
        }
        String hS = hour < 10 ? "0" + hour : "" + hour;
        String mS = min < 10 ? "0" + min : "" + min;
        String sS = sec < 10 ? "0" + sec : "" + sec;
        if (TextUtils.isEmpty(format)) {
            dateBuilder.append(hS).append("时");
            dateBuilder.append(mS).append("分");
            dateBuilder.append(sS).append("秒");
            return dateBuilder.toString();
        } else {
            return String.format(format, hS, mS, sS);
        }
    }

    /**
     * 格式化指定时间毫秒数为(时分秒)格式
     *
     * @param totalTime 总毫秒数
     * @param isFormat 如果为true, 返回 11时22分33秒，如果为false，则为11:22:33
     * @return 返回如: 22时33分51秒 或 22:33:51
     */
    public static String formatHourMinuteSecond(long totalTime, boolean isFormat) {
        StringBuilder dateBuilder = new StringBuilder();
        long diff = new java.sql.Date(totalTime).getTime();
        long hour = diff % ONE_DAY / ONE_HOUR + diff / ONE_DAY * 24;//时
        long min = diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;                //分
        long sec = diff % ONE_DAY % ONE_HOUR % ONE_MINUTE / ONE_SECOND;        //秒
        if (hour + min + sec < 0) {
            return null;
        }
        String hS = hour < 10 ? "0" + hour : "" + hour;
        String mS = min < 10 ? "0" + min : "" + min;
        String sS = sec < 10 ? "0" + sec : "" + sec;
        if (isFormat) {
            dateBuilder.append(hS).append("时");
            dateBuilder.append(mS).append("分");
            dateBuilder.append(sS).append("秒");
        } else {
            dateBuilder.append(hS).append(":");
            dateBuilder.append(mS).append(":");
            dateBuilder.append(sS);
        }
        return dateBuilder.toString();
    }

    /**
     * 格式化分钟数（如：00小时00分钟）
     *
     * @param totalMinute 总分钟数
     * @return 例: 1小时40分钟
     */
    public static String formatHourMinute(long totalMinute) {
        if (totalMinute <= 0) return "0分钟";
        return String.format("%s小时%s分钟", totalMinute / 60L, totalMinute % 60L);
    }

    /**
     * 格式化毫秒数（如：00小时00分钟）
     *
     * @param totalTime 总毫秒数
     * @param isFormat true：00时00分，false：00:00
     */
    public static String formatHourMinute(long totalTime, boolean isFormat) {
        StringBuilder dateBuilder = new StringBuilder();
        // 因为不显示秒，避免显示00时00分(后面我59秒之后不显示)，所以分+1，秒去掉。
        Date date = new Date(totalTime + 60 * 1000);
        date.setSeconds(0);
        long diff = date.getTime();
        long hour = diff % ONE_DAY / ONE_HOUR + diff / ONE_DAY * 24;//时
        long min = diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;//分
        if (hour + min < 0) {
            return null;
        }
        String hS = hour < 10 ? "0" + hour : "" + hour;
        String mS = min < 10 ? "0" + min : "" + min;
        if (isFormat) {
            dateBuilder.append(hS).append("时");
            dateBuilder.append(mS).append("分");
        } else {
            dateBuilder.append(hS).append(":");
            dateBuilder.append(mS);
        }
        return dateBuilder.toString();
    }

    /**
     * 格式化指定时间毫秒数自定义的格式（如：00时00分）
     *
     * @param totalTime 总毫秒数
     * @param format 格式化类型,，例如：%s时%s分，则返回结果例如：00时00分
     * @return 返回如: 00时00分
     */
    public static String formatHourMinute(long totalTime, String format) {
        StringBuilder dateBuilder = new StringBuilder();
        // 因为不显示秒，避免显示00时00分(后面我59秒之后不显示)，所以分+1，秒去掉。
        Date date = new Date(totalTime + 60 * 1000);
        date.setSeconds(0);
        long diff = date.getTime();
        long hour = diff % ONE_DAY / ONE_HOUR + diff / ONE_DAY * 24;//时
        long min = diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;             //分
        if (hour + min < 0) {
            return null;
        }
        String hS = hour < 10 ? "0" + hour : "" + hour;
        String mS = min < 10 ? "0" + min : "" + min;
        if (TextUtils.isEmpty(format)) {
            dateBuilder.append(hS).append("时");
            dateBuilder.append(mS).append("分");
            return dateBuilder.toString();
        } else {
            return String.format(format, hS, mS);
        }
    }

    /**
     * 把毫秒数转换为小时数，去掉天数。
     * @param diff 毫秒数
     */
    private static long floorModHour(long diff) {
        return diff % ONE_DAY / ONE_HOUR + diff / ONE_DAY * 24;
    }

    /**
     * 把毫秒数转换为分钟数，去掉小时数。
     * @param diff 毫秒数
     */
    private static long floorModMinute(long diff) {
        return diff % ONE_DAY % ONE_HOUR / ONE_MINUTE;
    }

    /**
     * 把毫秒数转换为秒数，去掉分钟数。
     * @param diff 毫秒数
     */
    private static long floorModSecond(long diff) {
        return diff % ONE_DAY % ONE_HOUR % ONE_MINUTE / ONE_SECOND;
    }

    /* *****************************************************************************************
     *          返回日期的值
     *******************************************************************************************/

    /**
     * 获取当前手机日期的月份（用0补位）
     *
     * @return 如 1月返回 "01"
     */
    public static String getCurrentMonth() {
        return DateUtils.getDateShortString().substring(5, 7);
    }

    /**
     * 获取当前手机日期的月份（不补位）
     *
     * @return 如1月获取1
     */
    public static int getCurrentMonthInt() {
        return getMonthInt(getCurrentMonth());
    }

    /**
     * 根据2位String月份获取int类型月份（）
     *
     * @param month 2位String月份
     * @return 如02获取2
     */
    public static int getMonthInt(String month) {
        int monthInt = 0;
        if (!TextUtils.isEmpty(month)) {
            try {
                monthInt = Integer.parseInt(month);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return monthInt;
    }

    /**
     * 根据字符串日期获年份(已加1900)
     *
     * @param dateString 长日期 如2015-01-01 10:20:30 返回2015
     * @return 年份
     */
    public static int getYear(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getYear() + 1900;
    }

    /**
     * 根据字符串日期获取当前第几月（0-11）
     *
     * @param dateString 长日期 如2015-01-02 10:20:30 返回0
     * @return 月份（0-11）
     */
    public static int getMonth(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getMonth();
    }

    /**
     * 根据字符串日期获取当前月是第几日
     *
     * @param dateString 长日期 如2015-01-02 10:20:30 返回 2
     * @return 第几日(1 - 31)
     */
    public static int getDay(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getDate();
    }

    /**
     * 根据字符串日期获取当前小时
     *
     * @param dateString 长日期 如2015-01-02 10:20:30 返回 10
     * @return 当前小时
     */
    public static int getHours(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getHours();
    }

    /**
     * 根据字符串日期获取当前分钟
     *
     * @param dateString 长日期 如2015-01-02 10:20:30 返回 20
     * @return 当前分钟
     */
    public static int getMinute(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getMinutes();
    }

    /**
     * 根据字符串日期获取当前秒
     *
     * @param dateString 长日期 如2015-01-02 10:20:30 返回 30
     * @return 当前秒
     */
    public static int getSecond(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getSeconds();
    }

    /**
     * 根据字符串日期获取它是周几
     *
     * @param dateString 长日期 如2015-01-02 10:20:30
     * @return 返回这个日期是周几（0-6：周日-周六）
     */
    public static int getWeek(String dateString) {
        Date date = formatDate(dateString);
        return date == null ? 0 : date.getDay();
    }

    /**
     * 获取当前秒（2位，不足补0）
     *
     * @return 当前秒（2位，不足补0）
     */
    public static String addZero() {
//	    System.out.println("========= 年：" + String.formatString("%ty%n", date)); // 当前年份（2位，不足补0）
//	    System.out.println("========= 月：" + String.formatString("%tm%n", date) ); // 当前月份（2位，不足补0）
//	    System.out.println("========= 日：" + String.formatString("%td%n", date) ); // 当前日（2位，不足补0）
//	    System.out.println("========= 时：" + String.formatString("%tk%n", date) ); // 当前时（2位，不足补0）
//	    System.out.println("========= 分：" + String.formatString("%tM%n", date) ); // 当前分（2位，不足补0）
//	    System.out.println("========= 秒：" + String.formatString("%tS%n", date) ); // 当前秒（2位，不足补0）
//	    System.out.println("========毫秒：" + String.formatString("%tL%n", date) ); // 当前毫秒（3位，不足补0）
        return String.format("%tS%n", new Date());
    }

    /**
     * 根据给定时间戳，获取该年总天数
     * @param timestamp 时间戳
     */
    public static int getDaysOfYear(long timestamp) {
        Date date = DateUtils.formatDate(timestamp);
        if (date != null) {
            int year = date.getYear() + 1900;//要判断的年份，比如2008
            int days;//某年(year)的天数
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {//闰年的判断规则
                days = 366;
            } else {
                days = 365;
            }
            return days;
        }
        return 0;
    }
}
