package com.wuguangxin.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Bitmap、Drawable、InputStream和byte[] 之间互相转换的（single）工具类。
 *
 * {@link #fileToDrawable(File)}
 * {@link #fileToBitmap(File)}
 *
 * {@link #drawableToBitmap(Drawable)}
 * {@link #drawableToInputStream(Drawable)}
 * {@link #drawableToBitmap(Drawable)}
 * {@link #drawableToBytes(Drawable)}
 *
 * {@link #bitmapToDrawable(Bitmap)}
 * {@link #bitmapToInputStream(Bitmap)}
 * {@link #bitmapToInputStream(Bitmap, int)}
 * {@link #bitmapToBytes(Bitmap)}
 * {@link #bitmapToBytes(Bitmap, int)}
 *
 * {@link #bytesToDrawable(byte[])}
 * {@link #bytesToBitmap(byte[])}
 * {@link #bytesToInputStream(byte[])}
 *
 * {@link #inputStreamToBitmap(InputStream)}
 * {@link #inputStreamToDrawable(InputStream)}
 * {@link #inputStreamToBytes(InputStream)}
 *
 * {@link #uriToBitmap(Context, Uri)}
 *
 * {@link #readStream(InputStream)}
 */
public class IOFormat {

    private static IOFormat mIOFormat;

    public static IOFormat getInstance() {
        if (mIOFormat == null) {
            synchronized (IOFormat.class) {
                if (mIOFormat == null) {
                    mIOFormat = new IOFormat();
                }
            }
        }
        return mIOFormat;
    }

    // ########################### File start ##################################################
    // file2Drawable
    // file2Bitmap

    /**
     * File 转换成 Drawable
     *
     * @param file 图片文件
     * @return
     */
    public Drawable fileToDrawable(File file) {
        if (file == null || !file.exists()) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return inputStreamToDrawable(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * File 转换成 Bitmap
     *
     * @param file 图片文件
     * @return
     */
    public Bitmap fileToBitmap(File file) {
        if (file == null || !file.exists()) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            return inputStreamToBitmap(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // ########################### Drawable start ##################################################
    // drawableToInputStream
    // drawableToBitmap
    // drawableToBytes

    /**
     * Drawable 转换成 Bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        boolean isOpaque = drawable.getOpacity() != PixelFormat.OPAQUE;
        Bitmap.Config config = isOpaque ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Drawable 转换为 File
     * @param drawable  Drawable
     * @param targetFile 目标文件
     * @return
     */
    public File drawableToFile(Drawable drawable, File targetFile){
        return bitmapToFile(drawableToBitmap(drawable), targetFile);
    }

    /**
     * Drawable转换成InputStream
     *
     * @param drawable Drawable
     * @return InputStream
     */
    public InputStream drawableToInputStream(Drawable drawable) {
        return bitmapToInputStream(drawableToBitmap(drawable));
    }

    /**
     * Drawable转换成byte[]
     *
     * @param drawable Drawable
     * @return byte[]
     */
    public byte[] drawableToBytes(Drawable drawable) {
        return bitmapToBytes(drawableToBitmap(drawable));
    }

    // ########################### Bitmap start ####################################################
    // bitmapToDrawable
    // bitmapToInputStream
    // bitmapToBytes

    /**
     * Bitmap转换成Drawable
     *
     * @param bitmap Bitmap
     * @return Drawable
     */
    public Drawable bitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) return null;
        return new BitmapDrawable(bitmap);
    }

    /**
     * 将Bitmap转换成InputStream
     *
     * @param bitmap Bitmap
     * @return InputStream
     */
    public InputStream bitmapToInputStream(Bitmap bitmap) {
        return bitmapToInputStream(bitmap, 100);
    }

    /**
     * 将Bitmap转换成InputStream,并指定压缩质量
     *
     * @param bitmap Bitmap
     * @param quality 压缩质量(0-100)，如果是PNG格式，将忽略压缩质量，按无损压缩
     * @return InputStream
     */
    public InputStream bitmapToInputStream(Bitmap bitmap, int quality) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        return bais;
    }

    /**
     * Bitmap 转换成 byte[]
     *
     * @param bitmap Bitmap
     * @return byte[]
     */
    public byte[] bitmapToBytes(Bitmap bitmap) {
        return bitmapToBytes(bitmap, 100);
    }

    /**
     * Bitmap 转换成 byte[]
     *
     * @param bitmap Bitmap
     * @param quality 质量 （0-100）
     * @return byte[]
     */
    public byte[] bitmapToBytes(Bitmap bitmap, int quality) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return baos.toByteArray();
    }

    /**
     * Bitmap 转换为 File
     * @param bitmap
     * @param targetFile
     * @return
     */
    public File bitmapToFile(Bitmap bitmap, File targetFile) {
        FileOutputStream fos = null;
        try {
            if (targetFile != null) {
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                fos = new FileOutputStream(targetFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile;
    }

    // ########################### Bytes start #####################################################
    // bytesToDrawable
    // bytesToBitmap
    // bytesToInputStream

    /**
     * byte[] 转换成 Drawable
     *
     * @param bytes byte[]
     * @return Drawable
     */
    public Drawable bytesToDrawable(byte[] bytes) {
        return bitmapToDrawable(bytesToBitmap(bytes));
    }

    /**
     * byte[] 转换成 Bitmap
     *
     * @param bytes byte[]
     * @return Bitmap
     */
    public Bitmap bytesToBitmap(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * byte[] 转换成 InputStream
     *
     * @param bytes byte[]
     * @return InputStream
     */
    public InputStream bytesToInputStream(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        return new ByteArrayInputStream(bytes);
    }

    // ########################### InputStream start ###############################################
    // inputStreamToBitmap
    // inputStreamToDrawable
    // inputStreamToBytes

    /**
     * InputStream 转换成 Bitmap
     *
     * @param inputStream InputStream
     * @return Bitmap
     */
    public Bitmap inputStreamToBitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    /**
     * InputStream 转换成 Drawable
     *
     * @param inputStream InputStream
     * @return Drawable
     */
    public Drawable inputStreamToDrawable(InputStream inputStream) {
        return bitmapToDrawable(inputStreamToBitmap(inputStream));
    }

    /**
     * InputStream 转换成 byte[]
     *
     * @param inputStream InputStream
     * @return byte[]
     */
    public byte[] inputStreamToBytes(InputStream inputStream) {
        byte[] readByte = new byte[1024];
        StringBuilder sb = new StringBuilder();
        try {
            while (inputStream.read(readByte, 0, 1024) != -1) {
                sb.append(new String(readByte));
            }
            return sb.toString().getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ########################### other ###########################################################
    // uriToBitmap

    /**
     * 将绑定的URI转换为Bitmap返回
     *
     * @param uri Uri
     * @return Bitmap
     */
    public Bitmap uriToBitmap(Context context, Uri uri) {
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            if (is != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap != null) return bitmap;
                byte[] data = readStream(is);
                if (data.length > 0) {
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) {
                        return bitmap;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成与原图同样大小的Bitmap，不作压缩
     *
     * @param imageUrl 图片URL
     * @return Bitmap
     */
    public Bitmap urlToBitmap(String imageUrl) {
        try {
            return BitmapFactory.decodeFile(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到图片字节流 数组大小
     *
     * @param inStream InputStream
     * @return byte[]
     * @throws Exception
     */
    public byte[] readStream(InputStream inStream) throws Exception {
        int len;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 简单获取网落图片资源
     *
     * @param url String
     * @return Bitmap
     */
    public Bitmap getBitmapFromUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        InputStream is = null;
        URL imgUrl;
        try {
            imgUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            // conn.setConnectionTiem(0); // 表示没有时间限制
            conn.setConnectTimeout(6000); // 超时时间
            conn.setDoInput(true);
            conn.setUseCaches(false); // 不使用缓存
            // conn.connect(); // 这句可有可无，没有影响
            is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}