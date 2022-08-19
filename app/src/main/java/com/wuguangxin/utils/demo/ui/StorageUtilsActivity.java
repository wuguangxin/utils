package com.wuguangxin.utils.demo.ui;

import android.content.Context;
import android.widget.TextView;

import com.wuguangxin.utils.FileUtils;
import com.wuguangxin.utils.StorageUtils;
import com.wuguangxin.utils.demo.R;

import java.io.File;

public class StorageUtilsActivity extends BaseActivity {

    @Override
    public int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    public void initView() {
        setTitle(getSimpleTitle());
        Context context = this;
        StringBuilder sb = new StringBuilder();

        sb.append("\n====存储信息=====");
        sb.append("\n低内存状态：").append(StorageUtils.isLowMemory(context));  // 是否是低内存：false
        sb.append("\n运行内存（RAM）：")
                .append(formatUnit(StorageUtils.getAvailRAMSize(context)))              // 1810214912    = 1.68GB
                .append("/").append(formatUnit(StorageUtils.getTotalRAMSize(context))); // 5903380480    = 5.49GB
        sb.append("\n内置存储（ROM）：")
                .append(formatUnit(StorageUtils.getInternalStorageAvailSize()))                 // 3656527872    = 3.40GB
                .append("/").append(formatUnit(StorageUtils.getInternalStorageTotalSize()));    // 118981873664  = 110.81GB
        sb.append("\n外置存储（ROM）：")
                .append(formatUnit(StorageUtils.getExternalStorageAvailSize()))                 // 3656527872    = 3.40GB
                .append("/").append(formatUnit(StorageUtils.getExternalStorageTotalSize()));    // 118981873664  = 110.81GB

        sb.append(format("getFilesDir：", StorageUtils.getFilesDir(context))); // /data/user/0/<包名>/files
        sb.append(format("getCacheDir：", StorageUtils.getCacheDir(context))); // /data/user/0/<包名>/cache
        sb.append(format("getCodeCacheDir：", StorageUtils.getCodeCacheDir(context))); // /data/user/0/<包名>/code_cache
        sb.append(format("getExternalCacheDir：", StorageUtils.getExternalCacheDir(context))); // /storage/emulated/0/Android/data/<包名>/cache
        sb.append(format("getExternalFilesDir：", StorageUtils.getExternalFilesDir(context))); // /storage/emulated/0/Android/data/<包名>/files
        sb.append(format("getExternalFilesDirDCIM：", StorageUtils.getExternalFilesDirDCIM(context))); // /storage/emulated/0/Android/data/<包名>/files/DCIM
        sb.append(format("getExternalFilesDirCache：", StorageUtils.getExternalFilesDirCache(context))); // /storage/emulated/0/Android/data/<包名>/files/Caches
        sb.append(format("getExternalFilesDirMusic：", StorageUtils.getExternalFilesDirMusic(context))); // /storage/emulated/0/Android/data/<包名>/files/Music
        sb.append(format("getExternalFilesDirAlarms：", StorageUtils.getExternalFilesDirAlarms(context))); // /storage/emulated/0/Android/data/<包名>/files/Alarms
        sb.append(format("getExternalFilesDirMovies：", StorageUtils.getExternalFilesDirMovies(context))); // /storage/emulated/0/Android/data/<包名>/files/Movies
        sb.append(format("getExternalFilesDirPictures：", StorageUtils.getExternalFilesDirPictures(context))); // /storage/emulated/0/Android/data/<包名>/files/Pictures
        sb.append(format("getExternalFilesDirPodcasts：", StorageUtils.getExternalFilesDirPodcasts(context))); // /storage/emulated/0/Android/data/<包名>/files/Podcasts
        sb.append(format("getExternalFilesDirDownloads：", StorageUtils.getExternalFilesDirDownloads(context))); // /storage/emulated/0/Android/data/<包名>/files/Download
        sb.append(format("getExternalFilesDirDocuments：", StorageUtils.getExternalFilesDirDocuments(context))); // /storage/emulated/0/Android/data/<包名>/files/Documents
        sb.append(format("getExternalFilesDirRingtones：", StorageUtils.getExternalFilesDirRingtones(context))); // /storage/emulated/0/Android/data/<包名>/files/Ringtones
        sb.append(format("getExternalFilesDirAudioBooks：", StorageUtils.getExternalFilesDirAudioBooks(context))); // /storage/emulated/0/Android/data/<包名>/files/Audiobooks
        sb.append(format("getExternalFilesDirNotifications：", StorageUtils.getExternalFilesDirNotifications(context))); // /storage/emulated/0/Android/data/<包名>/files/Notifications

        sb.append("\n存储卡：");
        sb.append("\nSD卡挂载状态：").append(StorageUtils.hasExternalStorage());   // true
        sb.append(format("系统根目录：", StorageUtils.getRootDirectory())); // /system
        sb.append(format("数据目录：", StorageUtils.getDataDirectory())); // /data
        sb.append(format("缓存目录：", StorageUtils.getDownloadCacheDirectory())); // /data/cache

        sb.append(format("相册目录：", StorageUtils.getDirectoryDCIM())); // /storage/emulated/0/DCIM
        sb.append(format("音乐目录：", StorageUtils.getDirectoryMusic())); // /storage/emulated/0/Music
        sb.append(format("铃声目录：", StorageUtils.getDirectoryAlarms())); // /storage/emulated/0/Alarms
        sb.append(format("电影目录：", StorageUtils.getDirectoryMovies())); // /storage/emulated/0/Movies
        sb.append(format("图片目录：", StorageUtils.getDirectoryPictures())); // /storage/emulated/0/Pictures
        sb.append(format("下载目录：", StorageUtils.getDirectoryDownloads())); // /storage/emulated/0/Download
        sb.append(format("文档目录：", StorageUtils.getDirectoryDocuments())); // /storage/emulated/0/Documents
        sb.append(format("截屏目录：", StorageUtils.getDirectoryScreenshots())); // /storage/emulated/0/Screenshots

        String info = sb.toString().replaceAll(getPackageName(), "<包名>");
        TextView mTextView = findViewById(R.id.storage_info);
        mTextView.setText(info);
    }

    private StringBuilder format(String key, File fileDir) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n").append(key);
        sb.append("\n").append(fileDir);
        sb.append("  ").append(formatUnit(fileDir));
        return sb;
    }

    private String formatUnit(long size) {
        return size <= 0 ? "0GB" : FileUtils.formatFileSize(this, size);
    }

    private String formatUnit(File file) {
        long size = FileUtils.getFileSize(file);
        return size <= 0 ? "0GB" : FileUtils.formatFileSize(this, size);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }
}
