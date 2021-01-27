package com.song.project.extentions

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.song.common.extentions.log
import java.util.*

class LogTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn = Button(this)
        btn.setOnClickListener {
            "Date -----${Date()}".log(cache = true)
        }
        setContentView(btn)
    }
}