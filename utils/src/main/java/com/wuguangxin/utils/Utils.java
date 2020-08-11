package com.wuguangxin.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 *
 * <p>Created by wuguangxin on 14/4/14 </p>
 */
public class Utils {
    private static final String TAG = "Utils";

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * dip转换为px
     *
     * @param context  上下文
     * @param dipValue dip
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * dip转换为sp
     *
     * @param context 上下文
     * @param dpValue dip
     * @return
     */
    public static int dip2sp(Context context, float dpValue) {
        return (int) (dpValue * getDisplayMetrics(context).density);
    }

    /**
     * px转换为dip
     *
     * @param context 上下文
     * @param pxValue px
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / getDisplayMetrics(context).density + 0.5f);
    }

    /**
     * px转换为sp
     *
     * @param context 上下文
     * @param pxValue px数值
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / getDisplayMetrics(context).scaledDensity + 0.5f);
    }

    /**
     * mm转换为px(X轴)
     *
     * @param context 上下文
     * @param value   mm数值
     * @return
     */
    public static int mm2pxX(Context context, float value) {
        return (int) (value * getDisplayMetrics(context).xdpi * (1.0f / 25.4f));
    }

    /**
     * mm转换为px(Y轴)
     *
     * @param context 上下文
     * @param value   mm数值
     * @return
     */
    public static int mm2pxY(Context context, float value) {
        return (int) (value * getDisplayMetrics(context).ydpi * (1.0f / 25.4f));
    }

    /**
     * px转换为mm(X轴)
     *
     * @param context 上下文
     * @param value   px值
     * @return
     */
    public static int px2mmX(Context context, float value) {
        return (int) (value * (1 / mm2pxX(context, 1)));
    }

    /**
     * px转换为mm(Y轴)
     *
     * @param context 上下文
     * @param value   px值
     * @return
     */
    public static int px2mmY(Context context, float value) {
        return (int) (value * (1 / mm2pxY(context, 1)));
    }

    public static int applyDimension(Context context, int unit, float value) {
        return (int) TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取两个点之间的距离
     *
     * @param event MotionEvent
     * @return 距离
     */
    public static float getDistance(MotionEvent event) {
        float a = event.getX(1) - event.getX(0);
        float b = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(a * a + b * b);    // 平方根(勾股定理)
    }

    /**
     * 获取两个点的中间点坐标， 第一个点的x坐标+ 上第二个点的x坐标 结果/2 就是他们的中心点， y坐标同理。
     *
     * @param event MotionEvent
     * @return 中间点坐标
     */
    public static PointF getPoint(MotionEvent event) {
        float x = (event.getX(0) + event.getX(1)) / 2;
        float y = (event.getY(0) + event.getY(1)) / 2;
        return new PointF(x, y);
    }

    /**
     * 把版本号转为数字。如1.2.5转换为010205，2.9.13转换为020913，再转换为int， 因为目前友盟的版本更新数据中并没有版本号,暂时这样做
     *
     * @param versionName 版本名称如1.2.5
     * @return 如1.2.5转换为010205
     */
    public static int versionName2Int(String versionName) {
        if (TextUtils.isEmpty(versionName)) {
            return 0;
        }
        String[] split = versionName.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String str : split) {
            if (str.length() == 1) {
                sb.append("0").append(str);
            } else if (str.length() == 2) {
                sb.append(str);
            }
        }
        int parseInt = 0;
        try {
            parseInt = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return parseInt;
    }

    /**
     * 返回文字的长度，一个汉字算2个字符
     *
     * @param string 字符串
     * @return
     */
    public static int getTextLength(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            return string.getBytes("GBK").length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 用*号替换名字，只显示第一个字（如："吴某人" = "吴**"，"吴某" = "吴*"）
     *
     * @param name 名字
     * @return 字符串
     */
    public static String replaceNameKeepFirst(String name) {
        if (!TextUtils.isEmpty(name)) {
            String encryptName = name.substring(1);
            StringBuilder nameStr = new StringBuilder(name.substring(0, 1));
            for (int i = 0; i < encryptName.length(); i++) {
                nameStr.append("*");
            }
            return nameStr.toString();
        }
        return name;
    }

    /**
     * 加密姓名，只显示最后一个字（如："吴某人" = "**人"，"吴某" = "*某"）
     *
     * @param name 名字
     * @return 字符串
     */
    public static String replaceNameKeepLast(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.length() < 2) {
                return name;
            }
            String beforeStr = name.substring(0, name.length() - 1);
            StringBuilder newName = new StringBuilder();
            for (int i = 0; i < beforeStr.length(); i++) {
                newName.append("*");
            }
            newName.append(name.substring(name.length() - 1));
            return newName.toString();
        }
        return null;
    }

    /**
     * 验证客户姓名：
     * "(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,15}))"
     * 中文2-7位，英文3-15位
     * @param name 名字
     * @return 是否匹配
     */
    public static boolean verifyUserName(String name) {
        String reg = "(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,15}))";
        return Pattern.matches(reg, name);
    }

    /**
     * 复制文本到剪贴板
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void copyString(Context context, String text) {
        if (Build.VERSION.SDK_INT <= 11) {
            // 得到剪贴板管理器
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(text);
        } else {
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) context.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(text);
        }
    }

    /**
     * 获取比某个数小的随机数
     *
     * @param bound
     * @return 随机数
     */
    public static int getRandom(int bound) {
        return new Random().nextInt(bound);
    }

    /**
     * 获取两个数之间的随机数（包含最小和最大数）
     *
     * @param min 最小的数
     * @param max 最大的数
     * @return 随机数
     */
    public static int getRandom(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    /**
     * 获取两个数之间的随机数（包含最小和最大数，并补位为最大长度）
     *
     * @param min 最小的数
     * @param max 最大的数
     * @return 例如获取0-99999 的随机数，随机到的数字是456，则返回00456
     */
    public static String getRandomString(int min, int max) { //0-99999
        int num = getRandom(min, max); // 6666
        int len = String.valueOf(max).length(); //5
        return String.format(Locale.getDefault(),"%0"+len+"d", num);
    }

    /**
     * 获取数组中的随机一个对象
     *
     * @param arr 数组
     * @return Object
     */
    public static <T> T getRandom(T[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        int random = getRandom(arr.length + 1);
        if (random > 0) {
            random -= 1;
        }
        return arr[random];
    }

    /**
     * 获取List中的随机一个对象
     *
     * @param list 列表
     * @return Object
     */
    public static <T> T getRandom(List<T> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int random = getRandom(list.size() + 1);
        if (random > 0) {
            random -= 1;
        }
        return list.get(random);
    }

    /**
     * 从系统默认的浏览器打开网页
     *
     * @param context 上下文
     * @param url     地址
     */
    public static void openWebFromSystem(Context context, String url) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    /**
     * 是否包含Emoji表情
     *
     * @param string 文本
     * @return 是否包含
     */
    public static boolean containEmoji(String string) {
        String reg = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        Pattern pattern = Pattern.compile(reg, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * 判断是否含有中文字符
     *
     * @param string 文本
     * @return 是否包含
     */
    public static boolean containCNString(String string) {
        for (int i = 0; i < string.length(); i++) {
            int c = string.charAt(i);
            if ((19968 <= c && c < 40623)) {
                return true;
            }
        }
        return false;
    }
}