package com.song.project.extentions

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.song.common.extentions.goToActivity
import com.song.common.extentions.hasPermission
import com.song.common.extentions.log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG){
            startActivity(Intent(this,TestActivity::class.java))
            finish()
        }
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1001
        )

        bitmap.setOnClickListener {
            goToActivity<BitmapTestActivity>()
        }
        view.setOnClickListener {
            goToActivity<ViewTestActivity>()
        }
        json.setOnClickListener {
            goToActivity<FastJsonTestActivity>()
        }

        logTest.setOnClickListener {
            goToActivity<LogTestActivity>()
        }
        val hasPermission = hasPermission(Manifest.permission.READ_PHONE_STATE)
        hasPermission.toString().log()
        hasPermission(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}