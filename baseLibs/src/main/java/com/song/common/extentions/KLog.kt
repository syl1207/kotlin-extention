package com.song.common.extentions

import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

abstract class LogController {
    abstract fun getLogFolder(): File
    abstract fun shouldLog(): Boolean
    abstract fun checkLogFiles()
    class Default : LogController() {
        private val folder = Environment.getExternalStorageDirectory().toString() + "/cm/log/"
        override fun getLogFolder(): File {
            return File(folder)
        }

        override fun shouldLog(): Boolean {
            return true
        }

        override fun checkLogFiles() {

        }
    }
}

object KLog {
    private val fileNameDateFormat = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
    private var logController: LogController = LogController.Default()
    private val writeService = Executors.newSingleThreadExecutor()

    fun applyLogController(logEngine: LogController) {
        this.logController = logEngine
    }

    @Synchronized
    fun saveLog(string: String) {
        if (logController.shouldLog().not()) return
        writeService.execute {
            try {
                val folder = logController.getLogFolder()
                if (!folder.exists()) folder.mkdirs()
                val logFile =
                    File(folder, "${fileNameDateFormat.format(Date())}_${Build.SERIAL}.log")
                if (!logFile.exists()) logFile.createNewFile()
                val bufferedWriter = BufferedWriter(FileWriter(logFile, true))
                bufferedWriter.use {
                    it.write(df.format(Date()) + ":" + string + "\n")
                    it.flush()
                }
            } catch (e: Exception) {
            } finally {
                logController.checkLogFiles()
            }
        }
    }

    private val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
}

/**
 * adb shell setprop log.tag.Main D
 */

fun String.log(tag: String = "krik", cache: Boolean = false) {
    if (cache) {
        KLog.saveLog("$tag: $this")
    } else {
        Log.e(tag, this)
    }
}


fun Any.log(tag: String = "krik", content: String, cache: Boolean = false) {
    if (cache) {
        KLog.saveLog("$tag: $content")
    } else {
        Log.e(tag, content)
    }
}