package com.song.project.extentions

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TestActivity : AppCompatActivity() {
    private val service: ExecutorService = Executors.newCachedThreadPool()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this)
        setContentView(root)

        service.execute {

        }
        service.shutdown()
    }


    fun log(string: String) {
        Log.e("krik", string)
    }

    class Item(index: Int)
}