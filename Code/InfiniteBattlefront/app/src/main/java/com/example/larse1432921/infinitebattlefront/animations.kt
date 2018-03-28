package com.example.larse1432921.infinitebattlefront

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import android.widget.ImageView

open class animations : AppCompatActivity(){

    private lateinit var pepper: ImageView
    private lateinit var container: ConstraintLayout
    private lateinit var target: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.test_de_tire)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        container = findViewById(R.id.container) as ConstraintLayout
        pepper = findViewById(R.id.pepper_tower) as ImageView
        target = findViewById(R.id.target) as ImageView
        pepper.setOnClickListener {
            createAmmo(pepper)
        }
        translation2(target)
    }
    fun translation2(target: ImageView){
        val displayMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var screenWidth = displayMetrics.widthPixels.toFloat()

        val valueAnimator = ValueAnimator.ofFloat(0f, screenWidth)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            target.translationX = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = BaseAnimation.Companion.baseDuration.toLong()

        valueAnimator.start()
    }

    fun attack(ammo: ImageView){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val animX = ObjectAnimator.ofFloat(ammo, "x", target.x)
        val animY = ObjectAnimator.ofFloat(ammo, "y", target.y)
        val animXY = AnimatorSet()
        animXY.playTogether(animX, animY)

        animXY.start()
        ammo.visibility = View.GONE
    }

    fun translation(ammo: ImageView){
        val displayMetrics = DisplayMetrics()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var screenWidth = displayMetrics.widthPixels.toFloat()

        val valueAnimator = ValueAnimator.ofFloat(ammo.x, target.x)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            ammo.translationX = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = BaseAnimation.Companion.baseDuration.toLong()

        valueAnimator.start()
    }

    fun createAmmo(view: View){

        val ammo = ImageView(this)

        ammo.setImageResource(R.drawable.pepper_ammo)
        ammo.x = view.x
        ammo.y = view.y

        container.addView(ammo)
        attack(ammo)
    }
}