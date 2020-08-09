package com.wuguangxin.utils.demo.ui;

import android.view.View;
import android.widget.TextView;

import com.wuguangxin.utils.ToastUtils;
import com.wuguangxin.utils.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ToastUtilsActivity extends BaseActivity {

    @BindView(R.id.toast_text) TextView mTextView;
    private int count;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView() {
        setTitle("Toast工具类");
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick({R.id.show_toast})
    public void onClick(View v) {
        count++;
        mTextView.setText(String.valueOf(count));
        ToastUtils.showToast(this, "Toast " + count);
    }
}
