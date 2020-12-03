package com.song.project.extentions

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.annotation.JSONCreator
import com.song.common.extentions.*

class FastJsonTestActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = TextView(this)
        textView.textSize = 18f
        setContentView(textView)
        textView.setOnClickListener {
//            testJsonObject()
            testJsonArray()
        }
        textView.performClick()
    }

    private fun testJsonObject() {
        val str = createJsonObjectStr()
        showMsg("原始json : $str", true)

        str.toJsonObjectOrNull()?.let {
            showMsg(it.string("id"))
            showMsg(it.string("name"))

            it.toObjectOrNull<KItem>()?.let { item ->
                showMsg(item.toString())
            } ?: showMsg("null")
        }
    }

    private fun testJsonArray() {
        val str = createJsonArrayStr()
        showMsg("原始json : $str", true)

        str.toJsonArrayOrNull()?.let {
            showMsg(it.size.toString())
            showMsg(it[2].toString())
        }

        str.toObjectListOrNull<KItem>()?.let {
            showMsg("----------bean-------")
            showMsg(it.size.toString())
            showMsg(it[2].toString())
        }
    }

    private fun showMsg(s: String, clear: Boolean = false) {
        if (clear) {
            textView.text = s
        } else {
            textView.append("\n$s")
        }
        s.log()
    }


    private fun createJsonArrayStr(): String {
        val mutableList = mutableListOf<KItem>()
        mutableList.add(KItem(188, "张111"))
        mutableList.add(KItem(1, "王222"))
        mutableList.add(KItem(100, "李222"))
        return JSON.toJSONString(mutableList)
    }

    private fun createJsonObjectStr(): String {
        return JSON.toJSONString(KItem(666, "张hhahh"))
    }

    class KItem(var id: Int, var name: String) {
        override fun toString(): String = "{name =$name,id =$id}"
    }

    private fun String.log() {
        Log.e("Test", this)
    }
}