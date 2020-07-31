package com.wuguangxin.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * ${DESC}
 * Created by wuguangxin on 2019/10/31.
 */
public class EditUtils {

    /**
     * 当点击百分比时，动态计算并改变对应输入框的值
     * @param money 金额
     * @param priceET 价格输入框
     * @param numET 数量输入框
     * @param scale 保留的小数点
     */
    public static void setEditTextValue(double money, EditText priceET, EditText numET, int scale) {
        if (numET == null) return;
        BigDecimal moneyBig = BigDecimal.valueOf(money);
        String price = null;
        if (priceET != null) price = priceET.getText().toString().trim();
        if (TextUtils.isEmpty(price)) price = "0";
        BigDecimal priceBig = new BigDecimal(price);
        numET.requestFocus(); // 获取焦点
        BigDecimal div = MathUtils.div(moneyBig, priceBig, scale);
        numET.setText(String.valueOf(div.doubleValue()));
        numET.setSelection(numET.getText().length());
    }
}
