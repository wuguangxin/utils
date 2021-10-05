package com.wuguangxin.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 对TextView中的文字样式设置的工具类。
 *
 * @author Created by wuguangxin on 15/5/12
 */
public class FontUtils {
    /**
     * 设置指定String在TextView中的颜色，比如搜索结果高亮搜索关键字
     *
     * @param textView TextView
     * @param keyword  要高亮的文字
     * @param color    高亮颜色
     */
    public static void setFontColor(TextView textView, String keyword, int color) {
        setFontColor(textView, ArrayUtils.of(keyword), color);
    }

    /**
     * 设置指定String在TextView中的颜色，比如搜索结果高亮搜索关键字
     *
     * @param textView TextView
     * @param keywords 关键字数组
     * @param color    颜色
     */
    public static void setFontColor(TextView textView, String[] keywords, int color) {
        if (textView != null) {
            textView.setText(formatColor(textView.getText(), keywords, color));
        }
    }

    /**
     * 给指定的文字设置字体大小。
     *
     * @param view     TextView
     * @param text     文本
     * @param textSize 文本字体大小（单位dip）（-1不设置）
     */
    public static void setFontStyle(TextView view, String text, int textSize) {
        if (view != null) {
            view.setText(formatStyle(view.getText(), text, textSize, -1, -1, -1));
        }
    }

    /**
     * 设置文字样式。
     *
     * @param view      TextView
     * @param text      要改变的文本
     * @param textSize  文本字体大小（单位dip）（-1不设置）
     * @param textStyle 字体样式（如粗体 Typeface.BOLD）（-1不设置）
     */
    public static void setFontStyle(TextView view, String text, int textSize, int textStyle) {
        if (view != null) {
            view.setText(formatStyle(view.getText(), text, textSize, textStyle, -1, -1));
        }
    }

    /**
     * 设置文字样式。
     *
     * @param view      TextView
     * @param text      要改变的文本
     * @param textSize  文本字体大小（单位dip）（-1不设置）
     * @param textStyle 字体样式（如粗体 Typeface.BOLD）（-1不设置）
     * @param textColor 字体前景颜色（-1不设置）
     */
    public static void setFontStyle(TextView view, String text, int textSize, int textStyle, int textColor) {
        if (view != null) {
            view.setText(formatStyle(view.getText(), text, textSize, textStyle, textColor, -1));
        }
    }

    /**
     * 设置文字样式。可指定字体的前景色和背景色、粗体等。
     *
     * @param view        TextView
     * @param text        要改变的文本
     * @param textSize    文本字体大小（单位dip）（-1不设置）
     * @param textStyle   字体样式（如粗体 Typeface.BOLD）（-1不设置）
     * @param textColor   字体前景颜色（-1不设置）
     * @param textBgColor 字体背景颜色（-1不设置）
     */
    public static void setFontStyle(TextView view, String text, int textSize, int textStyle, int textColor, int textBgColor) {
        if (view != null) {
            view.setText(formatStyle(view.getText(), text, textSize, textStyle, textColor, textBgColor));
        }
    }

    /**
     * 构建一个Spannable。
     *
     * @param string   TextView中的文本（CharSequence）
     * @param text     要格式化的文本
     * @param textSize 文本大小（-1不设置）
     * @return Spannable
     */
    public static Spannable formatStyle(CharSequence string, CharSequence text, int textSize) {
        return formatStyle(string, text, textSize, -1, -1, -1);
    }

    /**
     * 构建一个Spannable。
     *
     * @param string    TextView中的文本（CharSequence）
     * @param text      要格式化的文本
     * @param textSize  文本大小（-1不设置）
     * @param textStyle 文本粗体/斜体等样式（如粗体 Typeface.BOLD）（-1不设置）
     * @return Spannable
     */
    public static Spannable formatStyle(CharSequence string, CharSequence text, int textSize, int textStyle) {
        return formatStyle(string, text, textSize, textStyle, -1, -1);
    }

    /**
     * 构建一个Spannable。
     *
     * @param string    TextView中的文本（CharSequence）
     * @param text      要格式化的文本
     * @param textSize  文本大小（-1不设置）
     * @param textStyle 文本粗体/斜体等样式（如粗体 Typeface.BOLD）（-1不设置）
     * @param textColor 字体前景颜色（-1不设置）
     * @return Spannable
     */
    public static Spannable formatStyle(CharSequence string, CharSequence text, int textSize, int textStyle, int textColor) {
        return formatStyle(string, text, textSize, textStyle, textColor, -1);
    }

