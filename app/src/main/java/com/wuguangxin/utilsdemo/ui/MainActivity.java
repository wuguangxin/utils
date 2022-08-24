package com.wuguangxin.utilsdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.wuguangxin.utils.ListUtils;
import com.wuguangxin.utils.MapUtils;
import com.wuguangxin.utils.JsonUtils;
import com.wuguangxin.utilsdemo.R;
import com.wuguangxin.utilsdemo.UserBean;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;

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
        mActivityMap.put(R.id.android_utils, AndroidUtilsActivity.class);
        mActivityMap.put(R.id.dialog_utils, DialogUtilsActivity.class);
        mActivityMap.put(R.id.wave_anim, WaveViewActivity.class);

        findViewById(R.id.other).setOnClickListener(v -> {
            // ...
//            BigDecimal result = MathUtils.add(null, null, 1, "23");
//            printLogI("add = " + result);
//
//            Type type = new TypeToken<Map<String, List<A>>>(){}.getType();
//            Map<String, Object> string = JsonUtils.toMap(json, type);
//            printLogI("map == " + string);


//            ShakeUtils.vibrate(this);

            List<UserBean> list = ListUtils.toList(
                    new UserBean("a", "张三"),
                    new UserBean("b", "李四"));

            String listJson = JsonUtils.toJson(list);
            List<UserBean> list2 = JsonUtils.fromJson(listJson);

            String userJson0 = JsonUtils.toJson(list.get(0));

            UserBean userBean0 = JsonUtils.toBean(userJson0, UserBean.class);

            Map<String, String> map = new HashMap<>();
            map.put("A", "0000000");
            map.put("B", "1111111");
            map.put("C", "2222222");
            String mapJsonStr = JsonUtils.toJson(map);
            Map<Object, Object> toMap = JsonUtils.fromJson(mapJsonStr);
            printLogI("map = " + toMap);


        });
    }

    String testData = "[{\"amountChangeMin\":0.01,\"base\":\"AXS\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":106,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":93.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":3,\"simulate\":0,\"symbol\":\"AXSUSDT\",\"type\":null,\"valuePerLot\":1.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.1,\"base\":\"BTC\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":92,\"leverAgeMax\":100,\"leverAges\":[10,20,50,100],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":59000.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":1,\"simulate\":0,\"symbol\":\"BTCUSDT\",\"type\":null,\"valuePerLot\":0.01,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.01,\"base\":\"ETH\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":95,\"leverAgeMax\":100,\"leverAges\":[5,10,20,50,75,100],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":1800.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":2,\"simulate\":0,\"symbol\":\"ETHUSDT\",\"type\":null,\"valuePerLot\":0.1,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":1.0E-4,\"base\":\"EOS\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":96,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":3.9,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":4,\"simulate\":0,\"symbol\":\"EOSUSDT\",\"type\":null,\"valuePerLot\":10.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.01,\"base\":\"LTC\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":105,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":364.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":2,\"simulate\":0,\"symbol\":\"LTCUSDT\",\"type\":null,\"valuePerLot\":1.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":1.0E-5,\"base\":\"XRP\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":100,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":1.31,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":5,\"simulate\":0,\"symbol\":\"XRPUSDT\",\"type\":null,\"valuePerLot\":100.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.001,\"base\":\"FIL\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":98,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":117.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":3,\"simulate\":0,\"symbol\":\"FILUSDT\",\"type\":null,\"valuePerLot\":1.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":1.0E-5,\"base\":\"TRX\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":101,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":0.12,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":5,\"simulate\":0,\"symbol\":\"TRXUSDT\",\"type\":null,\"valuePerLot\":1000.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.001,\"base\":\"DOT\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":102,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":38.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":3,\"simulate\":0,\"symbol\":\"DOTUSDT\",\"type\":null,\"valuePerLot\":10.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":1.0E-6,\"base\":\"DOGE\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":103,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":0.4,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":6,\"simulate\":0,\"symbol\":\"DOGEUSDT\",\"type\":null,\"valuePerLot\":1000.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.001,\"base\":\"UNI\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":104,\"leverAgeMax\":75,\"leverAges\":[5,10,20,50,75],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":37.0,\"pricePre\":8,\"quote\":\"USDT\",\"showPre\":3,\"simulate\":0,\"symbol\":\"UNIUSDT\",\"type\":null,\"valuePerLot\":10.0,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0},{\"amountChangeMin\":0.1,\"base\":\"BTC\",\"closePrice\":0.0,\"cnyPrice\":null,\"description\":null,\"direction\":null,\"fee\":5.0E-4,\"fundingFee\":1.0,\"highPrice\":0.0,\"id\":97,\"leverAgeMax\":100,\"leverAges\":[10,20,50,100],\"lowPrice\":0.0,\"minMaintenanceMarginRate\":20,\"openPrice\":50000.0,\"pricePre\":8,\"quote\":\"SUSDT\",\"showPre\":1,\"simulate\":1,\"symbol\":\"BTCSUSDT\",\"type\":null,\"valuePerLot\":0.01,\"volume\":null,\"volumeMin\":1.0,\"volumePre\":0}]";

    public static class Abs {

    }


    public static class A {
        String initial;

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }
    }

    String json = "{\n" +
            "\"A\":[\n" +
            "{\n" +
            "\"initial\":\"A1\"\n" +
            "},\n" +
            "{\n" +
            "\"initial\":\"A2\"\n" +
            "}\n" +
            "],\n" +
            "\"B\":[\n" +
            "{\n" +
            "\"initial\":\"B1\"\n" +
            "},\n" +
            "{\n" +
            "\"initial\":\"B2\"\n" +
            "},\n" +
            "{\n" +
            "\"initial\":\"B3\"\n" +
            "},\n" +
            "{\n" +
            "\"initial\":\"B4\"\n" +
            "}\n" +
            "]\n" +
            "}";

    static class Desc implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public static BigDecimal add(BigDecimal... v) {
        BigDecimal result = BigDecimal.ZERO;
        for (BigDecimal bigDecimal : v) {
            result = result.add(bigDecimal);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                showToast("获取权限（READ_CONTACTS）成功");
            }
        }
    }

    @Override
    public void initData() {
    }


    @Override
    public void setListener() {
        List<Integer> keyList = MapUtils.getKeys(mActivityMap);
        for (Integer id : keyList) findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        openActivity(mActivityMap.get(view.getId()));
    }
}