package com.wuguangxin.utilsdemo.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wuguangxin.utils.Logger;
import com.wuguangxin.utils.ToastUtils;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        context = this;
        setTitle(getTitleByActivity());
        initView();
        initData();
        setListener();
    }

    public abstract int getLayoutRes();

    public abstract void initView();

    public abstract void initData();

    public abstract void setListener();

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

    public void setClickIds(View.OnClickListener clickListener, int... clickViewIds) {
        if (clickViewIds.length > 0) {
            for (int id : clickViewIds) {
                findViewById(id).setOnClickListener(clickListener);
            }
        }
    }
}