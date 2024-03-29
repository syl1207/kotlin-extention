package com.song.common.extentions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Build
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import java.io.Serializable

fun Context.readAssets(fileName: String): String? {
    try {
        assets.open(fileName).bufferedReader().use {
            return it.readText()
        }
    } catch (e: Exception) {
        return null
    }
}

fun Context.drawable(id: Int): Drawable? {
    return ContextCompat.getDrawable(this, id)
}

/**
 * reduce code length
 */
fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.checkPermission(permissions: Array<String>): Boolean {
    permissions.forEach {
        if (checkPermission(it).not()) return false
    }
    return true
}

val Context.versionCode
    get() = try {
        Log.e("krik", "get Version Code")
        val packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        0L
    }

fun Context.versionName(): String? {
    return try {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}

fun Context.getScreenWidth(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.widthPixels
}

fun Context.getScreenHeight(): Int {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val outMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(outMetrics)
    return outMetrics.heightPixels
}

fun Context.isScreenPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}


fun Context.dp2Px(dp: Float): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun <T> Context.goToActivity(clazz: Class<T>) {
    startActivity(Intent(this, clazz))
}

fun <T> Activity.startActivityForResult(clazz: Class<T>, code: Int) {
    startActivityForResult(Intent(this, clazz), code)
}


inline fun <reified T> Context.goToActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T> Context.goToActivity(vararg pairs: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    addParamsToIntent(pairs, intent)
    startActivity(intent)
}

inline fun <reified T> Activity.startActivityForResult(
    code: Int,
    vararg pairs: Pair<String, Any?>
) {
    val intent = Intent(this, T::class.java)
    addParamsToIntent(pairs, intent)
    startActivityForResult(intent, code)
}

fun addParamsToIntent(
    pairs: Array<out Pair<String, Any?>>,
    intent: Intent
) {
    pairs.forEach {
        when (val second = it.second) {
            is String -> intent.putExtra(it.first, second)
            is Int -> intent.putExtra(it.first, second)
            is Boolean -> intent.putExtra(it.first, second)
            is Serializable -> intent.putExtra(it.first, second)
            is Char -> intent.putExtra(it.first, second)
            is Long -> intent.putExtra(it.first, second)
            is Float -> intent.putExtra(it.first, second)
            //todo 完善常用类型
        }
    }
}

fun Context.getImei(): String? {
    return try {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.deviceId
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}




