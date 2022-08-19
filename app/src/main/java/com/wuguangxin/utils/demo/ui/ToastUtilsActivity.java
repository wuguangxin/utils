package com.wuguangxin.utils.demo.ui;

import android.widget.TextView;

import com.wuguangxin.utils.ToastUtils;
import com.wuguangxin.utils.demo.R;

public class ToastUtilsActivity extends BaseActivity {

    private TextView mTextView;
    private int count;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
        mTextView = findViewById(R.id.toast_text);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        findViewById(R.id.show_toast).setOnClickListener(v -> {
            count++;
            mTextView.setText(String.valueOf(count));
            ToastUtils.showToast(ToastUtilsActivity.this, "Toast " + count);
        });
    }
}
