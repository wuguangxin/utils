package com.wuguangxin.utils.demo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.wuguangxin.utils.Logger;
import com.wuguangxin.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mBinder;
    protected Context context;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        context = this;
        mBinder = ButterKnife.bind(this);
        setTitle(getTitleByActivity());
        initView();
        initData();
        setListener();
    }

    public abstract int getLayoutRes();

    public abstract void initView();

    public abstract void initData();

    public abstract void setListener();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinder != null) mBinder.unbind();
    }

    public void openActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void showToast(String text) {
        ToastUtils.showToast(this, text);
        printLogI(text);
    }

    public String getTitleByActivity() {
        return this.getClass().getSimpleName().replace("Activity", "");
    }

    public String getSimpleTitle() {
        return getClass().getSimpleName().replace("Activity", "");
    }

    public void printLogI(String text) {
        Logger.i(this, text);
    }
}