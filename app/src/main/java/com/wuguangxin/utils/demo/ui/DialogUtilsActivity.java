package com.wuguangxin.utils.demo.ui;

import android.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wuguangxin.utils.DialogUtils;
import com.wuguangxin.utils.NumberUtils;
import com.wuguangxin.utils.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class DialogUtilsActivity extends BaseActivity {

    @BindView(R.id.showDialog) View mShowDialog;
    @BindView(R.id.gravityRadioGroup) RadioGroup gravityRadioGroup;
    @BindView(R.id.alphaSeekBar) SeekBar alphaSeekBar;
    @BindView(R.id.alphaValue) TextView alphaValue;
    @BindView(R.id.widthSeekBar) SeekBar widthSeekBar;
    @BindView(R.id.widthValue) TextView widthValue;

    int screenWidth;
    AlertDialog dialog;
    // 位置
    int gravity = Gravity.CENTER;
    // 宽度
    int width = ViewGroup.LayoutParams.MATCH_PARENT;
    // 透明度
    float alpha;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_dialog_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        widthSeekBar.setMax(screenWidth);
        widthSeekBar.setProgress(screenWidth);

        setAlphaSeekBar(0);
        setWidthSeekBar(screenWidth);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListener() {
        gravityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.gravity_left) {
                    gravity = Gravity.LEFT;
                } else if (checkedId == R.id.gravity_top) {
                    gravity = Gravity.TOP;
                } else if (checkedId == R.id.gravity_right) {
                    gravity = Gravity.RIGHT;
                } else if (checkedId == R.id.gravity_bottom) {
                    gravity = Gravity.BOTTOM;
                } else {
                    gravity = Gravity.CENTER;
                }
                updateDialog();
            }
        });
        alphaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setAlphaSeekBar(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setWidthSeekBar(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateDialog() {
        DialogUtils.setDialog(dialog, gravity, width, alpha);
    }

    private void setAlphaSeekBar(int progress) {
        alpha = progress;
        alphaValue.setText(NumberUtils.formatPercent((float)alpha/(float)alphaSeekBar.getMax(), 2));
        updateDialog();
    }

    private void setWidthSeekBar(int progress) {
        String text;
        if (progress == 0) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
            text = "wrap_content";
        } else if (progress == screenWidth) {
            width = ViewGroup.LayoutParams.MATCH_PARENT;
            text = "match_parent";
        } else {
            width = progress;
            text = width + "px";
        }
        widthValue.setText(text);
        updateDialog();
    }


    @OnClick({
            R.id.showDialog,
    })
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.showDialog:
            if (dialog == null) {
                dialog = DialogUtils.showDialog(this, "标题", "恭喜你中大奖了！", null, null);
            } else {
                dialog.show();
            }
            break;
        }
    }
}
