package com.wuguangxin.utils;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

/**
 * 正式版日期  版本号               SDK 英文名称             中文名称
 * int SDK0 = 0;                           			    // 2008-09-22 Android Beta	    	Beta                阿童木
 * int SDK1 = VERSION_CODES.BASE; 					    // 2008-09-22 Android 1.0	    1	BASE                发条机器人
 * int SDK2 = VERSION_CODES.BASE_1_1; 				    // 2009------ Android 1.1	    2	BASE                发条机器人
 * int SDK3 = VERSION_CODES.CUPCAKE; 				    // 2009-04-30 Android 1.5	    3	CUPCAKE             纸杯蛋糕
 * int SDK4 = VERSION_CODES.DONUT; 					    // 2009-09-15 Android 1.6	    4	DONUT               甜甜圈
 * int SDK5 = VERSION_CODES.ECLAIR; 					// 2009-10-26 Android 2.0	    5	ECLAIR              闪电泡芙、法式奶油夹心甜点、松饼
 * int SDK6 = VERSION_CODES.ECLAIR_0_1; 				// 2009-12-03 Android 2.0.1     6 	ECLAIR              闪电泡芙、法式奶油夹心甜点、松饼
 * int SDK7 = VERSION_CODES.ECLAIR_MR1; 				// 2010-01-10 Android 2.1	    7	ECLAIR              闪电泡芙、法式奶油夹心甜点、松饼
 * int SDK8 = VERSION_CODES.FROYO; 					    // 2010-05-20 Android 2.2	    8	FROYO               冻酸奶
 * int SDK9 = VERSION_CODES.GINGERBREAD;				// 2010-12-07 Android 2.3	    9	GINGERBREAD         姜饼
 * int SDK10 = VERSION_CODES.GINGERBREAD_MR1;		    // 2011------ Android 2.3.3     10	GINGERBREAD         姜饼
 * int SDK11 = VERSION_CODES.HONEYCOMB;				    // 2011-02-03 Android 3.0	    11	HONEYCOMB           蜂巢
 * int SDK12 = VERSION_CODES.HONEYCOMB_MR1;			    // 2011-05-11 Android 3.1	    12	HONEYCOMB           蜂巢
 * int SDK13 = VERSION_CODES.HONEYCOMB_MR2;			    // 2011-07-13 Android 3.2	    13	HONEYCOMB           蜂巢
 * int SDK14 = VERSION_CODES.ICE_CREAM_SANDWICH;		// 2011-10-19 Android 4.0 	    14	ICE_CREAM_SANDWICH  冰激凌三明治、冰淇淋三明治
 * int SDK15 = VERSION_CODES.ICE_CREAM_SANDWICH_MR1;	// 2011-12-17 Android 4.0.3     15	ICE_CREAM_SANDWICH  冰激凌三明治、冰淇淋三明治
 * int SDK16 = VERSION_CODES.JELLY_BEAN;				// 2012-06-28 Android 4.1	    16	JELLY_BEAN          果冻豆
 * int SDK17 = VERSION_CODES.JELLY_BEAN_MR1;			// 2012-10-30 Android 4.2 	    17	JELLY_BEAN          果冻豆
 * int SDK18 = VERSION_CODES.JELLY_BEAN_MR2;			// 2013-07-25 Android 4.3 	    18	JELLY_BEAN          果冻豆
 * int SDK19 = VERSION_CODES.KITKAT;					// 2013-09-04 Android 4.4 	    19	KITKAT              奇巧巧克力棒
 * int SDK20 = VERSION_CODES.KITKAT_WATCH;			    // 2013-09-04 Android 4.4W 	    20	KITKAT watches      奇巧巧克力棒
 * int SDK21 = VERSION_CODES.LOLLIPOP;				    // 2014-10-15 Android 5.0	    21	LOLLIPOP            棒棒糖
 * int SDK22 = VERSION_CODES.LOLLIPOP_MR1;			    // 2015-03-10 Android 5.1	    22	Lollipop            棒棒糖
 * int SDK23 = VERSION_CODES.M;						    // 2015-05-25 Android 6.0	    23	Marshmallow         棉花糖
 * int SDK24 = VERSION_CODES.N;						    // 2016-08-22 Android 7.0 	    24  Nougat              牛轧糖
 * int SDK25 = VERSION_CODES.N_MR1;					    // 2016-12-05 Android 7.1.1	    25  Nougat              牛轧糖
 * int SDK26 = VERSION_CODES.O;						    // 2017-08-22 Android 8.0	    26  Oreo                奥利奥
 * int SDK27 = VERSION_CODES.O_MR1;					    // 2017-12-05 Android 8.1	    27  Oreo                奥利奥
 * int SDK28 = VERSION_CODES.P;						    // 2018-08-07 Android 9.0       28  Pre                 派
 * int SDK29 = VERSION_CODES.Q;						    // 2019------ Android 10 	    29  Q
 * update by:2019-12-30
 */

