package com.wuguangxin.utils.demo.ui;

import android.content.Context;
import android.widget.TextView;

import com.wuguangxin.utils.FileUtils;
import com.wuguangxin.utils.StorageUtils;
import com.wuguangxin.utils.demo.R;

import java.io.File;

import butterknife.BindView;

public class StorageUtilsActivity extends BaseActivity {
    @BindView(R.id.storage_info) TextView mTextView;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    public void initView() {
        setTitle("Toast工具类");
        Context context = this;
        StorageUtils.test(this);
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append("====存储信息=====");
        sb.append("\n").append("低内存状态：").append(StorageUtils.isLowMemory(context));  // 是否是低内存：false
        sb.append("\n").append("运行内存（RAM）：")
                .append(formatUnit(StorageUtils.getAvailRAMSize(context)))              // 1810214912    = 1.68GB
                .append("/").append(formatUnit(StorageUtils.getTotalRAMSize(context))); // 5903380480    = 5.49GB
        sb.append("\n").append("内置存储（ROM）：")
                .append(formatUnit(StorageUtils.getInternalStorageAvailSize()))                 // 3656527872    = 3.40GB
                .append("/").append(formatUnit(StorageUtils.getInternalStorageTotalSize()));    // 118981873664  = 110.81GB
        sb.append("\n").append("外置存储（ROM）：")
                .append(formatUnit(StorageUtils.getExternalStorageAvailSize()))                 // 3656527872    = 3.40GB
                .append("/").append(formatUnit(StorageUtils.getExternalStorageTotalSize()));    // 118981873664  = 110.81GB

        File getFilesDir = StorageUtils.getFilesDir(context); // /data/user/0/<包名>/files
        sb.append("\n").append("getFilesDir：")
                .append("\n").append(getFilesDir)
                .append("\t").append(formatUnit(getFilesDir));

        File getCacheDir = StorageUtils.getCacheDir(context); // /data/user/0/<包名>/cache
        sb.append("\n").append("getCacheDir：")
                .append("\n").append(getCacheDir)
                .append("\t").append(formatUnit(getCacheDir));

        File getCodeCacheDir = StorageUtils.getCodeCacheDir(context); // /data/user/0/<包名>/code_cache
        sb.append("\n").append("getCodeCacheDir：")
                .append("\n").append(getCodeCacheDir)
                .append("\t").append(formatUnit(getCodeCacheDir));

        File getExternalCacheDir = StorageUtils.getExternalCacheDir(context); // /storage/emulated/0/Android/data/<包名>/cache
        sb.append("\n").append("getExternalCacheDir：")
                .append("\n").append(getExternalCacheDir)
                .append("\t").append(formatUnit(getExternalCacheDir));

        File getExternalFilesDir = StorageUtils.getExternalFilesDir(context); // /storage/emulated/0/Android/data/<包名>/files
        sb.append("\n").append("getExternalFilesDir：")
                .append("\n").append(getExternalFilesDir)
                .append("\t").append(formatUnit(getExternalFilesDir));

        File getExternalFilesDirDCIM = StorageUtils.getExternalFilesDirDCIM(context); // /storage/emulated/0/Android/data/<包名>/files/DCIM
        sb.append("\n").append("getExternalFilesDirDCIM：")
                .append("\n").append(getExternalFilesDirDCIM)
                .append("\t").append(formatUnit(getExternalFilesDirDCIM));

        File getExternalFilesDirCache = StorageUtils.getExternalFilesDirCache(context); // /storage/emulated/0/Android/data/<包名>/files/Caches
        sb.append("\n").append("getExternalFilesDirCache：")
                .append("\n").append(getExternalFilesDirCache)
                .append("\t").append(formatUnit(getExternalFilesDirCache));

        File getExternalFilesDirMusic = StorageUtils.getExternalFilesDirMusic(context); // /storage/emulated/0/Android/data/<包名>/files/Music
        sb.append("\n").append("getExternalFilesDirMusic：")
                .append("\n").append(getExternalFilesDirMusic)
                .append("\t").append(formatUnit(getExternalFilesDirMusic));

        File getExternalFilesDirAlarms = StorageUtils.getExternalFilesDirAlarms(context); // /storage/emulated/0/Android/data/<包名>/files/Alarms
        sb.append("\n").append("getExternalFilesDirAlarms：")
                .append("\n").append(getExternalFilesDirAlarms)
                .append("\t").append(formatUnit(getExternalFilesDirAlarms));

        File getExternalFilesDirMovies = StorageUtils.getExternalFilesDirMovies(context); // /storage/emulated/0/Android/data/<包名>/files/Movies
        sb.append("\n").append("getExternalFilesDirMovies：")
                .append("\n").append(getExternalFilesDirMovies)
                .append("\t").append(formatUnit(getExternalFilesDirMovies));

        File getExternalFilesDirPictures = StorageUtils.getExternalFilesDirPictures(context); // /storage/emulated/0/Android/data/<包名>/files/Pictures
        sb.append("\n").append("getExternalFilesDirPictures：")
                .append("\n").append(getExternalFilesDirPictures)
                .append("\t").append(formatUnit(getExternalFilesDirPictures));

        File getExternalFilesDirPodcasts = StorageUtils.getExternalFilesDirPodcasts(context); // /storage/emulated/0/Android/data/<包名>/files/Podcasts
        sb.append("\n").append("getExternalFilesDirPodcasts：")
                .append("\n").append(getExternalFilesDirPodcasts)
                .append("\t").append(formatUnit(getExternalFilesDirPodcasts));

        File getExternalFilesDirDownloads = StorageUtils.getExternalFilesDirDownloads(context); // /storage/emulated/0/Android/data/<包名>/files/Download
        sb.append("\n").append("getExternalFilesDirDownloads：")
                .append("\n").append(getExternalFilesDirDownloads)
                .append("\t").append(formatUnit(getExternalFilesDirDownloads));

        File getExternalFilesDirDocuments = StorageUtils.getExternalFilesDirDocuments(context); // /storage/emulated/0/Android/data/<包名>/files/Documents
        sb.append("\n").append("getExternalFilesDirDocuments：")
                .append("\n").append(getExternalFilesDirDocuments)
                .append("\t").append(formatUnit(getExternalFilesDirDocuments));

        File getExternalFilesDirRingtones = StorageUtils.getExternalFilesDirRingtones(context); // /storage/emulated/0/Android/data/<包名>/files/Ringtones
        sb.append("\n").append("getExternalFilesDirRingtones：")
                .append("\n").append(getExternalFilesDirRingtones)
                .append("\t").append(formatUnit(getExternalFilesDirRingtones));

        File externalFilesDirAudioBooks = StorageUtils.getExternalFilesDirAudioBooks(context); // /storage/emulated/0/Android/data/<包名>/files/Audiobooks
        sb.append("\n").append("getExternalFilesDirAudioBooks：")
                .append("\n").append(externalFilesDirAudioBooks)
                .append("\t").append(formatUnit(externalFilesDirAudioBooks));

        File externalFilesDirNotifications = StorageUtils.getExternalFilesDirNotifications(context);// /storage/emulated/0/Android/data/<包名>/files/Notifications
        sb.append("\n").append("getExternalFilesDirNotifications：")
                .append("\n").append(externalFilesDirNotifications)
                .append("\t").append(formatUnit(externalFilesDirNotifications));

        sb.append("\n").append("存储卡：");
        sb.append("\n").append("SD卡挂载状态：")
                .append(StorageUtils.hasExternalStorage());   // true

        File rootDirectory = StorageUtils.getRootDirectory();// /system
        sb.append("\n").append("系统根目录：")
                .append("\n").append(rootDirectory)
                .append("\t").append(formatUnit(rootDirectory));

        File dataDirectory = StorageUtils.getDataDirectory(); // /data
        sb.append("\n").append("数据根目录：")
                .append("\n").append(dataDirectory)
                .append("\t").append(formatUnit(dataDirectory));

        File downloadCacheDirectory = StorageUtils.getDownloadCacheDirectory();// /data/cache
        sb.append("\n").append("下载缓存目录：")
                .append("\n").append(downloadCacheDirectory)
                .append("\t").append(formatUnit(downloadCacheDirectory));

        File directoryDCIM = StorageUtils.getDirectoryDCIM();// /storage/emulated/0/DCIM
        sb.append("\n").append("相册目录：")
                .append("\n").append(directoryDCIM)
                .append("\t").append(formatUnit(directoryDCIM));

        File directoryMusic = StorageUtils.getDirectoryMusic();// /storage/emulated/0/Music
        sb.append("\n").append("音乐目录：")
                .append("\n").append(directoryMusic)
                .append("\t").append(formatUnit(directoryMusic));

        File directoryAlarmsFile = StorageUtils.getDirectoryAlarms(); // /storage/emulated/0/Alarms
        sb.append("\n").append("警告音目录：")
                .append("\n").append(directoryAlarmsFile)
                .append("\t").append(formatUnit(directoryAlarmsFile));

        File directoryMovies = StorageUtils.getDirectoryMovies(); // /storage/emulated/0/Movies
        sb.append("\n").append("电影目录：")
                .append("\n").append(directoryMovies)
                .append("\t").append(formatUnit(directoryMovies));

        File directoryPictures = StorageUtils.getDirectoryPictures(); // /storage/emulated/0/Pictures
        sb.append("\n").append("图片目录：")
                .append("\n").append(directoryPictures)
                .append("\t").append(formatUnit(directoryPictures));

        File directoryDownloads = StorageUtils.getDirectoryDownloads(); // /storage/emulated/0/Download
        sb.append("\n").append("下载目录：")
                .append("\n").append(directoryDownloads)
                .append("\t").append(formatUnit(directoryDownloads));

        File directoryDocuments = StorageUtils.getDirectoryDocuments(); // /storage/emulated/0/Documents
        sb.append("\n").append("文档目录：")
                .append("\n").append(directoryDocuments)
                .append("\t").append(formatUnit(directoryDocuments));

        File directoryScreenshots = StorageUtils.getDirectoryScreenshots(); // /storage/emulated/0/Screenshots
        sb.append("\n").append("截屏目录：")
                .append("\n").append(directoryScreenshots)
                .append("\t").append(formatUnit(directoryScreenshots));

        String info = sb.toString().replaceAll(getPackageName(), "<包名>");
        mTextView.setText(info);
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
