package com.example.larse1432921.infinitebattlefront

import android.animation.AnimatorSet
import android.animation.ObjectAnimator.ofFloat
import android.annotation.SuppressLint
import android.graphics.Path
import android.os.Bundle
import android.provider.ContactsContract
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.util.Log
import java.util.*


open class Animations : AppCompatActivity(){

    private lateinit var container: ConstraintLayout
    private lateinit var greenTower: ImageView
    private lateinit var redTower: ImageView
    private lateinit var yellowTower: ImageView
    private lateinit var target: ImageView
    private lateinit var enemy: Enemy
    private var initX: Float = 0.0f
    private var initY: Float = 0.0f
    private var finalX: Float = 0.0f
    private var finalY: Float = 0.0f
    private var turretList = ArrayList<Turret>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.test_de_tire)

        container = findViewById(R.id.container) as ConstraintLayout
        greenTower = findViewById(R.id.greenTower) as ImageView
        redTower = findViewById(R.id.redTower) as ImageView
        yellowTower = findViewById(R.id.yellowTower) as ImageView
        target = findViewById(R.id.target) as ImageView

        val listener = View.OnTouchListener(function = { view, motionEvent ->

            val action = motionEvent.action

            when (action){
                MotionEvent.ACTION_DOWN -> {
                    initX = view.x
                    initY = view.y

                    Log.d("TAG", "ACTION_DOWN $initX $initY")
                }
                MotionEvent.ACTION_MOVE -> {
                    view.y = motionEvent.rawY - view.height/2
                    view.x = motionEvent.rawX - view.width/2

                    Log.d("TAG", "ACTION_MOVE")
                }
                MotionEvent.ACTION_UP -> {
                    finalX = motionEvent.rawX
                    finalY = motionEvent.rawY
                    view.x = initX
                    view.y = initY

                    placeTurret(view)

                    Log.d("TAG", "ACTION_UP $finalX $finalY")
                }
            }
            true
        })

        greenTower.setOnTouchListener(listener)
        redTower.setOnTouchListener(listener)
        yellowTower.setOnTouchListener(listener)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onStart() {
        super.onStart()
        enemy = createEnemy(target)
        target.setOnClickListener{translation(enemy)}
    }


    private fun inRange(turretStack: ArrayList<Turret>, enemy: Enemy){
        turretStack.forEach{
            val distance = (Math.sqrt(Math.pow((enemy.x - it.x).toDouble(), 2.0) + Math.pow((enemy.y - it.y).toDouble(), 2.0) ))
            if(distance <= it.range){
                it._enemyStack?.addElement(enemy)
                attack(it)
            }
        }
    }


    private fun translation(enemy: Enemy){
        val enemyPath: Path = Path()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidth = displayMetrics.widthPixels.toFloat()

        enemyPath.moveTo(0f, 540f)
        enemyPath.lineTo(260f, 540f)
        enemyPath.lineTo(260f, 215f)
        enemyPath.lineTo(650f, 215f)
        enemyPath.lineTo(650f, 650f)
        enemyPath.lineTo(1175f, 650f)
        enemyPath.lineTo(1175f, 430f)
        enemyPath.lineTo(screenWidth, 430f)
        val objectAnimator  = ofFloat(
                enemy.enemyView,
                "x",
                "y",
                enemyPath
        )

        objectAnimator.addUpdateListener {
            enemy.x = enemy.enemyView.x
            enemy.y = enemy.enemyView.y
            inRange(turretList, enemy)
        }

        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 10000
        objectAnimator.start()
    }


    private fun attack(turret: Turret){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
3
        val ammo: Ammo = createAmmo(turret)
        val animX = ofFloat(ammo.ammoView, "x", target.x+(target.width/2))
        val animY = ofFloat(ammo.ammoView, "y", target.y+(target.height/2))
        val animXY = AnimatorSet()
        animXY.duration = 1
        animXY.playTogether(animX, animY)

        animXY.start()
    }


    private fun createTurret(turretView: ImageView){
        val newTurretView = ImageView(this)
        when {
            turretView.id == R.id.greenTower ->{
                val greenTurret = Turret('G',turretView.x, turretView.y, 175f, greenTower)
                newTurretView.setImageResource(R.drawable.green_tower)
                turretList.add(greenTurret)
            } turretView.id == R.id.redTower -> {
                val redTurret = Turret('R', turretView.x, turretView.y, 100f, redTower)
            newTurretView.setImageResource(R.drawable.red_tower)
                turretList.add(redTurret)
            } turretView.id == R.id.yellowTower -> {
                val yellowTurret = Turret('Y', turretView.x, turretView.y, 350f, yellowTower)
            newTurretView.setImageResource(R.drawable.yellow_tower)
                turretList.add(yellowTurret)
            }
        }
        newTurretView.x = turretView.x
        newTurretView.y = turretView.y
        container.addView(newTurretView)
    }


    private fun createAmmo(turret: Turret): Ammo {
        val ammoView = ImageView(this)

        when{ turret.type == 'G' -> {
                ammoView.setImageResource(R.drawable.green_ammo)
            } turret.type == 'R' -> {
                ammoView.setImageResource(R.drawable.red_ammo)
            } turret.type == 'Y' -> {
                ammoView.setImageResource(R.drawable.yellow_ammo)
            }
        }

        ammoView.x = turret.x + 30
        ammoView.y = turret.y

        container.addView(ammoView)

        return Ammo(ammoView.x, ammoView.y, ammoView)
    }

    private fun createEnemy(enemyView: ImageView): Enemy{
        return Enemy(enemyView.x+enemyView.width/2, enemyView.y+enemyView.height/2, enemyView)
    }

    private fun placeTurret(view: View){
        val posX = view.x/1920*40
        val posY = view.y/1080*20

        createTurret(view as ImageView)
    }
}