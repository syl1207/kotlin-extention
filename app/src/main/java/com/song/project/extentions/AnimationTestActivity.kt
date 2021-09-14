package com.song.project.extentions

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.song.common.extentions.*
import kotlinx.android.synthetic.main.anim_test.*

class AnimationTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anim_test)
        var currentAngle: Float = 0f
        roateTest.setOnClickListener {
            val targetAngle = currentAngle + 90f
            img.animRotate(currentAngle, targetAngle, 2000L)
            currentAngle = targetAngle
        }
        translateTest.setOnClickListener {
            img.animTranslateX(200, 2000L).doOnEnd {
                img.animTranslateY(200, 2000L).doOnEnd {
                    img.animTranslateX(0, 2000L).doOnEnd {
                        img.animTranslateY(0, 2000L)
                    }
                }
            }
        }
        alphaTest.setOnClickListener {
            if (img.alpha == 0f) {
                img.animAlpha(0f, 1f, 2000L)
            } else {
                img.animAlpha(1f, 0f, 2000L)
            }
        }
        scaleTest.setOnClickListener { view ->
            Log.e("krik", " ${view.width} : ${view.height} ")
            img.animScale(2f, 2000L).doOnEnd {
                Log.e("krik", " ${view.width} : ${view.height} ")
                img.animScale(1f, 2000L)
            }
        }
    }
}