package com.song.common.extentions

import android.app.Application
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

object CustomToast {
    private const val BACKGROUND_COLOR = Color.BLACK
    private const val BACKGROUND_RADIUS = 10f
    private const val TEXT_COLOR = Color.WHITE

    private lateinit var context: Application
    private var toast: Toast? = null
    fun init(application: Application) {
        context = application
    }

    fun show(msg: String, time: Int = Toast.LENGTH_SHORT) {
        if (toast == null) {
            val textView = buildText(msg)
            toast = Toast(context)
            toast!!.setGravity(Gravity.BOTTOM, 0, 0)
            toast!!.duration = time
            toast!!.view = textView
        } else {
            try {
                (toast!!.view as TextView).text = msg
            } catch (e: Exception) {
            }
        }
        toast?.show()
    }

    private fun buildText(msg: String): TextView {
        val textView = TextView(context)
        textView.text = msg
        textView.gravity = Gravity.CENTER
        textView.width = context.dp2Px(166f)
        textView.setTextColor(TEXT_COLOR)
        textView.background = getRadiusDrawable()
        textView.textSize = 14f
        textView.setPadding(25, 25, 25, 25)
        textView.setLineSpacing(8f, 1f)
        return textView
    }

    private fun getRadiusDrawable(
        color: Int = BACKGROUND_COLOR,
        radius: Float = BACKGROUND_RADIUS
    ) = GradientDrawable().apply {
        cornerRadius = radius
        setColor(color)
    }
}

fun toast(msg: String) {
    CustomToast.show(msg)
}

fun longToast(msg: String) {
    CustomToast.show(msg)
}

fun String.toastIt() {
    CustomToast.show(this)
}
