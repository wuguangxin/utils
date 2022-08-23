package com.wuguangxin.utilsdemo.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wuguangxin.utils.Logger;
import com.wuguangxin.utils.MD5;
import com.wuguangxin.utilsdemo.AppConfig;
import com.wuguangxin.utilsdemo.R;
import com.wuguangxin.utilsdemo.UserBean;
import com.wuguangxin.utils.mmkv.MmkvUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MmkvUtilsActivity extends BaseActivity {
    private EditText mEditText;
    private RadioGroup mUserRadioGroup;
    private RadioButton mUser1;
    private RadioButton mUser2;
    private List<UserBean> userList;
    private UserBean currentUser;

    final String KEY_TEST = "text";
    final String KEY_LIST = "list";
    final String KEY_MAP = "map";
    final String KEY_BEAN = "bean";

    @Override
    public int getLayoutRes() {
        return R.layout.activity_mmkv_utils;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());

        mEditText = findViewById(R.id.edit_text);
        mUserRadioGroup = findViewById(R.id.user_radio_group);
        mUser1 = findViewById(R.id.user_1);
        mUser2 = findViewById(R.id.user_2);

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
        try {
            test();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void test() throws JSONException {
        UserBean user1 = new UserBean("1", "一");
        UserBean user2 = new UserBean("2", "二");
        UserBean user3 = new UserBean("3", "三");

        AppConfig.getInstance().put("user1", user1);
        UserBean getUser1 = AppConfig.getInstance().getSerializable("user1", UserBean.class);
        Logger.i("user1", getUser1);

        Map<String, UserBean> userMap = new HashMap<>();
        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);

        List<Object> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add(userMap);
        AppConfig.getInstance().put("list", list);
        Logger.i("list", AppConfig.getInstance().getList("list"));

        List<UserBean> userList = Arrays.asList(user1, user2, user3);
        AppConfig.getInstance().putList("userList", userList);
        Logger.i("userList", AppConfig.getInstance().getList("userList"));

        AppConfig.getInstance().putMap("userMap", userMap);
        Logger.i("userMap", AppConfig.getInstance().getMap("userMap"));

        UserBean userBean = new UserBean("1", "WGX");
        AppConfig.getInstance().putSerializable("userBean", userBean);
        Logger.i("userBean", AppConfig.getInstance().getSerializable("userBean", UserBean.class));

        Set<String> sets = new HashSet<>();
        sets.add("AAA");
        sets.add("BBB");
        sets.add("CCC");
        Logger.i("sets", AppConfig.getInstance().putStringSet("sets", sets).getStringSet("sets"));

        BigDecimal bigDecimal = BigDecimal.valueOf(100.123454565656D);
        Logger.i("bigDecimal", AppConfig.getInstance().putBigDecimal("bigDecimal", bigDecimal).getBigDecimal("bigDecimal"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        Logger.i("jsonObject", AppConfig.getInstance().putJSONObject("jsonObject", jsonObject).getJSONObject("jsonObject"));

        JSONArray jsonArray = new JSONArray();
        jsonArray.put("aaa");
        jsonArray.put(jsonObject);
        Logger.i("jsonArray", AppConfig.getInstance().putJSONArray("jsonArray", jsonArray).getJSONArray("jsonArray"));
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

        setClickIds(this::onClicked, R.id.put, R.id.get, R.id.del,
                R.id.put_list, R.id.get_list, R.id.del_list,
                R.id.put_map, R.id.get_map, R.id.del_map,
                R.id.put_bean, R.id.get_bean, R.id.del_bean);
    }

    public void onClicked(View view) {
        int id = view.getId();
        switch (id) {
            // 基本数据
        case R.id.put:
            MmkvUtils.putString(KEY_TEST, getText());
            showToast(currentUser.getUsername() + "存入成功");
            break;
        case R.id.get:
            showToast(currentUser.getUsername() + "取出：" + MmkvUtils.getString(KEY_TEST, null));
            break;
        case R.id.del:
            MmkvUtils.removeValueForKey(KEY_TEST);
            showToast(currentUser.getUsername() + "删除信息：" + !MmkvUtils.containsKey(KEY_TEST));
            break;

            // 操作List
        case R.id.put_list:
            MmkvUtils.putList(KEY_LIST, list2);
            showToast(currentUser.getUsername() + "List存入成功");
            break;
        case R.id.get_list:
            showToast(currentUser.getUsername() + "取出List：" + MmkvUtils.getList(KEY_LIST));
            break;
        case R.id.del_list:
            MmkvUtils.removeValueForKey(KEY_LIST);
            showToast(currentUser.getUsername() + "删除List：" + !MmkvUtils.containsKey(KEY_LIST));
            break;

            // 操作Map
        case R.id.put_map:
            MmkvUtils.putMap(KEY_MAP, map1);
            showToast(currentUser.getUsername() + ("Map存入成功"));
            break;
        case R.id.get_map:
            // Type type = new TypeToken<Map<String, String>>() {}.getType());
            showToast(currentUser.getUsername() + "取出Map：" + MmkvUtils.getMap(KEY_MAP, null));
            break;
        case R.id.del_map:
            MmkvUtils.removeValueForKey(KEY_MAP);
            showToast(currentUser.getUsername() + "删除Map：" + !MmkvUtils.containsKey(KEY_MAP));
            break;

            // 操作Bean
        case R.id.put_bean:
            MmkvUtils.putSerializable(KEY_BEAN, currentUser);
            showToast(currentUser.getUsername() + "Bean存入成功");
            break;
        case R.id.get_bean:
            // Type type = new TypeToken<UserBean>() {}.getType();
            UserBean userBean = MmkvUtils.getSerializable(KEY_BEAN, UserBean.class);
            showToast(currentUser.getUsername() + "取出Bean：" + userBean);
            break;
        case R.id.del_bean:
            MmkvUtils.removeValueForKey(KEY_BEAN);
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