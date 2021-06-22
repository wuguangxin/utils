package com.wuguangxin.utils.demo.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.reflect.TypeToken;
import com.wuguangxin.utils.MD5;
import com.wuguangxin.utils.MmkvUtils;
import com.wuguangxin.utils.demo.R;
import com.wuguangxin.utils.demo.UserBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MmkvUtilsActivity extends BaseActivity {
    @BindView(R.id.edit_text) EditText mEditText;
    @BindView(R.id.user_radio_group) RadioGroup mUserRadioGroup;
    @BindView(R.id.user_1) RadioButton mUser1;
    @BindView(R.id.user_2) RadioButton mUser2;
    private List<UserBean> userList;
    private UserBean currentUser;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mmkv_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
        userList = new ArrayList<>();
        userList.add(new UserBean(MD5.encode("0"), "张三"));
        userList.add(new UserBean(MD5.encode("1"), "李四"));

        currentUser = userList.get(0); // 记录当前用户
        mUser1.setChecked(true);
        mUser1.setText(userList.get(0).getUsername());
        mUser1.setHint(userList.get(0).getId());

        mUser2.setText(userList.get(1).getUsername());
        mUser2.setHint(userList.get(1).getId());
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mUserRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 切换用户
                String id = null;
                if (checkedId == R.id.user_1) {
                    id = mUser1.getHint().toString();
                } else if (checkedId == R.id.user_2) {
                    id = mUser2.getHint().toString();
                }
                for (UserBean userBean : userList) {
                    if (userBean.getId().equals(id)) {
                        currentUser = userBean;
                        MmkvUtils.switchUser(id);
                        return;
                    }
                }
            }
        });
    }

    final String KEY_TEST = "text";
    final String KEY_LIST = "list";
    final String KEY_MAP = "map";
    final String KEY_BEAN = "bean";

    @OnClick({
            R.id.put, R.id.get, R.id.del,
            R.id.put_list, R.id.get_list, R.id.del_list,
            R.id.put_map, R.id.get_map, R.id.del_map,
            R.id.put_bean, R.id.get_bean, R.id.del_bean,
    })
    public void onClicked(View view) {
        int id = view.getId();
        switch (id) {
            // 基本数据
        case R.id.put:
            showToast(currentUser.getUsername() + (MmkvUtils.put(KEY_TEST, getText()) ? "存入成功" : "存入失败"));
            break;
        case R.id.get:
            showToast(currentUser.getUsername() + "取出：" + MmkvUtils.get(KEY_TEST, (String) null));
            break;
        case R.id.del:
            MmkvUtils.removeValueByKey(KEY_TEST);
            showToast(currentUser.getUsername() + "删除信息：" + !MmkvUtils.containsKey(KEY_TEST));
            break;

            // 操作List
        case R.id.put_list:
            showToast(currentUser.getUsername() + (MmkvUtils.putList(KEY_LIST, list2) ? "List存入成功" : "List存入失败"));
            break;
        case R.id.get_list:
            showToast(currentUser.getUsername() + "取出List：" + MmkvUtils.getList(KEY_LIST, new TypeToken<List<Map<String, String>>>() {}.getType()));
            break;
        case R.id.del_list:
            MmkvUtils.removeValueByKey(KEY_LIST);
            showToast(currentUser.getUsername() + "删除List：" + !MmkvUtils.containsKey(KEY_LIST));
            break;

            // 操作Map
        case R.id.put_map:
            showToast(currentUser.getUsername() + (MmkvUtils.putMap(KEY_MAP, map1) ? "Map存入成功" : "Map存入失败"));
            break;
        case R.id.get_map:
            showToast(currentUser.getUsername() + "取出Map：" + MmkvUtils.getMap(KEY_MAP, new TypeToken<Map<String, String>>() {}.getType()));
            break;
        case R.id.del_map:
            MmkvUtils.removeValueByKey(KEY_MAP);
            showToast(currentUser.getUsername() + "删除Map：" + !MmkvUtils.containsKey(KEY_MAP));
            break;

            // 操作Bean
        case R.id.put_bean:
            showToast(currentUser.getUsername() + (MmkvUtils.putBean(KEY_BEAN, currentUser) ? "Bean存入成功" : "Bean存入失败"));
            break;
        case R.id.get_bean:
            UserBean userBean = MmkvUtils.getBean(KEY_BEAN, new TypeToken<UserBean>() {}.getType());
            showToast(currentUser.getUsername() + "取出Bean：" + userBean);
            break;
        case R.id.del_bean:
            MmkvUtils.removeValueByKey(KEY_BEAN);
            showToast(currentUser.getUsername() + "删除Bean：" + !MmkvUtils.containsKey(KEY_BEAN));
            break;
        }
    }

    public static Map<String, String> map1 = new HashMap<>();
    public static Map<String, String> map2 = new HashMap<>();
    public static List<Map<String, String>> list2 = new ArrayList<>();
    static  {
        map1.put("吴光新", "23");
        map1.put("刘德华", "24");
        map2.put("语文", "100");
        map2.put("数学", "100");
        list2.add(map1);
        list2.add(map2);

    }

    private String getText() {
        return mEditText.getText().toString();
    }

}