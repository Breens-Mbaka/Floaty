package com.breens.floaty

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.breens.floaty.Common.PERMISSION_MESSAGE
import com.breens.floaty.Common.PERMISSION_TITLE

fun requestOverlayDisplayPermission(context: Context, activity: Activity) {
    val builder = AlertDialog.Builder(context)

    builder.setCancelable(true)
    builder.setTitle(PERMISSION_TITLE)
    builder.setMessage(PERMISSION_MESSAGE)

    builder.setPositiveButton("Settings") { dialog, _ ->
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName),
        )
        activity.startActivityForResult(intent, AppCompatActivity.RESULT_OK)
    }

    val dialog = builder.create()
    dialog.show()
}

fun checkOverlayDisplayPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        Settings.canDrawOverlays(context)
    } else {
        true
    }
}
