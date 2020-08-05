package com.wuguangxin.utils;

import android.text.TextUtils;

/**
 * 银行卡号格式化工具类。
 * Created by wuguangxin on 15/6/14.
 */
public class BankCardUtils {

//	public static void main(String[] args){
//		String card = "622588141420743";
//		System.out.println("      card: " + card);
//		System.out.println("check code: " + getBankCardCheckCode(card));
//		System.out.println("   card id: " + card + getBankCardCheckCode(card));
//	}

    /**
     * 校验是否是有效银行卡号
     *
     * @param bankCardNumber 银行卡号
     * @return 是否正确
     */
    public static boolean isBankCard(String bankCardNumber) {
    	if (bankCardNumber != null) {
			bankCardNumber = bankCardNumber.replaceAll(" ", "");
			String reg = "[1-9]\\d{15,18}"; // 第一位必须是1-9的16-19位数字串
			if (!TextUtils.isEmpty(bankCardNumber) && bankCardNumber.matches(reg)) {
				String card = bankCardNumber.substring(0, bankCardNumber.length() - 1);
				if (!TextUtils.isEmpty(card)) {
					char bit = getBankCardCheckCode(card);
					return bankCardNumber.charAt(bankCardNumber.length() - 1) == bit;
				}
			}
		}
        return false;
    }

    /**
     * 从不含校验位的银行卡号采用 Luhm 校验算法获得校验位。
     *
     * @param bankCardNumber 银行卡号
     * @return
     */
    private static char getBankCardCheckCode(String bankCardNumber) {
        char[] chs = bankCardNumber.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 隐藏银行卡号中间的号码。
     *
     * @param bankCardNumber 银行卡号
     * @return 返回隐藏好的卡号字符串
     */
    public static String formatHint(String bankCardNumber) {
        if (!TextUtils.isEmpty(bankCardNumber)) {
            String e4 = bankCardNumber.substring(bankCardNumber.length() - 4, bankCardNumber.length());
            return bankCardNumber.substring(0, 4) + " **** **** " + e4;
        }
        return bankCardNumber;
    }
}