    /**
     * 构建一个Spannable。
     *
     * @param sourceText  TextView中的源文本（CharSequence）
     * @param keyword     要格式化的文本
     * @param textSize    文本大小（-1不设置）
     * @param textStyle   文本粗体/斜体等样式（如粗体 Typeface.BOLD）（-1不设置）
     * @param textColor   字体前景颜色（-1不设置）
     * @param textBgColor 字体背景颜色（-1不设置）
     * @return Spannable
     */
    public static Spannable formatStyle(CharSequence sourceText, CharSequence keyword, int textSize, int textStyle, int textColor, int textBgColor) {
        Spannable word = null;
        if (!TextUtils.isEmpty(sourceText) && !TextUtils.isEmpty(keyword)) {
            int start = sourceText.toString().indexOf(keyword.toString());
            if (start != -1) {
                if (!TextUtils.isEmpty(keyword)) {
                    int end = start + keyword.length();
                    word = new SpannableString(sourceText);
                    if (textSize != -1) {
                        word.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);        // 字体大小（dip）
                    }
                    if (textStyle != -1) {
                        word.setSpan(new StyleSpan(textStyle), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);                // 字体粗体样式 Typeface.BOLD
                    }
                    if (textColor != -1) {
                        word.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);    // 字体前景色 Color.BLUE
                    }
                    if (textBgColor != -1) {
                        word.setSpan(new BackgroundColorSpan(textBgColor), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);    // 字体背景色 Color.GRAY
                    }
                }
            }
        }
        return word;
    }

    /**
     * 给指定的 CharSequence 中的指定关键字设置颜色。
     *
     * @param text    字符串
     * @param keyword 关键字
     * @param color   要设置的颜色
     * @return Spannable
     */
    public static CharSequence formatColor(CharSequence sourceText, String keyword, int color) {
        return formatColor(sourceText, ArrayUtils.of(keyword), color);
    }

    /**
     * 给指定的 CharSequence 中的指定关键字设置颜色。
     *
     * @param sourceText 字符串
     * @param color      要设置的颜色
     * @param keywords   关键字数组
     * @return Spannable
     */
    public static CharSequence formatColor(CharSequence sourceText, String[] keywords, int color) {
        if (TextUtils.isEmpty(sourceText)) {
            return sourceText;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(sourceText);
        for (String keyword : keywords) {
            if (TextUtils.isEmpty(keyword)) {
                continue;
            }
            // m.group()    获取匹配后的结果；
            // m.register() 返回以前匹配的初始索引；
            // m.end()      返回最后匹配字符之后的偏移量
            Matcher m = Pattern.compile(keyword).matcher(sourceText);
            while (m.find()) {
                builder.setSpan(new ForegroundColorSpan(color), m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }
        }
        return builder;
    }

    /**
     * 设置文字字体。
     *
     * @param activity   Activity
     * @param viewId     View的ID
     * @param assetsPath 字体路径(assets中的)
     */
    public static void setTypeface(Activity activity, int viewId, String assetsPath) {
        View view = activity.findViewById(viewId);
        if (view instanceof TextView) {
            setTypeface(((TextView) view), assetsPath);
        }
    }

    /**
     * TextView设置字体
     *
     * @param textView   TextView
     * @param assetsPath assets下的目录，例如 "fonts/SIMYOU.TTF"
     */
    public static void setTypeface(TextView textView, String assetsPath) {
        if (textView != null && !TextUtils.isEmpty(assetsPath)) {
            //将字体文件保存在assets/fonts/目录下，创建Typeface对象
            Typeface ttf = Typeface.createFromAsset(textView.getContext().getAssets(), assetsPath);
            textView.setTypeface(ttf);
        }
    }

    /**
     * TextView设置字体
     *
     * @param activity Activity
     * @param viewId   资源ID
     * @param ttf      字体 Typeface
     */
    public static void setTypeface(Activity activity, int viewId, Typeface ttf) {
        View view = activity.findViewById(viewId);
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(ttf);
        }
    }

    /**
     * TextView设置字体
     *
     * @param textView TextView
     * @param ttf      字体 Typeface
     */
    public static void setTypeface(TextView textView, Typeface ttf) {
        if (ttf != null) {
            textView.setTypeface(ttf);
        }
    }

    /**
     * 格式化文本样式
     *
     * @param textView    TextView
     * @param textSize    文本大小（0不设置）
     * @param textStyle   文本粗体/斜体等样式（如粗体 Typeface.BOLD）（0不设置）
     * @param textColor   字体前景颜色（0不设置）
     * @param textBgColor 字体背景颜色（0不设置）
     * @return
     */
    public static void setStyle(TextView textView, int textSize, int textStyle, int textColor, int textBgColor) {
        if (textView == null || textView.getText() == null) return;
        Spannable word = null;
        CharSequence string = textView.getText();
        if (!TextUtils.isEmpty(string)) {
            int start = 0;
            int end = string.length();
            word = new SpannableString(string);
            if (textSize != 0) {
                word.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);        // 字体大小（dip）
            }
            if (textStyle != 0) {
                word.setSpan(new StyleSpan(textStyle), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);                // 字体粗体样式 Typeface.BOLD
            }
            if (textColor != 0) {
                word.setSpan(new ForegroundColorSpan(textColor), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);    // 字体前景色 Color.BLUE
            }
            if (textBgColor != 0) {
                word.setSpan(new BackgroundColorSpan(textBgColor), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);    // 字体背景色 Color.GRAY
            }
        }
        textView.setText(word);
    }

    /**
     * 设置文字粗体/斜体/正常等
     *
     * @param textView  TextView
     * @param textStyle 文本粗体/斜体等样式（如粗体 Typeface.BOLD）（0不设置）
     * @return
     */
    public static void setTextStyle(TextView textView, int textStyle) {
        setStyle(textView, 0, textStyle, 0, 0);
    }
}
