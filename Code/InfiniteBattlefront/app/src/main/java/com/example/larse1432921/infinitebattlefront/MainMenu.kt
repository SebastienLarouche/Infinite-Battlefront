package com.example.larse1432921.infinitebattlefront

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.LinearInterpolator

class MainMenu : AppCompatActivity() {

    protected lateinit var pepper: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        pepper = findViewById(R.id.pepper_tower)
        pepper.setOnClickListener { rotation(pepper) }
    }

    fun rotation(view: View){
        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float

            pepper.rotation = value
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 500
        valueAnimator.start()
    }
}
