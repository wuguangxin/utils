package com.wuguangxin.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wuguangxin.R;

import java.lang.ref.WeakReference;

/**
 * Toast工具类。
 * Created by wuguangxin on 14/11/28
 */
public class ToastUtils {
    private static final String TAG = "ToastUtils";
    private static WeakReference<Toast> mSystemToast, mCustomToast;

    public static void showToast(Context context, CharSequence text) {
        showToast(context, getCustomToast(context), text);
    }

    public static void showToastFromSystem(Context context, CharSequence text) {
        showToast(context, getSystemToast(context), text);
    }

    private static void showToast(Context context, Toast toast, CharSequence text) {
        if (context != null && toast != null && !isDestroyed(context)) {
            int duration = getDuration(text);
            toast.setDuration(duration);
            toast.setText(text);
            toast.show();
        } else {
            Log.i(TAG, "showToast() \ntoast="+toast + "\ntext="+text);
        }
    }

    private static Toast getCustomToast(Context context) {
        Toast toast = null;
        if (mCustomToast != null) {
            toast = mCustomToast.get();
        }
        if (toast == null && context != null) {
            toast = new CustomToast(context);
            mCustomToast = new WeakReference<>(toast);
        }
        return toast;
    }

    private static Toast getSystemToast(Context context) {
        Toast toast = null;
        if (mSystemToast != null) {
            Log.i(TAG, "getSystemToast() by cache");
            toast = mSystemToast.get();
        }
        if (toast == null && context != null) {
            Log.i(TAG, "getSystemToast() by create");
            toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
            mSystemToast = new WeakReference<>(toast);
        }
        return toast;
    }

    // 根据字符长度获取显示时长
    private static int getDuration(CharSequence msg) {
        int len = msg == null ? 0 : msg.length();
        return len < 15 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
    }

    // activity 是否已销毁
    private static boolean isDestroyed(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            if (context instanceof Activity) {
                return ((Activity) context).isDestroyed();
            }
        }
        return false;
    }

    // 自定义Toast布局
    static class CustomToast extends Toast {
        TextView msgView;

        CustomToast(Context context) {
            super(context);
            View rootView = LayoutInflater.from(context).inflate(R.layout.xin_toast_warn_layout, null);
            msgView = rootView.findViewById(R.id.xin_toast_text);
            setView(rootView);
            setGravity(Gravity.TOP, 0, 250);
        }

        @Override
        public void setText(int resId) {
            msgView.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            msgView.setText(s);
        }
    }
}
