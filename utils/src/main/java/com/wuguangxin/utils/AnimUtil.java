package com.wuguangxin.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Vibrator;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.wuguangxin.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 动画工具类
 * Created by wuguangxin on 2015/4/1
 */
public class AnimUtil {

    // android:toXDelta="100%",表示自身的100%,也就是从View自己的位置开始。
    // android:toXDelta="80%p",表示父层View的80%,是以它父层View为参照的。

    public static Animation loadAnim(Context context, int animRes) {
        return AnimationUtils.loadAnimation(context, animRes);
    }

    public static Animation getLeft_in(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_left_in);
    }

    public static Animation getLeft_out(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, -1.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_left_out);
    }

    public static Animation getTop_in(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, -1.0F,
                Animation.RELATIVE_TO_SELF, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_top_in);
    }

    public static Animation getTop_out(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, -1.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_top_out);
    }

    public static Animation getRight_in(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_right_in);
    }

    public static Animation getRight_out(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 1.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        return set; // loadAnim(context, R.anim.xin_anim_right_out);
    }

    public static Animation getBottom_in(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 1.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        set.setFillAfter(true);
        set.setFillEnabled(true);
        return set; // loadAnim(context, R.anim.xin_anim_bottom_in);
    }

    public static Animation getBottom_out(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_SELF, 1.0F);
        translate.setDuration(300);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);
        set.setFillAfter(true); // 动画停留在最后一帧
        set.setFillEnabled(true);
        return set; // loadAnim(context, R.anim.xin_anim_bottom_out);
    }

    public static Animation getFade_in(Context context) {
        Animation alpha = new AlphaAnimation(0.0F, 1.0F);
        alpha.setDuration(1000);
//        AnimationSet set = new AnimationSet(false);
//        set.addAnimation(alpha);
        return alpha; // loadAnim(context, R.anim.xin_anim_fade_in);
    }

    public static Animation getFade_out(Context context) {
        Animation alpha = new AlphaAnimation(1.0F, 0.0F);
        alpha.setDuration(1000);
//        AnimationSet set = new AnimationSet(false);
//        set.addAnimation(alpha);
        return alpha; // loadAnim(context, R.anim.xin_anim_fade_out);
    }

    public static Animation getLayerFadeIn(Context context) {
        Animation alpha = new AlphaAnimation(0.0F, 1.0F);
        alpha.setDuration(300);
//        AnimationSet set = new AnimationSet(false);
//        set.addAnimation(alpha);
        return alpha; // loadAnim(context, R.anim.xin_anim_layer_fade_in);
    }

    public static Animation getLayerFadeOut(Context context) {
        Animation alpha = new AlphaAnimation(1.0F, 0.0F);
        alpha.setDuration(300);
//        AnimationSet set = new AnimationSet(false);
//        set.addAnimation(alpha);
        return alpha; // loadAnim(context, R.anim.xin_anim_layer_fade_out);
    }

// <rotate
//    android:fromDegrees="-20"
//    android:toDegrees="20"
//    android:pivotX="50%"
//    android:pivotY="50%"
//    android:duration="1000"
//    android:startOffset="300"
//    android:fromXScale="1"
//    android:fromYScale="1"
//    android:toXScale="0"
//    android:toYScale="0"
//    android:repeatCount="infinite"
//    android:repeatMode="reverse" />
    public static Animation getRotate_20(Context context) {
//        float fromDegrees,
//        float toDegrees,
//        int pivotXType,
//        float pivotXValue,
//        int pivotYType,
//        float pivotYValue
        RotateAnimation rotate = new RotateAnimation(
                -20, 20, // 旋转幅度 -20度到20度
                Animation.RELATIVE_TO_SELF, 0.5F, // 50%
                Animation.RELATIVE_TO_SELF, 0.5F // 50%
        );
        rotate.setDuration(1000); // 执行时长
        rotate.setStartOffset(300); // 执行延时
        rotate.setRepeatCount(Animation.INFINITE); // infinite 无限次数
        rotate.setRepeatMode(Animation.REVERSE); // reverse 循环播放
        AnimationSet set = new AnimationSet(false);
        set.setInterpolator(new AccelerateInterpolator()); // @android:anim/accelerate_interpolator
        set.addAnimation(rotate);
        return set; // loadAnim(context, R.anim.xin_anim_rotate_20);
    }

