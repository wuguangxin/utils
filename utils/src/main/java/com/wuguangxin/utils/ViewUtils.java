package com.wuguangxin.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.viewpager.widget.ViewPager;

/**
 * View工具类
 * <p>Created by wuguangxin on 14/10/23 </p>
 */
public class ViewUtils {
    /**
     * 判断View是否是可见的
     *
     * @param view View
     * @return 是否是可见的
     */
    public static boolean isVisible(View view) {
        return view != null && view.getVisibility() == View.VISIBLE;
    }

    /**
     * 获取View的宽度
     *
     * @param view View
     * @return 宽度
     */
    public static int getViewWidth(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return view.getMeasuredWidth();
    }

    /**
     * 获取View的高度
     *
     * @param view View
     * @return 高度
     */
    public static int getViewHeight(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }

    /**
     * 重置 ViewPager 的宽高度 (宽为屏幕宽度，高度为 屏幕宽/2)
     *
     * @param viewPager ViewPager
     */
    public static void setViewPagerHeight(ViewPager viewPager) {
        LayoutParams layoutParams = viewPager.getLayoutParams();
        WindowManager wm = (WindowManager) viewPager.getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        layoutParams.height = dm.widthPixels >> 1;
        viewPager.setLayoutParams(layoutParams);
    }

    /**
     * 重置 ViewPager 的宽高度
     *
     * @param context 上下文
     * @param viewPager ViewPager
     * @param height 高
     */
    public static void setViewPagerHeight(Context context, ViewPager viewPager, int height) {
        LayoutParams layoutParams = viewPager.getLayoutParams();
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        layoutParams.height = height;
        viewPager.setLayoutParams(layoutParams);
    }

    /**
     * 重置 GridView 的高度,使高度等于Child 总数的高度
     *
     * @param gridView GridView
     */
//	@SuppressLint("NewApi")
    public static void setGridViewHeight(GridView gridView) {
        if (gridView == null) {
            return;
        }
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) {
            return;
        }
        int itemCount = adapter.getCount();
        int colCount = gridView.getNumColumns();
        int totalHeight = 0;
        int lineCount = itemCount / colCount + itemCount % colCount; // 计算行数
        for (int i = 0, len = lineCount; i < len; i++) {
            View listItem = adapter.getView(i, null, gridView);
            listItem.measure(0, 0); //计算子项View 的宽高
            int lineHeight = 0;
            lineHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
            lineHeight += listItem.getPaddingTop();
            lineHeight += listItem.getPaddingBottom();
            totalHeight += lineHeight;
            for (int j = 0; j < colCount; j++) {
                if (i * colCount + j < itemCount) {
                    Logger.e("WGX", String.format("setGridViewHeight item getId=%s", listItem.getId()));
                    Logger.e("WGX", String.format("setGridViewHeight item getHeight=%s", listItem.getHeight()));
                    Logger.e("WGX", String.format("setGridViewHeight item getMeasuredHeight=%s", listItem.getMeasuredHeight()));
                    listItem.setMinimumHeight(lineHeight);
                    listItem.invalidate();
                }
            }
            Logger.e("WGX", String.format("setGridViewHeight 第%s行高：%s", i, lineHeight));
        }

