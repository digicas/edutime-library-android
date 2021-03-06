@file:JvmName("EduPermissionUtil")

package cz.edukids.sdk.model.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import cz.edukids.sdk.model.internal.EduConstants

@JvmName("hasPermission")
fun Context.hasEduPermission() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    checkSelfPermission(EduConstants.Permission.ACCESS_DATA) == PackageManager.PERMISSION_GRANTED
} else {
    true
}

@JvmName("requestPermission")
inline fun ComponentActivity.requestEduPermission(crossinline onResult: (Boolean) -> Unit) {
    registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        onResult(it == true)
    }.launch(EduConstants.Permission.ACCESS_DATA)
}

@JvmName("requestPermission")
fun Activity.requestEduPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        requestPermissions(arrayOf(EduConstants.Permission.ACCESS_DATA), 10)
    }
}

inline fun onPermissionResult(
    requestCode: Int,
    resultCode: Int,
    extras: Intent?,
    onDeclined: () -> Unit,
    onGranted: () -> Unit
) {
    if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
        onGranted()
    } else {
        onDeclined()
    }
}
