package com.wuguangxin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * 权限管理工具类
 * Created by wuguangxin on 2018/8/10.
 */
public class PermissionUtils {

    /**
     * 检查权限是否获取
     *
     * @param context 上下文
     * @param permission 权限
     * @return 是否已获取
     */
    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;

//        boolean result = false;
//        if (VERSION.SDK_INT >= 23) {
//            try {
//                Class<?> clazz = Class.forName("android.content.Context");
//                Method method = clazz.getMethod("checkSelfPermission", String.class);
//                result = (int) method.invoke(context, permission) == PackageManager.PERMISSION_GRANTED;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            PackageManager pm = context.getPackageManager();
//            result = pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
//        }
//        return result;
    }

    /**
     * 判断是否已经获取该权限
     *
     * @param context 上下文
     * @param permissions 权限集
     * @return 如果其中某个权限为获取，返回false，否则返回true
     */
    public static boolean checkPermission(Context context, String... permissions) {
        if (context == null || permissions == null) return true;
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != packageManager.checkPermission(permission, packageName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测未授权的权限并返回
     *
     * @param context
     * @param permissions 权限集
     * @return
     */
    public static String[] checkUnAcceptPermission(Context context, String... permissions) {
        ArrayList<String> list = new ArrayList<>();
        if (permissions != null) {
            for (String permission : permissions) {
                if (!checkPermission(context, permission)) {
                    list.add(permission);
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[0]);
    }

    /**
     * 请求权限。（直接搬用系统的： {@link ActivityCompat#requestPermissions(Activity, String[], int)}）
     *
     * @param activity Activity
     * @param permissions 权限集
     * @param requestCode 请求码
     */
    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
}
