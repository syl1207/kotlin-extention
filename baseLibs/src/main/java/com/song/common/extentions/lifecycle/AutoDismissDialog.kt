package com.song.common.extentions.lifecycle

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

open class AutoDismissDialog(private val owner: LifecycleOwner, context: Context) :
    Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lifecycle = owner.lifecycle
        val observer = LifecycleEventObserver { source, _ ->
            if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                dismiss()
            }
        }
        lifecycle.addObserver(observer)
        setOnDismissListener {
            lifecycle.removeObserver(observer)
        }
    }
}
