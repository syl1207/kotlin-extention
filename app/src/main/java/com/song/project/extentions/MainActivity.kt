package com.song.project.extentions

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.song.common.extentions.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CustomToast.init(application)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            ),
            1001
        )

        bitmap.setOnClickListener {
            versionCode.toString().log()
            return@setOnClickListener
            goToActivity<BitmapTestActivity>()
        }
        view.setOnClickListener {
            goToActivity<ViewTestActivity>()
        }
        json.setOnClickListener {
            goToActivity<FastJsonTestActivity>()
        }

        logTest.setOnClickListener {
//            goToActivity<LogTestActivity>()
            val imei = getImei()
            toast(imei ?: "imei is null")
        }

        animTest.setNoFastClickListener {
            goToActivity<AnimationTestActivity>()
        }
        animTest.performClick()

        val hasPermission = checkPermission(Manifest.permission.READ_PHONE_STATE)
        checkPermission(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

    }

}