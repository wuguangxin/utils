package com.wuguangxin.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * 对话框工具类。
 * Created by wuguangxin on 14/9/11
 */
public class DialogUtils {

    /**
     * 弹出普通对话框
     *
     * @param context 上下文
     * @param title 对话框标题
     * @param message 对话框内容
     * @param positiveIntent 点击确定后的意图
     * @param negativeIntent 点击取消后的意图
     */
    public static AlertDialog createDialog(final Context context, String title, String message, final Intent positiveIntent, final Intent negativeIntent) {
        Builder builder = new Builder(context);
        builder.setTitle(title != null ? title : "提示");
        if (message != null) {
            builder.setMessage(message);
        }
        if (positiveIntent != null) {
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    context.startActivity(positiveIntent);
                }
            });
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (negativeIntent != null) {
                    context.startActivity(negativeIntent);
                }
            }
        });
        return builder.create();
    }

    /**
     * 弹出普通对话框
     *
     * @param context 上下文
     * @param title 对话框标题
     * @param message 对话框内容
     * @param negativeIntent 点击取消后的意图
     * @param positiveIntent 点击确定后的意图
     */
    public static AlertDialog showDialog(final Context context, String title, String message, final Intent negativeIntent, final Intent positiveIntent) {
        AlertDialog dialog = createDialog(context, title, message, negativeIntent, positiveIntent);
		if (dialog != null) {
			dialog.show();
		}
        return dialog;
    }

    /**
     * 设置对话框显示位置、宽、透明度
     *
     * @param dialog Dialog对话框
     * @param gravity 要显示在什么位置，左上右下（Gravity取值）
     * @param width 宽（android.view.ViewGroup.LayoutParams 取值）
     * @param alpha 透明度0.0-1.0之间float值
     */
    public static void setDialog(Dialog dialog, int gravity, int width, float alpha) {
        if (dialog == null) return;
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(gravity);
            // window.setWindowAnimations(R.style.xin_dialog_from_bottom_in_out);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            lp.alpha = alpha;
            window.setAttributes(lp);
        }
    }

    /**
     * 点击对话框按钮后对话框不关闭
     *
     * @param dialog
     */
    public static void keepDialog(DialogInterface dialog) {
        try {
            if (dialog == null) return;
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击对话框按钮后对话框可关闭
     *
     * @param dialog
     */
    public static void closeDialog(DialogInterface dialog) {
        try {
            if (dialog == null) return;
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建dialog 并显示
     *
     * @param context 上下文
     * @return Dialog
     */
    public static Dialog createLoadingDialog(Context context) {
        if (context != null) {
            ProgressDialog pd = new ProgressDialog(context);
            pd.setTitle("提示");
            pd.setMessage("请稍后..");
            pd.show();
            return pd;
        }
        return null;
    }

    public static Dialog showLoadingDialog(Context context) {
        Dialog loadingDialog = createLoadingDialog(context);
        if (loadingDialog != null) {
            loadingDialog.show();
        }
        return loadingDialog;
    }

}