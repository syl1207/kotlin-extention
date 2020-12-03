package com.song.project.extentions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song.common.extentions.goToActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bitmap.setOnClickListener {
            goToActivity<BitmapTestActivity>()
        }
        view.setOnClickListener {
            goToActivity<ViewTestActivity>()
        }
    }
}