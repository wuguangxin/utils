package com.wuguangxin.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper

/**
 * 屏幕开关状态监听
 *
 * Created by wuguangxin on 2022/6/2.
 */
object ScreenStatusUtils {
    private const val TAG = "ScreenStatusUtils"
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val listeners = arrayListOf<OnScreenChangedListener>()
    private var receiver = NetworkStatusReceiver()
    private var registered = false
    private var screenOn = true

    fun addListener(listener: OnScreenChangedListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    fun removeListener(listener: OnScreenChangedListener): Boolean {
        return listeners.remove(listener)
    }

    fun register(context: Context) {
        if (!registered) {
            registered = true
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_ON)
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            context.registerReceiver(receiver, filter)
        }
    }

    fun unregister(context: Context) {
        registered = false
        listeners.clear()
        handler.removeCallbacksAndMessages(null)
        context.unregisterReceiver(receiver)
    }

    // 屏幕是否点亮
    fun isScreenOn(): Boolean = screenOn

    // 屏幕是否熄灭
    fun isScreenOff(): Boolean = !screenOn

    private class NetworkStatusReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                Intent.ACTION_SCREEN_ON -> changed(true)
                Intent.ACTION_SCREEN_OFF -> changed(false)
            }
        }
    }

    private fun changed(on: Boolean) {
        this.screenOn = on
        Logger.i(TAG, if (on) "亮屏" else "息屏")
        invoke {
            for (it in listeners)
                if (on) it.onScreenOn()
                else it.onScreenOff()
        }
    }

    private fun invoke(task: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            task.run()
        } else {
            handler.post(task)
        }
    }

    interface OnScreenChangedListener {
        fun onScreenOn() // 亮屏
        fun onScreenOff() // 息屏
    }
}