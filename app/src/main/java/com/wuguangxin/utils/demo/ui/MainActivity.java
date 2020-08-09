package com.wuguangxin.utils.demo.ui;

import android.app.Activity;
import android.view.View;

import com.wuguangxin.utils.MapUtils;
import com.wuguangxin.utils.demo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    // 触发点击事件的ViewID/要打开的Activity Class
    Map<Integer, Class<? extends Activity>> mActivityMap;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setTitle("UtilsDemo");
        mActivityMap = new HashMap<>();
        mActivityMap.put(R.id.anim_utils, AnimUtilsActivity.class);
        mActivityMap.put(R.id.mmkv_utils, MmkvUtilsActivity.class);
        mActivityMap.put(R.id.shot_key_board, SoftKeyBoardActivity.class);
        mActivityMap.put(R.id.toast_utils, ToastUtilsActivity.class);
        mActivityMap.put(R.id.storage_utils, StorageUtilsActivity.class);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListener() {
        List<Integer> keyList = MapUtils.getKeyList(mActivityMap);
        for (Integer id : keyList) findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        openActivity(mActivityMap.get(view.getId()));
    }
}