// <rotate
//    android:fromDegrees="0"
//    android:toDegrees="360"
//    android:pivotX="50%"
//    android:pivotY="50%"
//    android:duration="1000"
//    android:startOffset="300"
//    android:repeatCount="infinite"
//    android:repeatMode="reverse" />
    public static Animation getRotate_360(Context context) {
        RotateAnimation rotate = new RotateAnimation(
        0, 360, // 360度旋转
                Animation.RELATIVE_TO_SELF, 0.5F, // 50%
                Animation.RELATIVE_TO_SELF, 0.5F // 50%
        );
        rotate.setDuration(1000); // 执行时长
        rotate.setStartOffset(300); // 执行延时
        rotate.setRepeatCount(Animation.INFINITE); // infinite 无限次数
        rotate.setRepeatMode(Animation.REVERSE); // reverse 循环播放

        AnimationSet set = new AnimationSet(false);
        set.setInterpolator(new AccelerateInterpolator()); // @android:anim/accelerate_interpolator
        set.addAnimation(rotate);
        return set; // loadAnim(context, R.anim.xin_anim_rotate_360);
    }


// <set xmlns:android="http://schemas.android.com/apk/res/android" >
//    <alpha
//        android:duration="500"
//        android:repeatCount="0"
//        android:fromAlpha="0"
//        android:toAlpha="1" />
//
//    <scale
//        android:interpolator= "@android:anim/accelerate_interpolator"
//        android:fromXScale="3"
//        android:toXScale="1"
//        android:fromYScale="3"
//        android:toYScale="1"

