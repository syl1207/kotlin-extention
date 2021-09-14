package com.song.common.extentions.lifecycle

import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager.widget.ViewPager
import com.song.common.extentions.core.InputKt

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

fun AppCompatActivity.listenOwnerDestroy(callback: () -> Unit) {
    (this as LifecycleOwner).listenOwnerDestroy(callback)
}

fun EditText.watchTextChang(owner: LifecycleOwner, afterTextChange: (Editable?) -> Unit) {
    val watcher = doAfterTextChanged {
        afterTextChange(it)
    }
    owner.listenOwnerDestroy {
        removeTextChangedListener(watcher)
    }
}

fun ViewPager.onPageSelected(lifecycleOwner: LifecycleOwner, callback: (Int) -> Unit) {
    val listener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            callback.invoke(position)
        }
    }
    addOnPageChangeListener(listener)
    lifecycleOwner.listenOwnerDestroy {
        removeOnPageChangeListener(listener)
    }
}

fun EditText.addRightBtn(lifecycleOwner: LifecycleOwner, resId: Int, onTextCleared: (() -> Unit)?) {
    InputKt(lifecycleOwner, this).addClearIcon(resId, onTextCleared)
}