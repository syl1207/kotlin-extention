package com.song.project.extentions

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.song.common.extentions.goToActivity
import com.song.common.extentions.hasPermission
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
        json.setOnClickListener {
            goToActivity<FastJsonTestActivity>()
        }

        hasPermission(Manifest.permission.READ_PHONE_STATE)
        hasPermission(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}