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

fun String.save2File(path: String) {
    save2File(File(path))
}

fun String.save2File(file: File) {
    try {
        file.ensureExist()
        file.writer().buffered().use {
            it.write(this)
            it.flush()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun File.ensureExist() {
    val parentFile = parentFile ?: return
    if (parentFile.exists().not()) parentFile.mkdirs()
    if (exists().not()) createNewFile()
}

