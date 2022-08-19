package com.wuguangxin.utils.demo.ui;

import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.wuguangxin.utils.demo.R;
import com.wuguangxin.utils.demo.view.ProgressWaveView;

/**
 * 波浪View
 *
 * Created by wuguangxin on 2022/4/8.
 */
public class WaveViewActivity extends BaseActivity {

    private AnimationDrawable animDrawable;

    private ProgressWaveView extremeRainVolAnim;
    private ImageView extremeRainVolAnim2;

    private ImageView bubble1;
    private ImageView bubble2;
    private ImageView bubble3;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_wave_view;
    }

    @Override
    public void initView() {
        setTitle("");

        extremeRainVolAnim = findViewById(R.id.extreme_rain_vol_anim);
        extremeRainVolAnim2 = findViewById(R.id.extreme_rain_vol_anim2);
        bubble1 = findViewById(R.id.bubble1);
        bubble2 = findViewById(R.id.bubble2);
        bubble3 = findViewById(R.id.bubble3);

        if (extremeRainVolAnim != null) {
            extremeRainVolAnim.startAnim();
            float highRatio = extremeRainVolAnim.getProgress();
            bubble1.startAnimation(getTopOut(3000, -0.2F, highRatio));
            bubble2.startAnimation(getTopOut(3500, 0F, highRatio));
            bubble3.startAnimation(getTopOut(4000, 0.2F, highRatio));
        }

        extremeRainVolAnim2.setImageResource(R.drawable.anim_wave_5);
        animDrawable = (AnimationDrawable) extremeRainVolAnim2.getDrawable();
        if (animDrawable != null) {
            animDrawable.start();
        }
    }

    /**
     * 从顶部滑出的位移动画。
     *
     * @param duration 动画时长
     * @param offset 居中偏移量
     * @param highRatio 高度比例
     * @return
     */
    public static AnimationSet getTopOut(long duration, float offset, float highRatio) {
        // 位移
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0F,
                Animation.RELATIVE_TO_PARENT, 0.0F + offset,
                Animation.RELATIVE_TO_PARENT, highRatio,
                Animation.RELATIVE_TO_PARENT, -1.0F);
        setConfig(translate, duration);

        // 透明
        AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0f);
        setConfig(alpha, duration);

        // 缩放
        ScaleAnimation scale = new ScaleAnimation(
                0.0F, 2.0F, 0.0F, 2.0F,
                Animation.RELATIVE_TO_SELF, 0.5F, // 50%
                Animation.RELATIVE_TO_SELF, 0.5F); // 50%
        setConfig(scale, duration);

        AnimationSet set = new AnimationSet(false);
        set.setInterpolator(new AccelerateInterpolator());
        set.addAnimation(translate);
        set.addAnimation(alpha);
        set.addAnimation(scale);
//
//        set.setRepeatMode(Animation.RESTART);
//        set.setDuration(duration);
//        set.setFillEnabled(true);
//        set.setFillAfter(false); // 让动画停留在最后一帧
        return set;
    }

    private static void setConfig(Animation anim, long duration) {
        anim.setRepeatMode(Animation.INFINITE);
        anim.setDuration(duration);
        anim.setFillEnabled(true);
        anim.setFillAfter(false); // 让动画停留在最后一帧
        anim.setRepeatCount(1000);
        anim.setRepeatMode(ValueAnimator.INFINITE);
    }

    /**
     * 自身三倍进行放大+淡入的组合动画。
     *
     * @param duration 动画时长
     * @return
     */
    public static AnimationSet getZoomOut(int duration) {
        AlphaAnimation alpha = new AlphaAnimation(0.0F, 1.0F);
        alpha.setDuration(duration);
        alpha.setRepeatCount(0);

        ScaleAnimation scale = new ScaleAnimation(
                3.0F, 1.0F, 3.0F, 1.0F,
                Animation.RELATIVE_TO_PARENT, 0.5F, // 50%
                Animation.RELATIVE_TO_PARENT, 0.5F); // 50%
        scale.setDuration(duration);
        scale.setRepeatCount(0);

        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new AccelerateInterpolator());
        set.addAnimation(alpha);
        set.addAnimation(scale);

        return set;
        // return loadAnim(context, R.anim.xin_anim_zoomout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

}
