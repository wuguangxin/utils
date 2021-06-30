package com.wuguangxin.utils.demo.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.wuguangxin.utils.AnimUtil;
import com.wuguangxin.utils.BitmapUtils;
import com.wuguangxin.utils.PermissionUtils;
import com.wuguangxin.utils.demo.R;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.OnClick;

public class AnimUtilsActivity extends BaseActivity {
    @BindView(R.id.anim_image_view) ImageView mImageView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_anim_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListener() {

    }

    @OnClick({
            R.id.left_in, R.id.left_out,
            R.id.top_in, R.id.top_out,
            R.id.right_in, R.id.right_out,
            R.id.bottom_in, R.id.bottom_out,

            R.id.fade_in, R.id.fade_out,
            R.id.rotate_20, R.id.rotate_360,
            R.id.zoom_out, R.id.zoom_in,

            R.id.request_permission, R.id.check_permission, R.id.write_file,
    })
    public void onClicked(View view) {
        int id = view.getId();
        switch (id) {
        case R.id.left_in:
            mImageView.startAnimation(AnimUtil.getLeftIn());

//            ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationX", -mImageView.getWidth(), 0);
//            animator.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator.setDuration(500);
//            animator.start();
            break;
        case R.id.left_out:
            mImageView.startAnimation(AnimUtil.getLeftOut());
//            mImageView.animate().translationX(-mImageView.getWidth()).setDuration(300).setInterpolator(new AccelerateDecelerateInterpolator());
            break;
        case R.id.top_in:
            mImageView.startAnimation(AnimUtil.getTopIn(500));
            break;
        case R.id.top_out:
            mImageView.startAnimation(AnimUtil.getTopOut(500));
//            mImageView.animate().translationY(-mImageView.getHeight());
            break;
        case R.id.right_in:
            mImageView.startAnimation(AnimUtil.getRightIn());
            break;
        case R.id.right_out:
            mImageView.startAnimation(AnimUtil.getRightOut());
//            mImageView.animate().translationX(mImageView.getHeight());
            break;
        case R.id.bottom_in:
            mImageView.startAnimation(AnimUtil.getBottomIn());
            break;
        case R.id.bottom_out:
            mImageView.startAnimation(AnimUtil.getBottomOut());
//            mImageView.animate().translationY(mImageView.getHeight());
            break;
        case R.id.fade_in:
            mImageView.startAnimation(AnimUtil.getFadeIn());
            break;
        case R.id.fade_out:
            mImageView.startAnimation(AnimUtil.getFadeOut());
            break;
        case R.id.rotate_20:
            mImageView.startAnimation(AnimUtil.getRotate20());
            break;
        case R.id.rotate_360:
            mImageView.startAnimation(AnimUtil.getRotate360());
            break;
        case R.id.zoom_out:
            mImageView.startAnimation(AnimUtil.getZoomOut());
            break;
        case R.id.zoom_in:
            mImageView.startAnimation(AnimUtil.getZoomIn());
            break;
        case R.id.request_permission:
            // 请求权限
            ActivityCompat.requestPermissions(this, PERMISSION_EXTERNAL_STORAGE, 100);
            break;
        case R.id.check_permission:
            boolean permission1 = PermissionUtils.checkPermission(this, PERMISSION_EXTERNAL_STORAGE);
            showToast(Manifest.permission.READ_EXTERNAL_STORAGE + "权限是否已获取：" + permission1);
            break;
        case R.id.write_file:
            if (PermissionUtils.checkPermission(this, PERMISSION_EXTERNAL_STORAGE)) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                if (bitmap != null) {
                    File file = BitmapUtils.bitmapToFile(this, bitmap);
                    showToast(file == null ? "Logo保存失败！" : "Logo已保存到相册");
                } else {
                    showToast("bitmap == null");
                }
            } else {
                showToast("未获取[存储空间]权限，请先授权");
            }
            break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= 23) {
                // 获得被拒绝的权限
                String deniedPermission = null;
                for (int i = 0; i < permissions.length; i++) {
                    printLogI("permissions: " + permissions[i] + " = " + grantResults[i]);
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        deniedPermission = permissions[i];
                    }
                }
                if (TextUtils.isEmpty(deniedPermission)) {
//                    checkPermission();
                } else {
                    // 这个方法大概意思是当请求一个权限而被用户拒绝并且勾选不再提示时，返回false（当然返回false的情况也有多种）
                    boolean flag = shouldShowRequestPermissionRationale(deniedPermission);
                    if (!flag) {
                        // 被用户拒绝后系统不再提示，这里由APP弹出提示去设置
//                        showPermissionDialog(mContext, permissions);
                        showToast("被用户拒绝后系统不再提示的权限：" + deniedPermission);
                    } else {
//                        finish();
                    }
                }
            }
        }
    }

    /**
     * 6.0 动态请求的权限-存储空间
     */
    public static final String[] PERMISSION_EXTERNAL_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,    // 读存储卡
            Manifest.permission.WRITE_EXTERNAL_STORAGE    // 写存储卡
    };
}