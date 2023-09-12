package com.breens.floaty

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.breens.floaty.Common.ACTION

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CustomLayoutConfiguration.customLayoutResourceId = R.layout.floating_widget
        CustomLayoutConfiguration.floatingWidgetWidth = 0.45f
        CustomLayoutConfiguration.floatyWidgetHeight = 0.30f
        CustomLayoutConfiguration.floatingWidgetPosition = Gravity.TOP

        val showFloatingWidgetButton = findViewById<Button>(R.id.showFloatingWidgetButton)
        showFloatingWidgetButton.setOnClickListener {
            if (checkOverlayDisplayPermission(context = this)) {
                showFloatingWidget(context = this)
            } else {
                requestOverlayDisplayPermission(context = this, activity = this)
            }
        }

        val closeWidgetButton = findViewById<Button>(R.id.closeWidgetButton)
        closeWidgetButton.setOnClickListener {
            val closeFloatingWidgetIntent = Intent(ACTION)
            sendBroadcast(closeFloatingWidgetIntent)
        }
    }

    private fun showFloatingWidget(context: MainActivity) {
        context.startService(
            Intent(
                context,
                FloatyWidget::class.java,
            ),
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        val closeFloatingWidgetIntent = Intent(ACTION)
        sendBroadcast(closeFloatingWidgetIntent)
    }
}
