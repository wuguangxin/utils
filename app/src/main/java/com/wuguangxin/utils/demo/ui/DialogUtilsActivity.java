package com.wuguangxin.utilsdemo.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wuguangxin.utils.DialogUtils;
import com.wuguangxin.utils.NumberUtils;
import com.wuguangxin.utilsdemo.R;

public class DialogUtilsActivity extends BaseActivity {

    private RadioGroup gravityRadioGroup;
    private SeekBar alphaSeekBar;
    private TextView alphaValue;
    private SeekBar widthSeekBar;
    private TextView widthValue;

    int screenWidth;
    AlertDialog dialog;
    // 位置
    int gravity = Gravity.CENTER;
    // 宽度
    int width = ViewGroup.LayoutParams.MATCH_PARENT;
    // 透明度0-1
    float alpha;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_dialog_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());

        gravityRadioGroup = findViewById(R.id.gravityRadioGroup);
        alphaSeekBar = findViewById(R.id.alphaSeekBar);
        alphaValue = findViewById(R.id.alphaValue);
        widthSeekBar = findViewById(R.id.widthSeekBar);
        widthValue = findViewById(R.id.widthValue);

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

        findViewById(R.id.showDialog).setOnClickListener(v -> {
            if (dialog == null) {
                dialog = showDialog(context, null, null);
            } else {
                dialog.show();
            }
        });
    }

    private void updateDialog() {
        DialogUtils.setDialog(dialog, gravity, width, alpha);
    }

    private void setAlphaSeekBar(int progress) {
        alpha = progress / 100f;
        alphaValue.setText(NumberUtils.formatPercent(alpha, 2));
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

    public static AlertDialog showDialog(final Context context, final Intent posIntent, final Intent negIntent) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (negIntent != null) {
            dialog.setNegativeButton("取消", (dialog12, which) -> {
                dialog12.cancel();
                context.startActivity(negIntent);
            });
        }
        dialog.setPositiveButton("确定", (dialog1, which) -> {
            dialog1.cancel();
            if (posIntent != null) {
                context.startActivity(posIntent);
            }
        });

        AlertDialog alertDialog = dialog.create();
        View view = View.inflate(context, R.layout.dialog_edit, null);
        EditText editText = view.findViewById(R.id.edit_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        alertDialog.setView(view);
        alertDialog.show();
        return alertDialog;
    }
}