//        android:pivotX="50%"
//        android:pivotY="50%"
//        android:duration="800"
//        android:repeatCount="0"  />
//
//    <!--
//        fromXDelta,fromYDelta       	起始时X，Y座标,屏幕右下角的座标是X:320,Y:480
//        toXDelta， toYDelta      		动画结束时X,Y的座标
//        interpolator                  指定动画插入器
//        加速减速插入器        		    accelerate_decelerate_interpolator
//        加速插入器               		accelerate_interpolator，
//        减速插入器               		decelerate_interpolator。
//        fromXScale,fromYScale，       动画开始前X,Y的缩放，0.0为不显示，  1.0为正常大小
//        toXScale，toYScale，          动画最终缩放的倍数， 1.0为正常大小，大于1.0放大
//        pivotX，  pivotY             动画起始位置，相对于屏幕的百分比,两个都为50%表示动画从屏幕中间开始
//        startOffset，                动画多次执行的间隔时间，如果只执行一次，执行前会暂停这段时间，
//                                    单位毫秒 duration，一次动画效果消耗的时间，单位毫秒，
//                                    值越小动画速度越快 repeatCount，动画重复的计数，动画将会执行该值+1次
//        repeatMode，               动画重复的模式，reverse为反向，当第偶次执行时，动画方向会相反。
//        restart                   为重新执行，方向不变 -->
// </set>
    /**
     * 全屏逐渐缩小的动画
     */
    public static Animation getZoomOut(Context context) {
        AlphaAnimation alpha = new AlphaAnimation(0.0F, 1.0F);
        alpha.setDuration(500);
        alpha.setRepeatCount(0);

        ScaleAnimation scale = new ScaleAnimation(
                3.0F, 1.0F, 3.0F, 1.0F,
                Animation.RELATIVE_TO_SELF, 0.5F, // 50%
                Animation.RELATIVE_TO_SELF, 0.5F); // 50%
        scale.setDuration(500);
        scale.setRepeatCount(0);

        AnimationSet set = new AnimationSet(false);
        set.setInterpolator(new AccelerateInterpolator()); // @android:anim/accelerate_interpolator
        set.addAnimation(alpha);
        set.addAnimation(scale);

        return set; // loadAnim(context, R.anim.xin_anim_zoomout);
    }

    /**
     * 左右晃动动画
     */
    public static Animation getShakeLR(Context context) {
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 5.0F,
                Animation.RELATIVE_TO_SELF, 0.0F,
                Animation.RELATIVE_TO_SELF, 0.0F);
        translate.setDuration(800);
        translate.setInterpolator(new CycleInterpolator(5));

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(translate);

        return set; // loadAnim(context, R.anim.xin_anim_shake_view);
    }

    /**
     * 让组件从左边移入
     */
    public static void startLeftIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getLeft_in(view.getContext()));
    }

    /**
     * 让组件从上边移入
     */
    public static void startTopIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getTop_in(view.getContext()));
    }

    /**
     * 让组件从右边移入
     */
    public static void startRightIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getRight_in(view.getContext()));
    }

    /**
     * 让组件从下边移入
     */
    public static void startBottomIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getBottom_in(view.getContext()));
    }

    /**
     * 让组件从左边移出
     */
    public static void startLeftOut(View view) {
        if (view != null) startAnim(view, View.GONE, getLeft_out(view.getContext()));
    }

    /**
     * 让组件从上边移出
     */
    public static void startTopOut(View view) {
        if (view != null) startAnim(view, View.GONE, getTop_out(view.getContext()));
    }

    /**
     * 让组件从右边移出
     */
    public static void startRightOut(View view) {
        if (view != null) startAnim(view, View.GONE, getRight_out(view.getContext()));
    }

    /**
     * 让组件从下边移出
     */
    public static void startBottomOut(View view) {
        if (view != null) startAnim(view, View.GONE, getBottom_out(view.getContext()));
    }

    /**
     * 让组件淡入-放大界面
     */
    public static void startFadeIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getFade_in(view.getContext()));
    }

    /**
     * 让组件淡出-缩小界面
     */
    public static void startFadeOut(View view) {
        if (view != null) startAnim(view, View.GONE, getFade_out(view.getContext()));
    }

    /**
     * 让View以淡入方式显示（半透明层）
     */
    public static void startAlphaLayerFadeIn(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getLayerFadeIn(view.getContext()));
    }

    /**
     * 让View以淡出方式隐藏（半透明层）
     */
    public static void startAlphaLayerFadeOut(View view) {
        if (view != null) startAnim(view, View.GONE, getLayerFadeOut(view.getContext()));
    }

    /**
     * 让View左右20度来回摆动（如八挂钟摆）
     */
    public static void startRotate20(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getRotate_20(view.getContext()));
    }

    /**
     * 让View 360度循环旋转
     */
    public static void startRotate360(View view) {
        if (view != null) startAnim(view, View.VISIBLE, getRotate_360(view.getContext()));
    }

    private static void startAnim(View view, int visibility, Animation anim) {
        if (view != null) {
            view.setVisibility(visibility);
            if (anim != null) {
                view.startAnimation(anim);
            }
        }
    }


    /**
     * Activity 打开时动画，在Activity.startActivity()后调用
     *
     * @param activity
     */
    public static void animEnter(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_activity_open_enter, R.anim.xin_anim_activity_open_exit);
        }
    }

    /**
     * Activity 关闭时动画，在Activity.finish()后调用
     *
     * @param activity
     */
    public static void animClose(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_activity_close_enter, R.anim.xin_anim_activity_close_exit);
        }
    }

    /**
     * Lock Activity 打开时动画，在Activity.startActivity()后调用
     *
     * @param activity
     */
    public static void animEnterLock(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_fade_in, R.anim.xin_anim_fade_out);
        }
    }

    /**
     * Lock Activity 关闭时动画，在Activity.finish()后调用
     *
     * @param activity
     */
    public static void animCloseLock(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_fade_in, R.anim.xin_anim_fade_out);
        }
    }

    /**
     * Lock Activity 打开时动画，在Activity.startActivity()后调用
     *
     * @param activity
     */
    public static void animEnterLockDialog(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_dialog_in, R.anim.xin_anim_dialog_out);
        }
    }

    /**
     * Lock Activity 关闭时动画，在Activity.finish()后调用
     *
     * @param activity
     */
    public static void animCloseLockDialog(Activity activity) {
        if (activity != null) {
            activity.overridePendingTransition(R.anim.xin_anim_dialog_in, R.anim.xin_anim_dialog_out);
        }
    }

    /**
     * 让View钟摆方式摆动
     *
     * @param view view
     * @param angle 距离中心点(0)偏移角度
     * @param duration 执行时长
     * @param count 摆动次数
     */
    public static SwingAnimation startSwingAnimation(View view, float angle, long duration, int count) {
        final SwingAnimation swingAnim = new SwingAnimation(view.getContext(), count, angle);
        swingAnim.setDuration(duration);
        swingAnim.setRepeatCount(count);
        swingAnim.setRepeatMode(Animation.REVERSE);
        view.startAnimation(swingAnim);
        return swingAnim;
    }

    /**
     * 模仿摇摆动画
     */
    public static class SwingAnimation extends Animation implements Animation.AnimationListener {
        private float mFromDegrees;
        private float mToDegrees;

        private int mPivotXType = ABSOLUTE;
        private int mPivotYType = ABSOLUTE;
        private float mPivotXValue = 0.0f;
        private float mPivotYValue = 0.0f;

        private float mPivotX;
        private float mPivotY;

        private int mCount;
        private float mAngle = 15;
        private float singleSize;
        private float currentCount;

        public float getToDegrees() {
            return mToDegrees;
        }

        public void setToDegrees(float mToDegrees) {
            this.mToDegrees = mToDegrees;
        }

        public float getFromDegrees() {
            return mFromDegrees;
        }

        public void setFromDegrees(float mFromDegrees) {
            this.mFromDegrees = mFromDegrees;
        }

        public void stop() {
            this.mFromDegrees = 0;
            this.mToDegrees = 0;
        }

        public SwingAnimation(Context context, int count, float angle) {
            this(context, count, angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f);
        }

        public SwingAnimation(Context context, int count, float angle, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
            super(context, null);
            mCount = count;
            currentCount = mCount;

            mAngle = angle;
            singleSize = mAngle * 2 / count;

            mFromDegrees = 0;
            mToDegrees = mAngle;

            mPivotXValue = pivotXValue;
            mPivotXType = pivotXType;
            mPivotYValue = pivotYValue;
            mPivotYType = pivotYType;

            setStartOffset(200); // 在为重复执行前设置偏移，当重复执行后，设置偏移为0
            setAnimationListener(this);
            initializePivotPoint();
        }

        private void initializePivotPoint() {
            if (mPivotXType == ABSOLUTE) {
                mPivotX = mPivotXValue;
            }
            if (mPivotYType == ABSOLUTE) {
                mPivotY = mPivotYValue;
            }
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime); // -10 + ((10+10) * -10) = -210
            float scale = getScaleFactor();

            if (mPivotX == 0.0f && mPivotY == 0.0f) {
                t.getMatrix().setRotate(degrees);
            } else {
                t.getMatrix().setRotate(degrees, mPivotX * scale, mPivotY * scale);
            }
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
            mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            setStartOffset(0);
            currentCount--;
            if (currentCount <= 0) {
                mToDegrees = 0;
            } else {
                if (currentCount % 2 == 0) {
                    mToDegrees = singleSize * currentCount;
                } else {
                    mFromDegrees = -singleSize * currentCount;
                }
            }
        }
    }

    /**
     * 让View震动，如果是EditText，则自动获取焦点，并把输入光标置于字符串后面。
     *
     * @param context 上下文
     * @param shakeView 要震动的View对象
     * @param msg 震动时提示的Toast消息，如果为null，则不提示消息
     */
    public static void shakeView(Context context, View shakeView, String msg) {
        shakeView(context, null, shakeView, msg);
    }

    /**
     * 让View震动，如果是EditText，则自动获取焦点，并把输入光标置于字符串后面。
     * （需要震动权限）
     *
     * @param context 上下文
     * @param clickView 触发点击事件的View（只是让此View可点击）
     * @param shakeView 要震动的View对象
     * @param toastMsg 震动时提示的Toast消息，如果为null，则不提示消息
     */
    @SuppressLint("MissingPermission")
    public static void shakeView(Context context, View clickView, View shakeView, String toastMsg) {
        if (clickView != null) {
            clickView.setEnabled(true);
        }
        if (shakeView != null) {
            Vibrator vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // vibrator.vibrate(new long[]{100,50,200,50}, 1); // 使用震动规则
                vibrator.vibrate(100); // 震动时长（毫秒）

                // Animation shakeAnimation = loadAnim(context, R.anim.xin_anim_shake_view);
                // 构建一个水平位移的动画，代替上面的xml动画配置文件
                // float fromXDelta = 0
                // float toXDelta = 5
                // float fromYDelta = 0
                // float toYDelta = 0
                Animation shakeAnimation = new TranslateAnimation(0, 5, 0, 0);
                shakeAnimation.setDuration(800);
                shakeAnimation.setInterpolator(new CycleInterpolator(5)); //

                shakeView.startAnimation(shakeAnimation); // 震动view
                shakeView.setFocusable(true);
                shakeView.setEnabled(true);
                shakeView.setFocusableInTouchMode(true);
                if (shakeView instanceof EditText) {
                    shakeView.requestFocus(); // 获取焦点，位置在内容后面
                    Editable text = ((EditText) shakeView).getText();
                    Selection.setSelection(text, text.length());
                }
            }
        }
        if (!TextUtils.isEmpty(toastMsg)) {
            ToastUtils.showToast(context, toastMsg);
        }
    }

    /**
     * 给 ViewGroup 的 child 做动画，特别最适合给列表的item项动画。
     * 此动画可以直接在继承至ViewGroup的xml属性中增加：android:layoutAnimation="@anim/xin_anim_layout"
     *
     * @param viewGroup
     */
    public static void startAnimChild(ViewGroup viewGroup) {
        if (viewGroup != null) {
            // 创建LayoutAnimationController方式1:
            // java：
//            Animation translate = new TranslateAnimation(
//                    Animation.RELATIVE_TO_SELF, -1.0F,
//                    Animation.RELATIVE_TO_SELF, 0.0F,
//                    Animation.RELATIVE_TO_SELF, 0.0F,
//                    Animation.RELATIVE_TO_SELF, 0.0F);
//            translate.setDuration(300);
//            AlphaAnimation alpha = new AlphaAnimation(0.0F, 1.0F);
//            alpha.setInterpolator(new DecelerateInterpolator());
//            alpha.setRepeatCount(0);
//            alpha.setDuration(500);
//            AnimationSet animation = new AnimationSet(false);
//            animation.addAnimation(translate);
//            animation.addAnimation(alpha);
            // xml：
            Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext(), android.R.anim.fade_in);
            LayoutAnimationController controller = new LayoutAnimationController(animation);

            // 创建LayoutAnimationController方式2:
            // LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(viewGroup.getContext(), R.anim.xin_anim_left_in_fade_in);

            //设置控件显示的顺序
            controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
            //设置控件显示间隔时间
            controller.setDelay(300);
            // 为ListView设置LayoutAnimationController属性；
            viewGroup.setLayoutAnimation(controller);
            viewGroup.startLayoutAnimation();
        }
    }

    //============================================================================================
    //============================================================================================
    //============以下代码是给列表的Item做动画========================================================

    private static boolean isRepeatItemAnim = true; // 已经动画过的item是否可以再次动画
    private static final long ANIM_DURATION = 500; // 动画的时长
    private static int animParentViewHeight; // 列表的高
    private static int animItemViewHeight; // item的高
    private static List<Integer> animItemPositionList; // 存储已经动画过的item的position集合
    private static WeakReference<View> animParentView; // 进行动画的列表

    /**
     * 创建item动画记录集合（对应的是item所在的位置集合）
     */
    public static void initAnimItemPositionList() {
        if (animItemPositionList == null) {
            animItemPositionList = new ArrayList<>();
        } else {
            animItemPositionList.clear();
        }
    }

    /**
     * 重置item动画记录集合
     */
    public static void clearAnimItemPositionList() {
        if (animItemPositionList != null) {
            animItemPositionList.clear();
            animItemPositionList = null;
        }
    }

    /**
     * 给ListView的Item做逐个淡出的动画
     */
    public static void setAnimParentView(View view) {
        animParentView = new WeakReference<>(view);
        initAnimItemPositionList();
        getAnimParentViewHeight();
    }

    public static void getAnimParentViewHeight() {
        if (animParentView != null) {
            ViewTreeObserver viewTreeObserver = animParentView.get().getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    animParentViewHeight = animParentView.get().getMeasuredHeight();
                    return true;
                }
            });
        }
    }

    public static void getAnimItemViewHeight(Context context, final View view) {
        if (view != null && animItemViewHeight == 0) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    animItemViewHeight = view.getMeasuredHeight();
                    return true;
                }
            });
        }
    }

    /**
     * 给List的item设置动画
     *
     * @param context
     * @param position 位置
     * @param itemView
     */
    public static void startItemAnim(Context context, int position, View itemView) {
        if (!isRepeatItemAnim && animItemPositionList.contains(position)) {
            return;
        }
        animItemPositionList.add(position);
        getAnimItemViewHeight(context, itemView);

        // itemView.startAnimation(BaseActivity.right_in); //动画1
        // startRotateAnimation(position, itemView); //动画2
        startTranslateAnimation(context, position, itemView);  // 动画3 当前使用
    }

    /**
     * 给List中的Item增加TranslateAnimation动画
     *
     * @param context
     * @param position 位置
     * @param itemView item对应的View
     */
    public static void startTranslateAnimation(Context context, int position, View itemView) {
        AnimationSet animationSet = new AnimationSet(true);
        // 透明动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(ANIM_DURATION);
        // 位移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,   // x轴
                Animation.RELATIVE_TO_SELF, 0f,     //
                Animation.RELATIVE_TO_SELF, 1f,   // y轴
                Animation.RELATIVE_TO_SELF, 0f);    //
        translateAnimation.setDuration(ANIM_DURATION);
        // 动画开始时间逐渐延后
        if (animParentViewHeight > 0 && animItemViewHeight > 0) {
            // 可见的item数量
            int canSeeItemCount = animParentViewHeight / animItemViewHeight;
            if (position < canSeeItemCount + 1) {
                animationSet.setStartOffset(position * 100);
            }
        }
        animationSet.addAnimation(translateAnimation); // 位移动画
        animationSet.addAnimation(alphaAnimation); // 透明动画
        animationSet.setFillAfter(true);
        itemView.startAnimation(animationSet);
    }

    /**
     * 给列表中的 item 增加 RotateAnimation 动画
     *
     * @param position 位置
     * @param itemView item 对应的 View
     */
    public static void startRotateAnimation(int position, View itemView) {
        RotateAnimation rotateAnimation = new RotateAnimation(
                0.0f, 350.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        // mRotateAnimation.setStartOffset(position * 500);
        itemView.startAnimation(rotateAnimation);
    }

    //============以上代码是给列表的Item做动画 EDN====================================================

}
