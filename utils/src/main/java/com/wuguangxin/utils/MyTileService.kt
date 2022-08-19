package com.wuguangxin.utils

import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Icon
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import androidx.annotation.RequiresApi
import com.tencent.mmkv.MMKV

/**
 *通知栏下的快捷小图标
 *
 * 用法：在applacation节点下
 * <service
        android:name=".MyTileService的路径"
        android:label="PearCoin测试"
        android:icon="@mipmap/ic_launcher"
        android:permission="android.permission.BIND_QUICK_SETTINGS_TILE"
        android:exported="true">
        <intent-filter>
            <action android:name="android.service.quicksettings.action.QS_TILE" />
        </intent-filter>
    </service>
 */

@RequiresApi(Build.VERSION_CODES.N)
class MyTileService : TileService() {

    companion object {
        val TAG = "MyQsTileService"
    }

    override fun onCreate() {
        log("onCreate")
        super.onCreate()
    }


    override fun startActivity(intent: Intent?) {
        log("startActivity")
        super.startActivity(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        log("onBind")
        return super.onBind(intent)
    }

    override fun onRebind(intent: Intent?) {
        log("onRebind")
        super.onRebind(intent)
    }

    /**
     * TODO： 按钮开启/关闭操作
     */
    override fun onClick() {
        log("TileService onClick ++++++++++++++")
        super.onClick()

        // 更新 Tile 状态
        val open = MMKV.defaultMMKV().decodeBool("open", false)
        qsTile.state = if (open) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        qsTile.updateTile()
        MMKV.defaultMMKV().encode("open", !open)

        qsTile.icon = Icon.createWithResource(this, R.drawable.xin_toast_icon)
        qsTile.label = "Studio"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            qsTile.subtitle = "我是TileService"
        }
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }


    /**
     * 下拉菜单时被调用，Tile 没有从 Editor 栏拖到设置栏中，则不会调用
     *  onTileAdded() 时会被调用一次
     */
    override fun onStartListening() {
        log("onStartListening")
        super.onStartListening()
    }

    /**
     * 同上 同理
     */
    override fun onStopListening() {
        log("onStopListening")
        super.onStopListening()
    }

    /**
     * 用户将 Tile 从 Edit 中添加到设定栏中
     */
    override fun onTileAdded() {
        log("onTileAdded")
        super.onTileAdded()
    }

    /**
     * 将 Tile 移除设定栏
     */
    override fun onTileRemoved() {
        log("onTileRemoved")
        super.onTileRemoved()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        log("onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        log("onLowMemory")
        super.onLowMemory()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        log("onTaskRemoved")
        super.onTaskRemoved(rootIntent)
    }

    override fun onTrimMemory(level: Int) {
        log("onTrimMemory")
        super.onTrimMemory(level)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        log("onUnbind")
        return super.onUnbind(intent)
    }

    private fun log(msg: String) {
        Log.e(TAG, msg)
    }

}