/**
 * Android系统相关的一些工具方法。
 * Created by wuguangxin on 15/6/14
 */
public class AndroidUtils {
    /**
     * 获取SDK版本号
     *
     * @return SDK版本号
     */
    public static int getSdkVersion() {
        return VERSION.SDK_INT;
    }

    /**
     * 获取手机版本号
     *
     * @return 手机版本号
     */
    public static String getRelease() {
        return VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取应用程序包名
     *
     * @return 应用程序包名
     */
    public static String getPackageName(Context context) {
        return context == null ? null : context.getPackageName();
    }

    /**
     * 获取 ActivityManager
     *
     * @param context
     * @return
     */
    public static ActivityManager getActivityManager(Context context) {
        return context == null ? null : (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 获取正在运行的进程信息列表
     *
     * @return 正在运行的进程信息列表
     */
    public static List<RunningAppProcessInfo> getRunningProcessList(Context context) {
        ActivityManager manager = getActivityManager(context);
        return manager == null ? null : manager.getRunningAppProcesses();
    }

    /**
     * 获取正在运行的进程数量
     *
     * @return 正在运行的进程数量
     */
    public static int getRunningProcessSize(Context context) {
        List<RunningAppProcessInfo> list = getRunningProcessList(context);
        return list == null ? 0 : list.size();
    }

    // *********************************************************************

    /**
     * 获取设备ID (唯一标识 IMEI)。
     * (6.0及以上系统需要动态请求权限 Manifest.permission.READ_PHONE_STATE)
     *
     * @param context 上下文
     * @return 设备ID
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId(Context context) {
        if (context == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 23) {
            if (!AndroidUtils.checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                Log.e(AndroidUtils.class.getSimpleName(), "权限拒绝：READ_PHONE_STATE");
                return null;
            }
        }
        try {
            TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                return tm.getDeviceId();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备IMEI，同 getDeviceId().
     *
     * @param context 上下文
     * @return 设备IMEI
     */
    public static String getIMEI(Context context) {
        return getDeviceId(context);
    }

    /**
     * 获取当前应用程序版本名称
     *
     * @return 当前应用程序版本名称。如 1.0.0
     */
    public static String getVersionName(Context context) {
        try {
            if (context != null) {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionName;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取SDK版本号
     *
     * @return SDK版本号
     */
    public static int getSDKCode() {
        return VERSION.SDK_INT;
    }

    /**
     * 判断当前设备是否是模拟器。如果是模拟器返回TRUE，不是返回FALSE
     *
     * @param context 上下文
     * @return 是否是模拟器
     */
    public static boolean isMobilePhone(Context context) {
        try {
            String device_id = getDeviceId(context);
            if (device_id != null && device_id.equals("000000000000000")) {
                return false;
            }
            return !((Build.MODEL.equals("sdk")) || (Build.MODEL.equals("google_sdk")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 根据WifiManager获取MAC地址。需要ACCESS_WIFI_STATE权限。(在6.0以上系统不再支持此种方式)
     *
     * @param context 上下文
     * @return MAC地址
     * @deprecated use getMac()
     */
    public static String getMac(Context context) {
        if (context != null) {
            WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (manager != null) {
                WifiInfo wifiInfo = manager.getConnectionInfo();
                if (wifiInfo != null) {
                    try {
                        return wifiInfo.getMacAddress();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;

    }

    /**
     * 通过网络接口取MAC地址
     *
     * @return MAC地址
     */
    public static String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本机号码，如果是双卡手机，获取的将是卡槽1的号码
     *
     * @param context 上下文
     * @return 本机号码，带有国家代码，如 "+86"
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getPhoneNumber(Context context) {
        if (context != null) {
            TelephonyManager manager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                return manager.getLine1Number();
            }
        }
        return null;
    }

    /**
     * 获取联系人电话
     * 参考 http://www.2cto.com/kf/201109/104686.html
     *
     * @param context 上下文
     * @param cursor cursor
     * @return 联系人电话
     */
    public static String getContactPhone(Context context, Cursor cursor) {
        if (context == null || cursor == null) {
            return null;
        }
        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            if (phones != null && phones.moveToFirst()) {
                // 遍历所有的电话号码
                for (; !phones.isAfterLast(); phones.moveToNext()) {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    phoneResult = phones.getString(index);
                }
                if (!phones.isClosed()) {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }

    /**
     * 得到屏幕宽度（px）
     *
     * @param context 上下文
     * @return
     */
    public static int getScreenWidth(@Nullable Context context) {
        return context == null ? 0 : context.getResources().getDisplayMetrics().widthPixels;
//        if (context != null) {
//            WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//            if (manager != null) {
//                DisplayMetrics display = new DisplayMetrics();
//                manager.getDefaultDisplay().getMetrics(display);
//                return display.widthPixels;
//            }
//        }
//        return 0;
    }

    /**
     * 得到屏幕高度（px）
     *
     * @param context 上下文
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context == null ? 0 : context.getResources().getDisplayMetrics().heightPixels;
//        if (context != null) {
//            WindowManager manager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//            if (manager != null) {
//                DisplayMetrics display = new DisplayMetrics();
//                manager.getDefaultDisplay().getMetrics(display);
//                return display.heightPixels;
//            }
//        }
//        return 0;
    }



    /**
     * 在Android 2.2版本之前，HttpURLConnection一直存在着一些令人厌烦的bug，
     * 比如说对一个可读的InputStream调用close()方法时，就有可能会导致连接池失效了，
     * 那么我们通常的解决办法就是直接禁用掉连接池的功能。
     */
    public static void disableConnectionReuseIfNecessary() {
        System.setProperty("http.keepAlive", "false");
    }

    /**
     * 判断程序是否在前台运行
     *
     * @param context 上下文
     * @return 程序是否在前台运行
     */
    public static boolean isAppOnForeground(Context context) {
        try {
            List<RunningAppProcessInfo> appList = getRunningProcessList(context);
            if (appList != null) {
                for (RunningAppProcessInfo app : appList) {
                    if (app != null && app.processName.equals(context.getPackageName())
                            && app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断程序是否在后台运行
     *
     * @param context 上下文
     * @return 程序是否在后台运行
     */
    public static boolean isAppOnBackground(Context context) {
        return !isAppOnForeground(context);
    }

    /**
     * 判断当前应用程序是否处于系统栈顶
     *
     * @param context 上下文
     * @return 当前应用程序是否处于系统栈顶
     */
    public static boolean isTopActivity(final Context context) {
        if (context != null) {
            String packageName = AndroidUtils.getAppTopActivityPackageName(context);
            return context.getPackageName().equals(packageName);
        }
        return false;
    }

    /**
     * 获取当前运行的APP中处于栈顶的APP包名。
     * 注：getRunningTasks() 方法从 Android L 起限制访问。
     * 看：http://blog.csdn.net/hyhyl1990/article/details/45700447
     *
     * @param context 上下文
     * @return 当前运行的APP中处于栈顶的APP包名
     */
    public static String getAppTopActivityPackageName(Context context) {
        if (context == null) return null;
        try {
            ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
                if (tasks != null && !tasks.isEmpty()) {
                    ComponentName topActivity = tasks.get(0).topActivity;
                    if (topActivity != null) {
                        return topActivity.getPackageName();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前运行的APP中处于栈顶的APP包名（已设置最大返回100条数据）。
     * 该方法从Android L起限制访问，看 http://blog.csdn.net/hyhyl1990/article/details/45700447
     *
     * @param context 上下文
     * @return 当前运行的APP中处于栈顶的APP包名
     */
    public static List<ActivityManager.RunningTaskInfo> getRunningAppList(Context context) {
        if (context == null) return null;
        try {
            ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                return am.getRunningTasks(100); // 获取多少条记录
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 让App在后台运行
     *
     * @param context 上下文
     */
    public static void setAppRunInBackground(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 获取当前应用程序的包名
     *
     * @param context 上下文
     * @return 当前应用程序的包名
     */
    public static String getAppPackageName(Context context) {
        // Android 提供了一个API以让应用程序向系统查询包名信息，使用 PackageManager 的 getPackageInfo(java.lang.String, int)方法
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备ID和MAC信息（注册友盟测试设备使用）
     *
     * @param context 上下文
     * @return 设备ID和MAC信息
     */
    @SuppressLint({"HardwareIds"})
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (tm != null && checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            try {
                in = new BufferedReader(fstream, 1024);
                mac = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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
                if (!AndroidUtils.checkPermission(context, permission)) {
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

    /**
     * 判断按下的是否是返回(back)键
     *
     * @param keyCode keyCode
     * @param event KeyEvent
     * @return 是否是返回(back)键
     */
    public static boolean isPressedKeycodeBack(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN);
    }

    /**
     * 是否按下了屏幕菜单(menu)键
     *
     * @param keyCode keyCode
     * @param event KeyEvent
     * @return 是否按下了屏幕菜单(menu)键
     */
    public static boolean isPressedKeycodeMenu(int keyCode, KeyEvent event) {
        return (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_DOWN);
    }

    /**
     * 重启应用程序
     *
     * @param context 上下文
     */
    public static void restartApplication(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 卸载应用程序
     *
     * @param context 上下文
     */
    public static void uninstallApplication(Context context) {
        // <action android:name="android.intent.action.DELETE" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <data android:scheme="package" />
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        // 附加的额外的参数
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }


    /**
     * 开启一个应用程序
     *
     * @param context 上下文
     */
    public static void startApplication(Context context) {
        // 开启这个应用程序的第一个activity. 默认情况下 第一个activity就是具有启动能力的activity.
        String packname = context.getPackageName();
        PackageManager pm = context.getPackageManager();
        try {
            // 懒加载
            PackageInfo packinfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activityinfos = packinfo.activities;
            if (activityinfos != null && activityinfos.length > 0) {
                String className = activityinfos[0].name;
                Intent intent = new Intent();
                intent.setClassName(packname, className);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "该应用程序没有界面", Toast.LENGTH_LONG).show();
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "无法启动应用.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 分享应用程序,启动系统的分享界面
     *
     * @param context 上下文
     */
    public static void shareApplication(Context context) {
        // <action android:name="android.intent.action.SEND" />
        // <category android:name="android.intent.category.DEFAULT" />
        // <data android:mimeType="text/plain" />
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "推荐您使用一款软件,下载地址为:https://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(intent);
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    /**
     * 设置透明状态栏。
     * >=19时:getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    @Deprecated
    public static void setImmersionStatusBar(Activity activity) {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏。注意华为和HTC等有虚拟HOME键盘的，如果使用下面这段代码，界面会被覆盖。无法操作底部TAB（建议关闭）
//			 activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置透明状态栏
     * >=19时:
     * getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
     * >=19 且 <21时，设置：
     * //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
     * viewGroup.setClipToPadding(true);
     * //将侧边栏顶部延伸至status bar
     * viewGroup.setFitsSystemWindows(false);
     *
     * @param activity Activity
     * @param viewGroup 窗口的跟布局
     */
    public static void setImmersionStatusBar(Activity activity, ViewGroup viewGroup) {
        if (activity == null || viewGroup == null) {
            return;
        }
        // >=19
        if (VERSION.SDK_INT >= 19) {
            // 透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏。注意华为和HTC等有虚拟HOME键盘的，如果不设置下面这段代码，虚拟键盘将覆盖APP底部界面，无法操作底部TAB
//				activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // <21
            if (VERSION.SDK_INT < 21) {
                // 设置系统是否需要考虑 StatusBar 占据的区域来显示
                viewGroup.setFitsSystemWindows(false);
                // 是否受 StatusBar的Padding的影响，true, 则布局会往下延伸Padding，false，则占用padding的区域
                viewGroup.setClipToPadding(true);

                // android:fitsSystemWindows=""，
                // false：布局不受StatusBar的影响，可以完全的展示在StatusBar的下面。
                // true：布局不受StatusBar的影响，不会被StatusBar遮住，

                // android:clipToPadding="false"
                // false：布局不受Padding的影响，可以展示在Padding的区域。其实fitsSystemWindows就是设置一个Padding使View不会展示在StatusBar的下方，
            }
        }
    }

    /**
     * 清除透明状态栏设置
     *
     * @param activity Activity
     */
    @SuppressLint("InlinedApi")
    public static void clearImmersionStatusBar(Activity activity) {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 获取Manifest中配置的渠道名
     *
     * @param context 上下文
     * @param key key
     * @return 渠道名
     */
    public static String getChannelName(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    /**
     * 判断屏幕是否亮着
     *
     * @param context 上下文
     * @return 是否亮着
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        return pm != null && pm.isScreenOn();
    }

    /**
     * 是否开启了重力感应
     *
     * @param context 上下文
     * @return 是否开启了重力感应
     */
    public static boolean isOpenRotate(Context context) {
        int gravity = 0;
        try {
            gravity = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return gravity == 1;
    }

    /**
     * 是否开启锁屏功能（比如手势，PIN，密码等锁屏功能）
     *
     * @param context 上下文
     * @return 是否开启锁屏功能
     */
    public static boolean isOpenKeyguard(Context context) {
        if (VERSION.SDK_INT >= 16) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                return keyguardManager.isKeyguardSecure();
            }
        }
        return false;
    }

    /**
     * 是否支持指纹识别（判断是否有硬件）
     * 给出两种方式，第一种是通过V4支持包获得兼容的对象引用，这是google推行的做法；还有就是直接使用api 23 framework中的接口获得对象引用。
     *
     * @param context 上下文
     * @return 是否支持指纹识别
     */
    @SuppressLint("MissingPermission")
    public static boolean isSupportFingerprint(Context context) {
        // Using the Android Support Library v4
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        // Using API level 23:
//        FingerprintManager fingerprintManager = (FingerprintManager) context.getApplicationContext().getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager == null) return false;
        return fingerprintManager.isHardwareDetected();
    }

    /**
     * 检查设备中是否有注册过的指纹信息（需要权限）
     *
     * @param context 上下文
     * @return 是否有注册过的指纹信息
     */
    @SuppressLint("MissingPermission")
    public static boolean hasEnrolledFingerprints(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        if (fingerprintManager == null) return false;
        return fingerprintManager.hasEnrolledFingerprints();
    }

    /**
     * 检测辅助功能是否开启
     *
     * @param mContext Context
     * @param serviceClass extends AccessibilityService
     * @return 是否开启
     */
    public static boolean isAccessibilitySettingsOn(Context mContext, Class<? extends AccessibilityService> serviceClass) {
        int accessibilityEnabled = 0;
        // TestService为对应的服务
        final String service = mContext.getPackageName() + "/" + serviceClass.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断服务是否启动,context上下文对象 ，className服务的name
     *
     * @param context 上下文
     * @param serviceClass 服务类名
     * @return 服务是否启动
     */
    public static boolean isServiceRunning(Context context, Class<? extends AccessibilityService> serviceClass) {
        if (context != null && serviceClass != null) {
            ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(200);
                if (!serviceList.isEmpty()) {
                    String serviceClassName = serviceClass.getSimpleName();
                    for (int i = 0; i < serviceList.size(); i++) {
                        if (serviceList.get(i).service.getClassName().equals(serviceClassName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 根据文件路径安装APK
     *
     * @param context 上下文
     * @param filePath 文件路径
     */
    public static void install(Context context, String filePath) {
        install(context, new File(filePath));
    }

    /**
     * 根据文件安装APK
     *
     * @param context 上下文
     * @param file APK文件
     */
    public static void install(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取屏幕密度DPI（如 120 / 160 / 240 / ）
     * 标准量化的DPI-低密度屏幕
     * DENSITY_LOW = 120;
     * DENSITY_140 = 140;
     * 标准量化的DPI-中密度屏幕 1
     * DENSITY_MEDIUM = 160;
     * DENSITY_180 = 180;
     * DENSITY_200 = 200;
     * 一般是720P的电视使用的密码，分辨率为1280x720的显示器
     * DENSITY_TV = 213;
     * DENSITY_220 = 220;
     * 标准量化的DPI-高密度屏幕 1.5
     * DENSITY_HIGH = 240;
     * DENSITY_260 = 260;
     * DENSITY_280 = 280;
     * DENSITY_300 = 300;
     * 标准量化的DPI-超高密度屏幕 2
     * DENSITY_XHIGH = 320;
     * DENSITY_340 = 340;
     * DENSITY_360 = 360;
     * DENSITY_400 = 400;
     * DENSITY_420 = 420;
     * DENSITY_440 = 440;
     * DENSITY_450 = 450;
     * 标准量化的DPI-额外高密度屏幕 2.5
     * DENSITY_XXHIGH = 480;
     * DENSITY_560 = 560;
     * DENSITY_600 = 600;
     * 标准量化的DPI-额外的扩展-高密度屏幕 3 一般4K电视屏幕使用这个密度，分辨率3840x2160
     * DENSITY_XXXHIGH = 640;
     *
     * @param context 上下文
     * @return 获取屏幕密度DPI，默认返回0
     */
    public static int getScreenDensityDpi(Context context) {
        return context == null ? 0 : context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取屏幕密度（如1.0、1.5, 2.0）
     *
     * @param context 上下文
     * @return 默认返回0.0
     */
    public static float getScreenDensity(Context context) {
        return context == null ? 0 : context.getResources().getDisplayMetrics().density;
    }

    /**
     * 字体缩放比例
     *
     * @param context 上下文
     * @return 字体缩放比例，默认返回0.0
     */
    public static float getScaledDensity(Context context) {
        return context == null ? 0 : context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 关闭AndroidP的系统提示对话框
     */
    public static void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLanguage(Context context) {
        return context == null ? null : context.getResources().getConfiguration().locale.getLanguage();
    }

    public static String getLanguageLocal(Context context) {
        String language = getLanguage(context);
        return "zh".equals(language) ? "zh_CN" : "en_US";
    }

    /**
     * 设置语言（App内部）
     *
     * @param context
     * @param locale
     */
    public static void setLanguageLocal(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics display = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, display);
    }

    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     * 原文链接：https://blog.csdn.net/mzm489321926/article/details/50542065/
     */
    public static boolean checkAppOps(Context context) {
        try {
            if (VERSION.SDK_INT >= 19) {
                Object object = context.getApplicationContext().getSystemService(Context.APP_OPS_SERVICE);
                if (object == null) {
                    return false;
                }
                Class[] arrayOfClass = new Class[3];
                arrayOfClass[0] = Integer.TYPE;
                arrayOfClass[1] = Integer.TYPE;
                arrayOfClass[2] = String.class;
                Method method = object.getClass().getMethod("checkOp", arrayOfClass);
                Object[] arrayOfObject1 = new Object[3];
                arrayOfObject1[0] = 24;
                arrayOfObject1[1] = Binder.getCallingUid();
                arrayOfObject1[2] = context.getPackageName();
                int m = (int) method.invoke(object, arrayOfObject1);
                return m == AppOpsManager.MODE_ALLOWED;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
