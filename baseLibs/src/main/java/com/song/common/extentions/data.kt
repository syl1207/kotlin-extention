package com.song.common.extentions

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun InputStream.toByteArray(): ByteArray {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val buff = ByteArray(4096)
    var rc: Int
    while (read(buff, 0, 4096).also { rc = it } > 0) {
        byteArrayOutputStream.write(buff, 0, rc)
    }
    return byteArrayOutputStream.toByteArray()
}

fun <T> List<T>.split(pieceSize: Int): List<List<T>> {
    val num: Int = (size + pieceSize - 1) / pieceSize
    val newList = ArrayList<List<T>>(num)
    for (i in 0 until num) {
        // 开始位置
        val fromIndex: Int = i * pieceSize
        // 结束位置
        val toIndex = if ((i + 1) * pieceSize < size) (i + 1) * pieceSize else size
        newList.add(subList(fromIndex, toIndex))
    }
    return newList
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>(size)
    forEach {
        arrayList.add(it)
    }
    return arrayList
}

fun String.toHtmlText(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this);
    }
}

inline fun <reified T> Any.cast(): T? = if (this is T) {
    this
} else {
    null
}

fun SimpleDateFormat.toDateOrNull(str: String?): Date? {
    if (str.isNullOrEmpty()) return null
    return try {
        parse(str)
    } catch (e: java.lang.Exception) {
        null
    }
}

fun String.md5(): String? {
    return try {
        val md5: MessageDigest = MessageDigest.getInstance("MD5")
        md5.update(toByteArray())
        val encryption: ByteArray = md5.digest()
        val result = StringBuilder()
        for (b in encryption) {
            if (Integer.toHexString(0xff and b.toInt()).length == 1) {
                result.append("0").append(Integer.toHexString(0xff and b.toInt()))
            } else {
                result.append(Integer.toHexString(0xff and b.toInt()))
            }
        }
        result.toString()
    } catch (e: NoSuchAlgorithmException) {
        null
    }
}

fun String.base64Decode(): ByteArray? {
    return Base64.decode(this, Base64.DEFAULT)
}

fun ByteArray.base64Encode(): String {
    return Base64.encodeToString(this, 0, size, Base64.DEFAULT)
}

