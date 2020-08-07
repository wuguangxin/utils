package com.wuguangxin.utils.demo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wuguangxin.utils.ToastUtils;
import com.wuguangxin.utils.demo.R;

import androidx.annotation.Nullable;

public class ToastUtilsActivity extends Activity {

    private TextView mTextView;
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        setTitle("Toast工具类");
        mTextView = findViewById(R.id.toast_text);

        findViewById(R.id.show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                mTextView.setText(String.valueOf(count));
                ToastUtils.showToast(ToastUtilsActivity.this, "Toast " + count);
            }
        });
    }
}
