package com.breens.floaty

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.breens.floaty.Common.ACTION

class FloatyWidget : Service() {

    private lateinit var floatView: ViewGroup
    private lateinit var floatingWindowLayoutParameters: WindowManager.LayoutParams
    private lateinit var windowManager: WindowManager
    private var LAYOUT_TYPE = 0

    private val stopFloatyWidgetReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION) {
                closeFloatingWidget()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val intentFilter = IntentFilter(ACTION)
        registerReceiver(stopFloatyWidgetReceiver, intentFilter)

        val metrics = applicationContext.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        val customLayoutResourceId = CustomLayoutConfiguration.customLayoutResourceId
        val floatingWidgetWidth = CustomLayoutConfiguration.floatingWidgetWidth
        val floatyWidgetHeight = CustomLayoutConfiguration.floatyWidgetHeight
        val floatingWidgetPosition = CustomLayoutConfiguration.floatingWidgetPosition

        val inflater = baseContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (customLayoutResourceId != 0) {
            floatView = (inflater.inflate(customLayoutResourceId, null) as ViewGroup?)!!
        } else {
            floatView =
                (inflater.inflate(R.layout.default_floating_widget_layout, null) as ViewGroup?)!!
            Log.e("FloatyWidget", "Invalid customLayoutResourceId: $customLayoutResourceId")
        }

        LAYOUT_TYPE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_TOAST
        }

        floatingWindowLayoutParameters = WindowManager.LayoutParams(
            (width * floatingWidgetWidth).toInt(),
            (height * floatyWidgetHeight).toInt(),
            LAYOUT_TYPE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT,
        )

        floatingWindowLayoutParameters.gravity = floatingWidgetPosition

        floatingWindowLayoutParameters.x = 0
        floatingWindowLayoutParameters.y = 0

        windowManager.addView(floatView, floatingWindowLayoutParameters)

        floatView.setOnTouchListener(object : View.OnTouchListener {
            val floatWindowLayoutUpdateParam: WindowManager.LayoutParams =
                floatingWindowLayoutParameters
            var x = 0.0
            var y = 0.0
            var px = 0.0
            var py = 0.0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = floatWindowLayoutUpdateParam.x.toDouble()
                        y = floatWindowLayoutUpdateParam.y.toDouble()

                        px = event.rawX.toDouble()

                        py = event.rawY.toDouble()
                    }

                    MotionEvent.ACTION_MOVE -> {
                        floatWindowLayoutUpdateParam.x = (x + event.rawX - px).toInt()
                        floatWindowLayoutUpdateParam.y = (y + event.rawY - py).toInt()

                        windowManager.updateViewLayout(floatView, floatWindowLayoutUpdateParam)
                    }
                }
                return false
            }
        })
    }

    fun closeFloatingWidget() {
        stopSelf()
        windowManager.removeView(floatView)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(stopFloatyWidgetReceiver)
    }
}
