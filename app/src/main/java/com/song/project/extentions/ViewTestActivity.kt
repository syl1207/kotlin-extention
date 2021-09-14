package com.song.project.extentions

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.song.common.extentions.*
import kotlinx.android.synthetic.main.activity_view_test.*
import kotlin.concurrent.thread

class ViewTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_test)
        drawableTest.setOnClickListener {
            val tag = it.tag
            if (tag == null) {
                drawableTest.drawableLeft(R.mipmap.ic_launcher, 10)
                it.tag = "left"
            } else if (tag == "left") {
                drawableTest.drawableTop(R.mipmap.ic_launcher)
                it.tag = "top"
            } else if (tag == "top") {
                drawableTest.drawableTop(null, 0)
                it.tag = null
            }
        }

        var i = 0
        selector.setOnClickListener {
            val textView = it.cast<TextView>()
            textView?.pressedStateTextColor("#0000ff", "#00ff00")
            if (i++ % 5 == 0) {
                textView?.pressedStateBackground("#ff0000", "#66ff0000")
            } else {
                textView?.pressedStateBackground("#ff0000", "#66ff0000", 20f)
            }
        }

        layoutParamTest.setOnClickListener {
            val tag = it.tag
            if (tag == null) {
                it.fullWidthWrapHeight()
                it.tag = "A"
            } else {
//                it.wrapContent()
                it.wrapWidthFullHeight()
                it.tag = null
            }
        }

        fastClick.setNoFastClickListener {
            "click--".log()
            thread {
                val toString = input.text.toString()
                val path = "${filesDir}/11/a.txt"
                path.log()
                toString.save2File(path)
            }
        }

        var count = 0
        toast.setNoFastClickListener {
            "hello   ${count++}".toastIt()
        }
    }

}