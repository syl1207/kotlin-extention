package com.song.common.extentions

import java.io.File

fun ByteArray.save2File(file: File) {
    try {
        file.outputStream().buffered().use {
            it.write(this)
            it.flush()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

