package com.wuguangxin.utils.demo.ui;

import android.view.ViewGroup;
import android.widget.TextView;

import com.wuguangxin.utils.AndroidUtils;
import com.wuguangxin.utils.demo.R;
import com.wuguangxin.utils.demo.databinding.ActivityAndroidUtilsBinding;

import butterknife.BindView;

public class AndroidUtilsActivity extends BaseActivity {
    ActivityAndroidUtilsBinding binding;

    @BindView(R.id.android_info) TextView mTextView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_android_utils;
    }

    @Override
    public void initView() {
        binding = ActivityAndroidUtilsBinding.inflate(getLayoutInflater());
        setTitle(getSimpleTitle());

        StringBuilder sb = new StringBuilder();

        sb.append("\n\n=========系统信息：=========");
        sb.append("\nSDK版本：").append(AndroidUtils.getSdkVersion());
        sb.append("\n系统版本：").append(AndroidUtils.getRelease());
        sb.append("\n设备型号：").append(AndroidUtils.getModel());
        sb.append("\n设备ID：").append(AndroidUtils.getDeviceId(context));
        sb.append("\n设备IMEI：").append(AndroidUtils.getIMEI(context));
        sb.append("\n设备类型：").append(AndroidUtils.isMobilePhone(context) ? "真机" : "模拟器");
        sb.append("\n正在运行的程序：").append(AndroidUtils.getRunningProcessSize(context)).append("个");
        sb.append("\n是否处于后台运行：").append(AndroidUtils.isAppOnBackground(context));
        sb.append("\n是否处于前台运行：").append(AndroidUtils.isAppOnForeground(context));

        sb.append("\n\n=========硬件功能：=========");
        sb.append("\n是否开启了重力感应：").append(AndroidUtils.isOpenRotate(context));
        sb.append("\n是否开启了锁屏功能：").append(AndroidUtils.isOpenKeyguard(context));
        // 需要权限
        sb.append("\n是否支持指纹传感器：").append(AndroidUtils.isSupportFingerprint(context));
//        sb.append("\n是否开启了指纹传感器：").append(AndroidUtils.hasEnrolledFingerprints(context));
        sb.append("\n是否开启了辅助功能：").append("有bug"/*AndroidUtils.isAccessibilitySettingsOn(context, null)*/);
        sb.append("\n悬浮窗口权限是否打开：").append(AndroidUtils.checkAppOps(context));

        sb.append("\n\n=========屏幕信息：=========");
        sb.append("\n屏幕宽：").append(AndroidUtils.getScreenWidth(context));
        sb.append("\n屏幕高：").append(AndroidUtils.getScreenHeight(context));
        sb.append("\n状态栏高：").append(AndroidUtils.getStatusBarHeight(context));
        sb.append("\n屏幕是否常亮：").append(AndroidUtils.isScreenOn(context));
        sb.append("\n屏幕密度DPI：").append(AndroidUtils.getScreenDensityDpi(context));
        sb.append("\n屏幕密度：").append(AndroidUtils.getScreenDensity(context));
        sb.append("\n字体缩放比例：").append(AndroidUtils.getScaledDensity(context));

        sb.append("\n\n=========APP信息：=========");
        sb.append("\n程序包名：").append(AndroidUtils.getPackageName(context));
        sb.append("\nAPP版本名：").append(AndroidUtils.getVersionName(context));
        sb.append("\nAPP版本号：").append(AndroidUtils.getVersionCode(context));
        sb.append("\n应用渠道号：").append(AndroidUtils.getChannelName(context, ""));
        sb.append("\n本机手机号：").append(AndroidUtils.getContactNumber(context, null));
        sb.append("\n应用是否处于栈顶：").append(AndroidUtils.isTopActivity(context));
        sb.append("\n当前栈顶程序包名：").append(AndroidUtils.getAppTopActivityPackageName(context));

        sb.append("\n运行的程序列表：").append(AndroidUtils.getRunningAppList(context));

        sb.append("\n友盟使用的设备信息：").append(AndroidUtils.getDeviceInfo(context));


        mTextView.setText(sb);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        binding.runBackground.setOnClickListener(v -> AndroidUtils.setAppRunInBackground(context));
        binding.reboot.setOnClickListener(v -> AndroidUtils.restartApplication(context));
        binding.uninstall.setOnClickListener(v -> AndroidUtils.uninstallApplication(context));
        binding.startApp.setOnClickListener(v -> AndroidUtils.startApplication(context));
        binding.shareApp.setOnClickListener(v -> AndroidUtils.shareApplication(context));
        binding.immersion.setOnClickListener(v -> AndroidUtils.setImmersionStatusBar(AndroidUtilsActivity.this));
        binding.immersion2.setOnClickListener(v -> AndroidUtils.setImmersionStatusBar(AndroidUtilsActivity.this, (ViewGroup) getWindow().getDecorView()));
        binding.immersionClear.setOnClickListener(v -> AndroidUtils.clearImmersionStatusBar(AndroidUtilsActivity.this));
    }
}
