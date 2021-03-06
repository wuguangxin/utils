package com.wuguangxin.utils.demo.ui;

import android.view.View;
import android.widget.EditText;

import com.wuguangxin.utils.SoftHideKeyBoardUtil;
import com.wuguangxin.utils.demo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class SoftKeyBoardActivity extends BaseActivity {
    @BindView(R.id.edit) EditText mEditText;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_soft_key_board;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
    }

    @OnClick({
            R.id.show, R.id.hide, R.id.toggle, R.id.pick,
    })
    public void onClicked(View view) {
        int id = view.getId();
        switch (id) {
        case R.id.show:
            SoftHideKeyBoardUtil.showSoftInput(mEditText);
            break;
        case R.id.hide:
            SoftHideKeyBoardUtil.hideSoftInput(this);
            break;
        case R.id.toggle:
            SoftHideKeyBoardUtil.toggleSoftInput(this);
            break;
        case R.id.pick:
            SoftHideKeyBoardUtil.showInputMethodPicker(this);
            break;
        }
    }

    private String getText() {
        return mEditText.getText().toString();
    }

}