        Logger.e("WGX", "setGridViewHeight totalHeight=" + totalHeight);
        LayoutParams params = gridView.getLayoutParams();
        int verticalSpacing = 0;  // 垂直的间隔高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            verticalSpacing = gridView.getVerticalSpacing();
        }
        params.height = totalHeight + (verticalSpacing * (lineCount - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    public static void setGridViewHeightByItem(GridView gridView) {
        if (gridView == null) {
            return;
        }
        int childCount = gridView.getChildCount();
        int colCount = gridView.getNumColumns();                 // 总列数
        int totalHeight = 0;                                     // 总高度
        Logger.e("WGX", "childCount=" + childCount);
        Logger.e("WGX", "colCount=" + colCount);
        ArrayList<View> listView = new ArrayList<>();
        int lastLine = 0;
        int curLineMaxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            int curLine = i / colCount; // 当前在第几行
            View itemView = gridView.getChildAt(i);
            if (itemView != null) {
                int measuredHeight = itemView.getMeasuredHeight();
                if (curLineMaxHeight < measuredHeight) {
                    curLineMaxHeight = measuredHeight;
                }
                Logger.i("wgxin", String.format("%s 第%s-%s高度为%s", i, curLine, i % colCount, curLineMaxHeight));
                listView.add(itemView);
            }
            if (listView.size() == childCount || curLine > lastLine) {
                for (int j = 0; j < listView.size(); j++) {
                    int currentPosition = i - j;
                    if (currentPosition < childCount) {
                        Logger.e("wgxin", (String.format("修改item%s，%s-%s高度为%s", currentPosition, curLine, j, curLineMaxHeight)));
                        gridView.getChildAt(currentPosition).setMinimumHeight(curLineMaxHeight);
                    }
                }
                totalHeight += curLineMaxHeight;
                lastLine = curLine;
                curLineMaxHeight = 0;
                listView.clear();
            }
        }
        gridView.setMinimumHeight(totalHeight);

        Logger.e("WGX", "totalHeight=" + totalHeight);
    }

//    E/wgx_WGXIN: creditMaterialInfo.getCreditInfoList() = 9
//    I/wgx_wgxin: 0 第0行，第0列 高度91
//    I/wgx_wgxin: 1 第0行，第1列 高度133
//    I/wgx_wgxin: 2 第1行，第0列 高度133
//    E/wgx_wgxin: 修改第2(1-0) 高度为133
//    E/wgx_wgxin: 修改第1(1-0) 高度为133
//    E/wgx_wgxin: 修改第0(1-0) 高度为133
//    I/wgx_wgxin: 3 第1行，第1列 高度91
//    I/wgx_wgxin: 4 第2行，第0列 高度133
//    E/wgx_wgxin: 修改第4(2-0) 高度为133
//    E/wgx_wgxin: 修改第3(2-0) 高度为133
//    I/wgx_wgxin: 5 第2行，第1列 高度133
//    I/wgx_wgxin: 6 第3行，第0列 高度133
//    E/wgx_wgxin: 修改第6(3-0) 高度为133
//    E/wgx_wgxin: 修改第5(3-0) 高度为133
//    I/wgx_wgxin: 7 第3行，第1列 高度91
//    I/wgx_wgxin: 8 第4行，第0列 高度175
//    E/wgx_wgxin: 修改第8(4-0) 高度为175
//    E/wgx_wgxin: 修改第7(4-0) 高度为175
//    E/wgx_WGXIN: creditMaterialInfo.getCreditInfoList() = 9


    /**
     * 重置 ViewPager 的高度,使高度等于Child 总数的高度(有bug)
     *
     * @param viewPager ViewPager
     * @param position 位置
     */
    @SuppressLint("NewApi")
    public static void setViewPagerChildCountHeight(ViewPager viewPager, int position) {
        if (viewPager != null) {
            View childView = viewPager.getChildAt(position);
            if (childView != null) {
                int h;
                LayoutParams params = viewPager.getLayoutParams();
                h = childView.getMeasuredHeight();
                h += viewPager.getPaddingTop();
                h += viewPager.getPaddingBottom();
                params.height = h;
                viewPager.setLayoutParams(params);
            }
        }
    }

    /**
     * 重置 ViewPager 的高度,使高度等于Child 总数的高度
     *
     * @param viewGroup ViewGroup
     */
    public static void setViewGroupChildTotalHeight(ViewGroup viewGroup) {
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            if (childCount > 0) {
                int totalHeight = 0;
                for (int i = 0; i < childCount; i++) {
                    View child = viewGroup.getChildAt(i);
                    if (child != null) {
                        totalHeight += child.getMeasuredHeight();
                        totalHeight += viewGroup.getPaddingTop();
                        totalHeight += viewGroup.getPaddingBottom();
                    }
                }
                LayoutParams params = viewGroup.getLayoutParams();
                params.height = totalHeight;
                viewGroup.setLayoutParams(params);
            }
        }
    }

    /**
     * 重置 ViewPager 的高度,为最高child的高度
     *
     * @param parentView ViewGroup
     */
    public static void setViewPagerHeightByMaxChild(ViewGroup parentView) {
        if (parentView != null) {
            int childCount = parentView.getChildCount();
            if (childCount > 0) {
                int maxHeight = 0;
                for (int i = 0; i < childCount; i++) {
                    View child = parentView.getChildAt(i);
                    if (child != null) {
                        int childHeight = child.getMeasuredHeight();
                        if (childHeight > maxHeight) {
                            maxHeight = childHeight;
                        }
                    }
                }
                maxHeight += parentView.getPaddingTop();
                maxHeight += parentView.getPaddingBottom();
                LayoutParams params = parentView.getLayoutParams();
                params.height = maxHeight;
                parentView.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置View的对齐方式。(view与anchor对齐方式为verb)
     *
     * @param view 哪个View需要设置对齐
     * @param verb 对齐方式（如：RelativeLayout.BELOW，view在id为verb的View的下方）
     * @param anchor 对齐哪个View的id
     */
    public static void setViewAlign(View view, int verb, int anchor) {
        if (view != null && verb != -1) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            if (params != null) {
                params.addRule(verb, anchor); // anchor为要对齐的View的id
                view.setLayoutParams(params);
            }
        }
    }

    /**
     * 设置EditText内容的显示或隐藏状态
     *
     * @param checkBox CheckBox
     * @param editText EditText
     */
    public static void setEditTextVisibleStatus(CheckBox checkBox, final EditText editText) {
        if (checkBox != null && editText != null) {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                private TransformationMethod visibleMethod = HideReturnsTransformationMethod.getInstance();
                private PasswordTransformationMethod goneMethod = PasswordTransformationMethod.getInstance();

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 如果选中，显示密码, 否则隐藏密码
                    editText.setTransformationMethod(isChecked ? visibleMethod : goneMethod);
                    Utils.setFocusPosition(editText);
                }
            });
        }
    }

    /**
     * 垂直图像的宽高，以宽在屏幕的尺寸为参考值等比例缩放高度
     *
     * @param view 显示图像的View
     * @param width 原图像的宽
     * @param height 原图像的高
     */
    public static void setRealSize(View view, int width, int height) {
        if (view != null) {
            int realWidth = view.getLayoutParams().width;
            view.getLayoutParams().height = realWidth * height / width;
        }
    }

    /**
     * 判断指定坐标是否在圆内
     *
     * @param curX 当前触摸的X轴
     * @param curY 当前触摸的Y轴
     * @param circleX 圆心X轴
     * @param circleY 圆心Y轴
     * @param circleRadius 圆半径
     * @return
     */
    public static boolean withinCircle(float curX, float curY, float circleX, float circleY, float circleRadius) {
        Point point1 = new Point((int) curX, (int) curY);
        Point point2 = new Point((int) circleX, (int) circleY);
        double distance = getInterval(point1, point2); // 两个坐标距离
        return distance < circleRadius;
    }

    /**
     * 判断指定坐标是否在圆内
     *
     * @param curPoint 当前坐标
     * @param circlePoint 圆心坐标
     * @param circleRadius 圆半径
     * @return
     */
    public static boolean withinCircle(Point curPoint, Point circlePoint, float circleRadius) {
        double distance = getInterval(curPoint, circlePoint); // 两个坐标距离
        return distance < circleRadius;
    }

    /**
     * 获取两个坐标的距离
     *
     * @param point1 坐标1
     * @param point2 坐标2
     * @return
     */
    public static double getInterval(Point point1, Point point2) {
        if (point1 == null || point2 == null) {
            return 0F;
        }
        // 开方
        double distance = Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
        return distance;
    }

    /**
     * 计算LayoutParams的leftMargin值
     *
     * @param menuCountWidth 全部menu所占的宽度
     * @param menuCount menu数量
     * @param nextPosition 将要获取焦点的menu位置
     * @param currPosition 当前menu位置
     * @param offset 偏移量
     * @return
     */
    public static int getLeftMargin(int menuCountWidth, int menuCount, int nextPosition, int currPosition, float offset) {
        int width = menuCountWidth / menuCount; // 计算每个menu的宽度
        int curLeft = nextPosition * width; // 计算当前距左的距离
        for (int i = 0; i < menuCount; i++) {
            if (nextPosition == i && currPosition == i) { // 左 > 右 滑
                return (int) (offset * width + curLeft);
            } else if (nextPosition == i + 1 && currPosition == i) { // 右 > 左 滑
                return (int) (-(1 - offset) * width + curLeft);
            }
        }
        return 0;
    }

    /**
     * 设置 imageView 的 LeftMargin
     *
     * @param view
     * @param menuCountWidth 全部menu所占的宽度
     * @param menuCount menu数量
     * @param currPosition 当前menu位置
     * @param nextPosition 下一个menu位置
     * @param offset 偏移量
     */
    public static void setLeftMargin(ImageView view, int menuCountWidth, int menuCount, int currPosition, int nextPosition, float offset) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = getLeftMargin(menuCountWidth, menuCount, currPosition, nextPosition, offset);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 给 view 设置 左上右下 的 drawable
     *
     * @param view View
     * @param left 左
     * @param top 上
     * @param right 右
     * @param bottom 下
     */
    public static void setCompoundDrawables(TextView view,
                                            @DrawableRes int left,
                                            @DrawableRes int top,
                                            @DrawableRes int right,
                                            @DrawableRes int bottom) {
        if (view == null) return;
        Resources res = view.getResources();
        Drawable drawableLeft = null, drawableTop = null, drawableRight = null, drawableBottom = null;
        if (res != null) {
            if (left != 0) drawableLeft = res.getDrawable(left);
            if (top != 0) drawableTop = res.getDrawable(top);
            if (right != 0) drawableRight = res.getDrawable(right);
            if (bottom != 0) drawableBottom = res.getDrawable(bottom);
        }
        ViewUtils.setCompoundDrawables(view, drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    /**
     * 给 view 设置 左上右下 的 drawable
     *
     * @param view View
     * @param drawableLeft 左
     * @param drawableTop 上
     * @param drawableRight 右
     * @param drawableBottom 下
     */
    public static void setCompoundDrawables(TextView view,
                                            Drawable drawableLeft,
                                            Drawable drawableTop,
                                            Drawable drawableRight,
                                            Drawable drawableBottom) {
        if (view == null) return;
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getIntrinsicWidth(), drawableLeft.getIntrinsicHeight());
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableTop.getIntrinsicWidth(), drawableTop.getIntrinsicHeight());
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getIntrinsicWidth(), drawableRight.getIntrinsicHeight());
        }
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());
        }
        view.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

    /**
     * 反转View的显示状态，如果是隐藏，则显示，如果是显示，则隐藏
     *
     * @param view View
     * @return 返回反转后是显示还是隐藏
     */
    public static boolean toggleViewVisibility(View view) {
        if (view != null) {
            setVisibility(view, view.getVisibility() != View.VISIBLE);
            return view.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    /**
     * 设置View的显示状态
     *
     * @param view View
     * @param visible 是否可见
     */
    public static void setVisibility(View view, boolean visible) {
        if (view != null) {
            if (visible) {
                if (view.getVisibility() != View.VISIBLE) view.setVisibility(View.VISIBLE);
            } else {
                if (view.getVisibility() != View.GONE) view.setVisibility(View.GONE);
            }
        }
    }

    /**
     * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
     * <pre>
     * if (convertView == null) {
     *     convertView = View.inflate(context, R.layout.item_layout, null);
     * }
     * TextView textView = ViewUtils.get(convertView, R.id.text_view);
     * ImageView imageView = ViewUtils.get(convertView, R.id.image_view);
     * </pre>
     */
    public static <T extends View> T get(View convertView, @IdRes int viewId) {
        if (convertView == null) return null;
        SparseArray<View> sparseArray = (SparseArray<View>) convertView.getTag();
        if (sparseArray == null) {
            sparseArray = new SparseArray<>();
            convertView.setTag(sparseArray);
        }
        View childView = sparseArray.get(viewId);
        if (childView == null) {
            childView = convertView.findViewById(viewId);
            sparseArray.put(viewId, childView);
        }
        return (T) childView;
    }

    /**
     * 替代 findViewById() 方法
     */
    public static <T extends View> T find(View view, @IdRes int viewId) {
        return (T) view.findViewById(viewId);
    }

    public static String getText(EditText editText) {
        return getText(editText, null);
    }

    public static String getText(EditText editText, String defText) {
        if (editText != null) {
            Editable editable = editText.getText();
            if (editable != null) {
                return editable.toString().trim();
            }
        }
        return defText;
    }

    /**
     * 设置 EditText 的 android:maxLength 值
     * @param editText
     * @param maxLength
     */
    public static void setMaxLength(EditText editText, int maxLength) {
        if (editText != null && maxLength > 0) {
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
        }
    }

    /**
     * 获取设置的最大长度
     *
     * @return
     */
    public static int getMaxLength(TextView textView) {
        int length = 0;
        try {
            InputFilter[] inputFilters = textView.getFilters();
            for (InputFilter filter : inputFilters) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (filter instanceof InputFilter.LengthFilter) {
                        length = ((InputFilter.LengthFilter) filter).getMax();
                        break;
                    }
                } else {
                    //  反射
                    Class<?> c = filter.getClass();
                    if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                        Field field = c.getDeclaredField("mMax");
                        field.setAccessible(true);
                        length = (Integer) field.get(filter);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 设置文本到TextView中
     *
     * @param textView extends of TextView
     * @param stringResId 文本资源ID
     */
    public void setText(TextView textView, @StringRes int stringResId) {
        if (textView != null) {
            CharSequence text = textView.getContext().getResources().getText(stringResId);
            this.setText(textView, text, null);
        }
    }

    /**
     * 设置文本到TextView中
     *
     * @param textView extends of TextView
     * @param text 文本
     */
    public void setText(TextView textView, CharSequence text) {
        this.setText(textView, text, "");
    }

    /**
     * 设置文本到TextView中
     *
     * @param textView extends of TextView
     * @param text 文本
     * @param defText 如果text为null，则使用defText
     */
    public void setText(TextView textView, CharSequence text, CharSequence defText) {
        if (textView != null) {
            textView.setText(TextUtils.isEmpty(text) ? defText : text);
        }
    }

    /**
     * 定位光标位置到EditText文本的末尾
     *
     * @param mEditText EditText
     */
    public static void setFocusPosition(EditText mEditText) {
        if (mEditText == null) {
            return;
        }
        mEditText.requestFocus();
        Editable text = mEditText.getText();
        Selection.setSelection(text, text.length());
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void setSoftInputVisible(Activity activity) {
        if (activity != null) {
            View currentFocusView = activity.getCurrentFocus();
            if (currentFocusView != null) {
                ((InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(currentFocusView.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
            if (currentFocusView instanceof EditText) {
                Utils.setFocusPosition((EditText) currentFocusView);
            }
        }
    }
}
