package com.song.project.extentions

import android.Manifest
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import com.song.common.extentions.*
import kotlinx.android.synthetic.main.activity_bitmap_test.*
import java.io.File

class BitmapTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap_test)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        val bitmap = bitmap(R.mipmap.test)
        img.setImageBitmap(bitmap)
        rotate.setOnClickListener {
            val src = img.drawToBitmap()
            val newBitmap = src.rotate(90f)
            img.setImageBitmap(newBitmap)
        }
        scale1.setOnClickListener {
            val src = img.drawToBitmap()
            img.setImageBitmap(src.scale(src.width + 50, src.height + 50))
        }
        scale2.setOnClickListener {
            img.setImageBitmap(img.drawToBitmap().scale(0.85f))
        }
        save.setOnClickListener {
            val file = File(Environment.getExternalStorageDirectory(), "AAA.jpg")
            file.absolutePath.log()
            val toBitmap = bitmap.compress(30_000)?.toBitmap()
            toBitmap?.save(file)
            "${file.length()}".log()
        }
    }
}