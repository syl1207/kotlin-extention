package com.song.common.extentions

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.util.Log
import java.io.*

/**
 * @param limitSize 为压缩后的二进制数据的大小
 */
fun Bitmap.compress(limitSize: Long): ByteArray? {
    var quality = 100
    val bmpStream = ByteArrayOutputStream()
    try {
        do {
            try {
                bmpStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            bmpStream.reset()
            compress(Bitmap.CompressFormat.JPEG, quality, bmpStream)
            quality -= 5
        } while (bmpStream.size() > limitSize && quality > 5)
        return bmpStream.use {
            it.toByteArray()
        }
    } catch (e: Exception) {
    }
    return null
}

fun Bitmap.scale(newWidth: Int, newHeight: Int): Bitmap {
    val widthRatio = newWidth.toFloat() / width
    val heightRatio = newHeight.toFloat() / height
    val matrix = Matrix().apply {
        postScale(widthRatio, heightRatio)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.scale(ratio: Float): Bitmap {
    val matrix = Matrix().apply {
        postScale(ratio, ratio)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.rotate(angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.setRotate(angle)
    // 围绕原地进行旋转
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.toJpegBytes(): ByteArray {
    return ByteArrayOutputStream().use {
        compress(Bitmap.CompressFormat.JPEG, 100, it)
        it.toByteArray()
    }
}

fun Bitmap.save(file: File) {
    try {
        val parentFile = file.parentFile ?: return
        if (parentFile.exists().not()) parentFile.mkdirs()
        if (file.exists().not()) file.createNewFile()
        BufferedOutputStream(FileOutputStream(file)).use {
            compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Bitmap.size(): Int {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {    //API 19
            allocationByteCount
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 -> { //API 12
            byteCount
        }
        else -> rowBytes * height
    }
}

fun ByteArray.nv21ToBitmap(width: Int, height: Int): Bitmap? {
    try {
        val image = YuvImage(this, ImageFormat.NV21, width, height, null)
        ByteArrayOutputStream().use {
            image.compressToJpeg(Rect(0, 0, width, height), 100, it)
            return BitmapFactory.decodeByteArray(it.toByteArray(), 0, it.size())
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun Resources.bitmap(id: Int): Bitmap {
    return BitmapFactory.decodeResource(this, id)
}

fun Context.bitmap(id: Int): Bitmap {
    return resources.bitmap(id)
}

fun ByteArray.toBitmap(options: BitmapFactory.Options? = null): Bitmap? {
    return BitmapFactory.decodeByteArray(this, 0, size, options)
}

