package com.song.common.extentions.core

import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.song.common.extentions.other.TextViewUtils

class InputKt(lifecycleOwner: LifecycleOwner, private val editText: EditText) {
    private lateinit var textWatcher: TextWatcher
    private val observer: LifecycleEventObserver by lazy {
        LifecycleEventObserver { source, _ ->
            if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                lifecycleOwner.lifecycle.removeObserver(observer)
                editText.removeTextChangedListener(textWatcher)
                editText.background?.callback = null
                TextViewUtils.clearTextLineCache()
            }
        }
    }

    init {
        lifecycleOwner.lifecycle.addObserver(observer)
    }

    fun addClearIcon(
        clearIcon: Int,
        onTextClear: (() -> Unit)? = null
    ) {
        val clearDrawable = ContextCompat.getDrawable(editText.context, clearIcon) ?: return
        editText.setOnTouchListener { _, event ->
            if (event.rawX >= editText.right - clearDrawable.bounds.width() - 20) {
                editText.setText("")
                onTextClear?.invoke()
                return@setOnTouchListener false
            }
            false
        }
        val compoundDrawables = editText.compoundDrawablesRelative
        textWatcher = editText.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                editText.setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0], compoundDrawables[1], null, compoundDrawables[3]
                )
            } else {
                editText.setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawables[0], compoundDrawables[1], clearDrawable, compoundDrawables[3]
                )
            }
        }

    }

    fun addIcon(editText: EditText, icon: Int, right: Boolean = true) {
        if (right) {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                null, null, ContextCompat.getDrawable(editText.context, icon), null
            )
        } else {
            editText.setCompoundDrawablesWithIntrinsicBounds(
                ContextCompat.getDrawable(editText.context, icon),
                null, null, null
            )
        }
    }
}