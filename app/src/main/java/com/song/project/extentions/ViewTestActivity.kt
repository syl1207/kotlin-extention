package com.song.project.extentions

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.song.common.extentions.*
import kotlinx.android.synthetic.main.activity_view_test.*
import java.io.File

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
            val textView = it.safeCast<TextView>()
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
        }
    }

}