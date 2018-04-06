package com.example.larse1432921.infinitebattlefront

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View


abstract class BaseAnimation : AppCompatActivity() {
    protected lateinit var pepper_tower: View
    protected lateinit var pepper_bullet: View
    protected lateinit var frameLayout: View
    protected var screenHeight = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout = findViewById(R.id.container)
        frameLayout.setOnClickListener { onStartAnimation() }
    }

    fun rotationTranslation(view: View){

        /*val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float

            pepper.rotation = value
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 1000
        valueAnimator.start()*/

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var screenWidth = displayMetrics.widthPixels.toFloat()

        val positionAnimator = ValueAnimator.ofFloat(0f, -screenWidth)

        positionAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            view.translationY = value
        }


        val rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 180f)

        val animatorSet = AnimatorSet()

        animatorSet.play(positionAnimator).with(rotationAnimator)

        animatorSet.duration = BaseAnimation.Companion.baseDuration.toLong()
        animatorSet.start()
    }

    protected abstract fun onStartAnimation()

    companion object {
        val baseDuration = 2500
    }
}
