package com.song.common.extentions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun View.animRotate(angle: Float, duration: Long): Animator {
    return animRotate(0f, angle, duration)
}

fun View.animRotate(fromAngle: Float, toAngle: Float, duration: Long): Animator {
    val animator =
        ObjectAnimator.ofFloat(this, "rotation", fromAngle, toAngle).setDuration(duration)
    animator.start()
    return animator
}

fun View.animAlpha(from: Float, to: Float, duration: Long): Animator {
    val animator = ObjectAnimator.ofFloat(this, "alpha", from, to).setDuration(duration)
    animator.start()
    return animator
}

fun View.animScale(ratio: Float, duration: Long): Animator {
    val set = AnimatorSet()
    val animatorX = ObjectAnimator.ofFloat(this, "scaleX", ratio).setDuration(duration)
    val animatorY = ObjectAnimator.ofFloat(this, "scaleY", ratio).setDuration(duration)
    set.playTogether(animatorX, animatorY)
    set.start()
    return set
}

fun View.animTranslateX(value: Int, duration: Long): Animator {
    val animator =
        ObjectAnimator.ofFloat(this, "translationX", value.toFloat()).setDuration(duration)
    animator.start()
    return animator
}

fun View.animTranslateY(value: Int, duration: Long): Animator {
    val animator =
        ObjectAnimator.ofFloat(this, "translationY", value.toFloat()).setDuration(duration)
    animator.start()
    return animator
}