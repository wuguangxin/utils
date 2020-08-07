package com.wuguangxin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 文件操作工具类
 * 参考资料 http://www.tuicool.com/articles/AvUnqiy
 * file:///android_asset/
 * Created by wuguangxin on 2014/5/5
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    private static WeakReference<Context> contextWeakReference;

    /**
     * 初始化
     * @return
     */
    public static void init(Context context) {
        contextWeakReference = new WeakReference<>(context);
    }

    private static Context getContext() {
        if (contextWeakReference != null) {
            return contextWeakReference.get();
        }
        return null;
    }

    /**
     * 复制文件
     *
     * @param context
     * @param fileName
     * @param targetFile 目标文件
     */
    public static void copyAssets(Context context, String fileName, File targetFile) {
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            copyInputStream(is, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
        }
    }

    /**
     * 复制流
     *
     * @param is 要复制的文件InputStream
     * @param targetFile 目标文件路径
     */
    public static void copyInputStream(InputStream is, File targetFile) {
        if (is == null || targetFile == null) {
            return;
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(is);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            out = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] bytes = new byte[1024];
            int by;
            while ((by = in.read(bytes)) != -1) {
                out.write(bytes, 0, by);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in, is);
            close(out);
        }
    }

    /**
     * 异步把文件从Assets下复制到data/data/下
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param targetFileDir 目标文件所在目录
     */
    public static void copyRawFile(Context context, int resId, String fileName, File targetFileDir) {
        File targetFile = new File(targetFileDir, fileName); // 目标文件：data/data/下的缓存文件
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(resId);
            copyInputStream(is, targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
        }
    }

    /**
     * 复制assets中的文件到指定目录下
     *
     * @param context
     * @param assetsFileName
     * @param targetPath
     * @return
     */
    public static void copyAssetsFile(Context context, String assetsFileName, String targetPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getAssets().open(assetsFileName);
            Log.i(TAG, "in.available() = " + in.available());
            Log.i(TAG, "data/ 下的大小为 = " + new File(context.getFilesDir(), assetsFileName).length());
            out = new FileOutputStream(targetPath + File.separator + assetsFileName);
            byte[] buf = new byte[in.available()];
            int count = 0;
            while ((count = in.read(buf)) > 0) {
                out.write(buf, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in);
            close(out);
        }
    }

    /**
     * 异步复制assets中的文件夹到指定目录下
     *
     * @param context
     * @param dirName Assets目录下的文件夹(不能是子目录)
     * @param savePath 目标文件夹
     */
    public static void copyAssetsDir(final Context context, final String dirName, final String savePath) {
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    File saveFilePath = new File(savePath + File.separator + dirName);
                    if (!saveFilePath.exists()) {
                        saveFilePath.mkdirs();
                    }
                    String[] fileNames = context.getAssets().list(dirName);
                    InputStream is = null;
                    for (String fileName : fileNames) {
                        String name = dirName + File.separator + fileName;
                        // 如果是文件，则直接拷贝，如果是文件夹，就会抛出异常，捕捉后递归拷贝
                        try {
                            is = context.getAssets().open(name);
                            copyAssetsFile(context, name, savePath);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            close(is);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    /**
     * 删除文件夹（未测试）
     *
     * @param fileDirPath 文件夹路径
     */
    public static boolean deleteFileDir(String fileDirPath) {
        if (TextUtils.isEmpty(fileDirPath)) {
            Log.i(TAG, "fileDirPath 不能为空");
            return false;
        }
        return deleteFileDir(new File(fileDirPath));
    }

    /**
     * 删除文件夹（TODO 未测试）
     *
     * @param dirFile File文件夹路径
     */
    public static boolean deleteFileDir(File dirFile) {
        if (dirFile != null && dirFile.exists()) {
            if (dirFile.isDirectory()) {
                try {
                    File[] files = dirFile.listFiles();
                    if (files == null) {
                        return true;
                    }
                    for (File file : files) {
                        if (file.isDirectory()) {
                            deleteFileDir(file);
                        } else {
                            boolean delete = file.delete();
                        }
                    }
                    // 最后删跟目录
                    String[] list = dirFile.list();
                    if (list != null && list.length > 0) {
                        deleteFileDir(dirFile);
                    } else {
                        return dirFile.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (dirFile.isFile()) {
                return dirFile.delete();
            }
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param pathString 文件路径
     * @return
     */
    public static boolean deleteFile(String pathString) {
        return deleteFile(new File(pathString));
    }

    /**
     * 删除文件
     *
     * @param file File
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file != null && file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 把数据写入到文件
     *
     * @param data 数据
     * @param filePath 目标文件路径
     */
    public static boolean writeTextToFile(String data, String filePath) {
        return writeTextToFile(data, new File(filePath));
    }

    /**
     * 把数据写入到文件
     *
     * @param data 数据
     * @param targetFile 目标文件
     */
    public static boolean writeTextToFile(String data, File targetFile) {
        if (data == null) data = "";
        return writeTextToFile(data.getBytes(), targetFile);
    }

    /**
     * 把数据写入到文件
     *
     * @param data 数据
     * @param targetFile 目标文件
     */
    public static boolean writeTextToFile(byte[] data, File targetFile) {
        FileOutputStream fos = null;
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            fos = new FileOutputStream(targetFile);
            fos.write(data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return false;

        /*
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new StringReader(data));
            writer = new BufferedWriter(new FileWriter(targetFile));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(reader);
            close(writer);
        }
        return false;
        */
    }

    /**
     * 读取本地文本数据
     *
     * @param filePath 目标文件路径
     */
    public static String readTextFromFile(String filePath) {
        return readTextFromFile(new File(filePath));
    }

    /**
     * 读取本地文本数据
     *
     * @param file 目标文件
     */
    public static String readTextFromFile(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(br);
        }
        return null;
    }

    /**
     * 关闭读取流Reader
     *
     * @param readers 读取流
     */
    public static void close(Reader... readers) {
        try {
            for (int i = 0; i < readers.length; i++) {
                if (readers[i] != null) {
                    readers[i].close();
                    readers[i] = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭写入流Writer
     *
     * @param writers： Writer实例对象
     */
    public static void close(Writer... writers) {
        try {
            for (int i = 0; i < writers.length; i++) {
                if (writers[i] != null) {
                    writers[i].close();
                    writers[i] = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输入流InputStream
     *
     * @param ins 输入流
     */
    public static void close(InputStream... ins) {
        try {
            for (int i = 0; i < ins.length; i++) {
                if (ins[i] != null) {
                    ins[i].close();
                    ins[i] = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流OutputStream
     *
     * @param outs 输出流
     */
    public static void close(OutputStream... outs) {
        try {
            for (int i = 0; i < outs.length; i++) {
                if (outs[i] != null) {
                    outs[i].close();
                    outs[i] = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取Access下的文本数据
     *
     * @param context
     * @param accessFileName Access下的文件名
     * @param accessFileName
     * @return
     */
    public static String readTextFromAccess(Context context, String accessFileName) {
        InputStream in = null;
        try {
            in = context.openFileInput(accessFileName);
            byte[] buf = new byte[in.available()];
            while (in.read(buf) != -1) { }
            return new String(buf, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(in);
        }
        return null;
    }

    /**
     * 获取照片文件名 格式为：20140718_221839.png
     *
     * @return
     */
    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
        String dateString = format.format(new Date());
        StringBuffer sb = new StringBuffer(dateString).append(".png");
        return sb.toString();
    }

    /**
     * 获取Bitmap大小(在内存中占的大小是文件本身大小的4倍，所以计算实际大小时 /4)
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        int bitmapSize = 0;
        if (bitmap != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                bitmapSize = bitmap.getByteCount();
            } else {
                bitmapSize = bitmap.getRowBytes() * bitmap.getHeight(); // HC-MR1 以前
            }
        }
        return bitmapSize;
    }

    /**
     * 把图片的角设置为圆角（未测试）
     *
     * @param bitmap Bitmap图片
     * @param roundSize 圆角的大小（PX）
     * @return
     */
    public static Bitmap setBitmapRound(Bitmap bitmap, int roundSize) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xffFFFFFF;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(250, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundSize, roundSize, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 把序列化对象保存到内存中
     *
     * @param obj 序列化对象
     * @param targetFilePath 保存路径
     * @return 序列化成功返回true
     */
    public static boolean writeObjectToCache(Object obj, String targetFilePath) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(targetFilePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(oos, fos);
        }
        return false;
    }

    /**
     * 从缓存文件中读取序列化对象
     *
     * @param savePath 序列化该对象时保存的路径
     * @return 反序列化成功返回true
     */
    public static Object readObjectFromCache(String savePath) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(savePath);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(ois, fis);
        }
        return null;
    }

    /**
     * 存储List对象
     *
     * @param context 程序上下文
     * @param fileName 文件名，要在系统内保持唯一
     * @param list 对象数组集合，对象必须实现Parcelable
     * @return boolean 存储成功的标志
     */
    @SuppressLint("Recycle")
    public static boolean writeParcelableList(Context context, String fileName, List<Parcelable> list) {
        boolean success = false;
        FileOutputStream fos = null;
        try {
            if (list instanceof List) {
                fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                Parcel parcel = Parcel.obtain();
                parcel.writeList(list);
                byte[] data = parcel.marshall();
                fos.write(data);
                success = true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fos);
        }
        return success;
    }

    /**
     * 获取制定文件目录下所有文件大小
     * @param dir 文件目录
     * @return
     */
    public static long getFileSize(File dir) {
        long size = 0;
        try {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 如果下面还有文件
                    if (file.isDirectory()) {
                        size += getFileSize(file);
                    } else {
                        size += file.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 格式化文件大小单位
     *
     * @param size 大小（Byte单位）
     * @return
     */
    public static String formatFileSize(long size) {
        return formatFileSize(size, 2);
    }

    /**
     * 格式化文件大小单位
     *
     * @param size 大小（Byte单位）
     * @param scale 小数位数
     * @return
     */
    public static String formatFileSize(long size, int scale) {
        if (scale < 0) {
            scale = 0;
        }
        if (size <= 0) {
            return "0B";
        }
        long kiloByte = size >> 10; // 就是 size/1024
        if (kiloByte < 1) {
            return size + "B";
        }
        long megaByte = kiloByte >> 10; // /1024
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        long gigaByte = megaByte >> 10; // /1024
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        long teraBytes = gigaByte >> 10; // /1024
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    public static String formatFileSize(Context context, long size) {
        return android.text.format.Formatter.formatFileSize(context, size);
    }

    public static String formatShortFileSize(Context context, long size) {
        return android.text.format.Formatter.formatShortFileSize(context, size);
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean isExistsSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 判断一个网络URL文件是否已下载到指定的目录下
     *
     * @param path 路劲
     * @param url 网络URL
     * @return
     */
    public static boolean isExistsFile(String path, String url) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(url)) {
            return false;
        }
        String oldFileName = url.substring(url.lastIndexOf('/') + 1);

        String fileName = MD5.encode(url);
        if (oldFileName.contains(".")) {
            fileName = fileName + oldFileName.substring(oldFileName.lastIndexOf(".")); // 自定义文件名
        } else {
            fileName = fileName + ".jpg";
        }
        File file = new File(path, fileName);
        return file.exists() && file.isFile();
    }

    /**
     * 获取根目录 (内存存在，使用内存，否则使用外置SD卡)
     *
     * @return
     */
    private static File getSDPath() {
        File sdCardPath = Environment.getExternalStorageDirectory();
//		if(isExistSDCard()){
//			long totalExternalMemorySize = SystemUtils.getAvailExternalMemorySize();
//			System.out.println("totalExternalMemorySize = " + totalExternalMemorySize);
//			if(totalExternalMemorySize > 1024 * 1024 * 100){
//				rootPath = getDataDirectory();
//			} else {
//				rootPath = getExternalStorageDirectory();
//			}
//		} else {
//			rootPath = getExternalStorageDirectory();
//		}
        return sdCardPath;
    }

    /**
     * 获取手机SD卡路径
     *
     * @return /storage/emulated/0
     */
    public static File getExternalStorageDirectory() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory.exists()) {
            return externalStorageDirectory;
        }
        return null;
    }

    /**
     * 获取手机相册的路径
     *
     * @return /storage/emulated/0/Pictures
     */
    public static File getDirectoryPictures() {
//        // 第一种方式
//        File dirFile = Environment.getExternalStorageDirectory();
        // 第二种方式
        File dirFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (dirFile != null && dirFile.exists()) {
            return dirFile;
        }
        return null;
    }

    /**
     * 获取手机sd卡中app的cache的路径，需要SD读写权限。
     *
     * @return /storage/emulated/0/Android/data/<package name>/files/Caches
     */
    public static File getCachesExternalFilesDir(Context context) {
        if (!isExistsSDCard()) {
            return null; // SD卡没安装
        }
        File dirFile = context.getExternalFilesDir("Caches");
        if (dirFile != null && dirFile.exists()) {
            return dirFile;
        }
        return null;
    }

    /**
     * 获取外部存储卡中的文件目录。存储在该目录下的数据将随应用被卸载而清除。
     *
     * @return /storage/emulated/0/Android/data/<package name>/files
     */
    public static File getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null);
    }

    /**
     * 获取手机sd卡中app的cache的路径，需要SD读写权限。
     *
     * @return /data/user/0/<package name>/files
     */
    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * 获取手机内存路径
     *
     * @return /data
     */
    public static File getDataDirectory() {
        return Environment.getDataDirectory();
    }


    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static File getSDDir() {
        return !isExistsSDCard() ? null : Environment.getExternalStorageDirectory();
    }

    /**
     * 获取手机缓存目录 /data/data/<application package>/cache
     *
     * @param context
     * @return
     */
    public static File getCacheDir(Context context) {
        return context == null ? null : context.getCacheDir();
    }

    /**
     * 获取手机文件目录 /data/data/<application package>/files
     *
     * @param context
     * @return
     */
    public static File getFileDir(Context context) {
        return context == null ? null : context.getFilesDir();
    }

    /**
     * 获取外部存储卡中的缓存目录。存储在该目录下的数据将随应用被卸载而清除。
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        return context == null ? null : context.getExternalCacheDir();
    }

    /**
     * 把字节流写到File文件里
     *
     * @param is
     * @param targetFile
     */
    public static void copyInputStreamToFile(InputStream is, File targetFile) {
        if (is == null || targetFile == null) {
            return;
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] bytes = new byte[1024];
            int by = 0;
            while ((by = in.read(bytes)) != -1) {
                out.write(bytes, 0, by);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in, is);
            close(out);
        }
    }

    /**
     * 异步把文件从Assets下复制到data/data/files/ 下
     *
     * @param context
     * @param fileName 要复制的文件名
     * @param targetFile 目标文件
     */
    public static void copyAssetsFileAsync(final Context context, final String fileName, final File targetFile) {
        // 第二个参数 是执行过程中进度 的数据类型
        new AsyncTask<Void, Integer, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                copyAssets(context, fileName, targetFile);
                return null;
            }
        }.execute();
    }

    public static void printAllPath() {

//        Logger.i(TAG, "getExternalStorageDirectory()：" + getExternalStorageDirectory());
//        Logger.i(TAG, "getDirectoryPictures()：" + getDirectoryPictures());
//        Logger.i(TAG, "getCachesExternalFilesDir()：" + getCachesExternalFilesDir());
//        Logger.i(TAG, "getExternalFilesDir()：" + getExternalFilesDir(App.getApplication().getContext()));
//        Logger.i(TAG, "getFilesDir()：" + getFilesDir(App.getApplication().getContext()));
//        Logger.i(TAG, "getDataDirectory()：" + getDataDirectory());

        // 小米8（Android 10）测试
        // FileUtils.getExternalStorageDirectory()： /storage/emulated/0
        // FileUtils.getDirectoryPictures()：        /storage/emulated/0/Pictures
        // FileUtils.getCachesExternalFilesDir()：   /storage/emulated/0/Android/data/<应用包名>/files/Caches
        // FileUtils.getExternalFilesDir()：         /storage/emulated/0/Android/data/<应用包名>/files
        // FileUtils.getFilesDir()：                 /data/user/0/<应用包名>/files
        // FileUtils.getDataDirectory()：            /data

    }
}
