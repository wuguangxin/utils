package com.wuguangxin.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码生成工具类
 * Created by wuguangxin on 16/11/15.
 */
public class ZXingUtils {

    /**
     * 创建二维码
     *
     * @param text 内容
     * @return
     */
    public static Bitmap create2DCode(String text) {
        try {
            // 获取屏幕宽高
            int maxWidth = 500;
            int maxHeight = 500;
            // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
            BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, maxWidth, maxHeight);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            // 二维矩阵转为一维像素数组,也就是一直横着排列
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap,具体参考api
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 创建条形码，目前不支持中文生成条形码
     *
     * @param text 内容
     * @return
     */
    public static Bitmap create1DCode(String text) {
        if (isCNString(text)) {
            return null;
        }
        try {
            // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
            BitMatrix matrix = null;
            matrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 500, 200);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // 通过像素数组生成bitmap,具体参考api
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断是否含有中文字符
     *
     * @param string 字符串
     * @return
     */
    public static boolean isCNString(String string) {
        for (int i = 0; i < string.length(); i++) {
            int c = string.charAt(i);
            if ((19968 <= c && c < 40623)) {
                return true;
            }
        }
        return false;
    }

}
