package com.wuguangxin.utils.demo.ui;

import android.widget.TextView;

import com.wuguangxin.utils.StorageUtils;
import com.wuguangxin.utils.demo.R;

import butterknife.BindView;

public class StorageUtilsActivity extends BaseActivity {

    @BindView(R.id.storage_info) TextView mTextView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    public void initView() {
        setTitle("Toast工具类");
        StorageUtils.test(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
