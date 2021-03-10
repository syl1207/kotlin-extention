package com.song.common.extentions

import android.R
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.song.common.extentions.core.InputKt

fun TextView.drawableTop(id: Int?, drawablePadding: Int = 0) {
    customCompoundDrawables(topId = id, drawablePadding = drawablePadding)
}

fun TextView.drawableLeft(id: Int?, drawablePadding: Int = 0) {
    customCompoundDrawables(leftId = id, drawablePadding = drawablePadding)
}

fun TextView.drawableBottom(id: Int?, drawablePadding: Int = 0) {
    customCompoundDrawables(bottomId = id, drawablePadding = drawablePadding)
}

fun TextView.drawableRight(id: Int?, drawablePadding: Int = 0) {
    customCompoundDrawables(rightId = id, drawablePadding = drawablePadding)
}

fun TextView.customCompoundDrawables(
    leftId: Int? = null,
    topId: Int? = null,
    rightId: Int? = null,
    bottomId: Int? = null,
    drawablePadding: Int = 0
) {
    val leftDrawable = getDrawable(context, leftId)
    val topDrawable = getDrawable(context, topId)
    val rightDrawable = getDrawable(context, rightId)
    val bottomDrawable = getDrawable(context, bottomId)
    setCompoundDrawablesWithIntrinsicBounds(
        leftDrawable, topDrawable, rightDrawable, bottomDrawable
    )
    compoundDrawablePadding = drawablePadding
}


private fun getDrawable(context: Context, id: Int?): Drawable? {
    return id?.let {
        ContextCompat.getDrawable(context, it)
    }
}

fun TextView.applyStateTextColor(id: Int) {
    setTextColor(ContextCompat.getColorStateList(context, id))
}

fun TextView.pressedStateTextColor(normalColor: Int, pressedColor: Int) {
    setTextColor(
        pressedStateColor(
            pressedColor,
            normalColor
        )
    )
}


fun TextView.pressedStateTextColor(normalColor: String, pressedColor: String) {
    setTextColor(
        pressedStateColor(
            Color.parseColor(pressedColor),
            Color.parseColor(normalColor)
        )
    )
}

fun TextView.pressedStateBackground(normalColor: Int, pressedColor: Int) {
    val drawable = StateListDrawable()
    drawable.addState(intArrayOf(R.attr.state_pressed), ColorDrawable(pressedColor))
    drawable.addState(intArrayOf(R.attr.state_selected), ColorDrawable(pressedColor))
    drawable.addState(intArrayOf(), ColorDrawable(normalColor))
    background = drawable
}

fun TextView.pressedStateBackground(normalColor: String, pressedColor: String) {
    pressedStateBackground(Color.parseColor(normalColor), Color.parseColor(pressedColor))
}

fun TextView.pressedStateBackground(normalColor: String, pressedColor: String, radius: Float) {
    val pressedDrawable = GradientDrawable()
    pressedDrawable.cornerRadius = radius
    pressedDrawable.setColor(Color.parseColor(pressedColor))

    val normalDrawable = GradientDrawable()
    normalDrawable.cornerRadius = radius
    normalDrawable.setColor(Color.parseColor(normalColor))

    val drawable = StateListDrawable()
    drawable.addState(intArrayOf(R.attr.state_pressed), pressedDrawable)
    drawable.addState(intArrayOf(R.attr.state_selected), pressedDrawable)
    drawable.addState(intArrayOf(), normalDrawable)
    background = drawable
}

private fun pressedStateColor(pressedColor: Int, normalColor: Int): ColorStateList {
    val states = arrayOf(
        intArrayOf(R.attr.state_pressed),
        intArrayOf(R.attr.state_selected),
        intArrayOf()
    )
    val colors = intArrayOf(pressedColor, pressedColor, normalColor)
    return ColorStateList(states, colors)
}


fun View.setNoFastClickListener(onClick: (View) -> Unit) {
    var canClick = true
    setOnClickListener {
        if (canClick) {
            canClick = false
            onClick(it)
            postDelayed({
                canClick = true
            }, 800L)
        }
    }
}

fun View.matchParent() {
    val params = layoutParams ?: return
    params.width = ViewGroup.LayoutParams.MATCH_PARENT
    params.height = ViewGroup.LayoutParams.MATCH_PARENT
    layoutParams = params
}

fun View.fullWidthWrapHeight() {
    val params = layoutParams ?: return
    params.width = ViewGroup.LayoutParams.MATCH_PARENT
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams = params
}

fun View.wrapWidthFullHeight() {
    val params = layoutParams ?: return
    params.width = ViewGroup.LayoutParams.WRAP_CONTENT
    params.height = ViewGroup.LayoutParams.MATCH_PARENT
    layoutParams = params
}

fun View.wrapContent() {
    val params = layoutParams ?: return
    params.width = ViewGroup.LayoutParams.WRAP_CONTENT
    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
    layoutParams = params
}

fun EditText.listenEditorAction(targetActionId: Int, callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == targetActionId) callback.invoke()
        false
    }
}

fun EditText.listenActionDone(callback: () -> Unit) {
    listenEditorAction(EditorInfo.IME_ACTION_DONE, callback)
}

fun EditText.listenActionSend(callback: () -> Unit) {
    listenEditorAction(EditorInfo.IME_ACTION_SEND, callback)
}

fun EditText.listenActionNext(callback: () -> Unit) {
    listenEditorAction(EditorInfo.IME_ACTION_NEXT, callback)
}

fun EditText.listenActionSearch(callback: () -> Unit) {
    listenEditorAction(EditorInfo.IME_ACTION_SEARCH, callback)
}

fun EditText.addClearBtn(resId: Int, onTextCleared: (() -> Unit)?) {
    if (context is LifecycleOwner) {
        InputKt(
            context.safeCast<LifecycleOwner>()!!,
            this
        ).addClearIcon(resId, onTextCleared)
    }
}
//V0.2 end