package com.example.larse1432921.infinitebattlefront

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.ImageView

open class Animations : AppCompatActivity(){

    val Pi: Double = 3.1416
    private lateinit var container: ConstraintLayout
    private lateinit var tower: ImageView
    private lateinit var target: ImageView
    private lateinit var enemy: Enemy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.test_de_tire)

        container = findViewById(R.id.container) as ConstraintLayout
        target = findViewById(R.id.target) as ImageView
        tower = findViewById(R.id.tower1) as ImageView

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        container = findViewById(R.id.container) as ConstraintLayout
        target = findViewById(R.id.target) as ImageView
        tower = findViewById(R.id.tower1) as ImageView
        enemy = createEnemy(target)
        createEnemy(target)
        createTurret(tower)
        translation2(enemy)
    }

    /*override fun onStart() {
        super.onStart()
        enemy = createEnemy(target)
        createEnemy(target)
        createTurret(tower)
        translation2(enemy)
    }*/


    private fun translation2(enemy: Enemy){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels.toFloat()
        val valueAnimator = ValueAnimator.ofFloat(0f, screenWidth)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            enemy.enemyView.translationX = value
            enemy.x = enemy.enemyView.x
            enemy.y = enemy.enemyView.y
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 2500
        valueAnimator.start()
    }


    private fun attack(ammo: Ammo){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val animX = ObjectAnimator.ofFloat(ammo.ammoView, "x", target.x)
        val animY = ObjectAnimator.ofFloat(ammo.ammoView, "y", target.y)
        val animXY = AnimatorSet()
        animXY.playTogether(animX, animY)

        animXY.start()
    }


    private fun createTurret(turretView: ImageView){
        val turret = Turret(turretView.x, turretView.y, 300f, tower)
        turretView.setOnClickListener {if(inRange(turret, enemy)){
        attack(createAmmo(turret))}}
    }


    private fun createAmmo(turret: Turret): Ammo{
            val ammoView = ImageView(this)

            ammoView.setImageResource(R.drawable.pepper_ammo)
            ammoView.x = turret.x
            ammoView.y = turret.y

            container.addView(ammoView)

        return Ammo(ammoView.x, ammoView.y, ammoView)
    }

    private fun createEnemy(enemyView: ImageView): Enemy{
        return Enemy(enemyView.x, enemyView.y, enemyView)
    }


    private fun inRange(turret: Turret, enemy: Enemy): Boolean {
        val inRange: Boolean
        val firstPow = enemy.x - turret.x
        val secondPow = enemy.y-turret.y
        val distance = (Math.sqrt(Math.pow(firstPow.toDouble(), 2.0) + Math.pow(secondPow.toDouble(), 2.0) ))

        inRange = distance < turret.range

        return inRange
    }
}