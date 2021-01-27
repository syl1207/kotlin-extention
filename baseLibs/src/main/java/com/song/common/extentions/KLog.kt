package com.song.common.extentions

import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

interface LogController {
    fun getLogFolder(): File
    fun shouldLog(): Boolean

    fun getMaxLogFiles(): Int

    fun getLogFileNameFormat(): SimpleDateFormat

    class Default : LogController {
        private val folder = Environment.getExternalStorageDirectory().toString() + "/cm/log/"
        override fun getLogFolder(): File {
            return File(folder)
        }

        override fun shouldLog(): Boolean {
            return true
        }

        override fun getMaxLogFiles(): Int {
            return 5
        }

        override fun getLogFileNameFormat(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMddHHmm", Locale.CHINA)
        }
    }
}

object KLog {
    private var logController: LogController = LogController.Default()
    private val writeService = Executors.newSingleThreadExecutor()
    private val logFileFormat: SimpleDateFormat by lazy {
        logController.getLogFileNameFormat()
    }

    /**
     * logFileFormat做了缓存
     */
    fun overrideLogController(logEngine: LogController) {
        this.logController = logEngine
    }

    @Synchronized
    fun saveLog(string: String) {
        if (logController.shouldLog().not()) return
        writeService.execute {
            val folder = logController.getLogFolder()
            try {
                if (!folder.exists()) folder.mkdirs()
                val logFile =
                    File(folder, "${logFileFormat.format(Date())}.log")
                if (!logFile.exists()) {
                    //创建文件的时候进行一次检测
                    val count = folder.list()?.size ?: 0
                    if (count >= logController.getMaxLogFiles()) {
                        folder.listFiles()?.get(0)?.delete()
                    }
                    logFile.createNewFile()
                }
                val writer = BufferedWriter(FileWriter(logFile, true))
                writer.use {
                    it.write(df.format(Date()) + ":" + string + "\n")
                    it.flush()
                }
            } catch (e: Exception) {
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