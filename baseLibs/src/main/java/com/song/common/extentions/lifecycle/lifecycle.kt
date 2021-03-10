package com.song.common.extentions.lifecycle

import android.content.Context
import android.text.Editable
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.listenOwnerDestroy(callback: () -> Unit) {
    lifecycle.addObserver(object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
                callback()
                lifecycle.removeObserver(this)
            }
        }
    })
}

fun EditText.watchTextChang(owner: LifecycleOwner, afterTextChange: (Editable?) -> Unit) {
    val watcher = doAfterTextChanged {
        afterTextChange(it)
    }
    owner.listenOwnerDestroy {
        removeTextChangedListener(watcher)
    }
}