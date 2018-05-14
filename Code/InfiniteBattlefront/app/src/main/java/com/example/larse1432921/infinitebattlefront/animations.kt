package com.example.larse1432921.infinitebattlefront

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask




open class Animations : AppCompatActivity(){

    private lateinit var container: ConstraintLayout
    private lateinit var greenTower: ImageView
    private lateinit var redTower: ImageView
    private lateinit var yellowTower: ImageView
    private lateinit var launcher: ImageView
    private var initX: Float = 0.0f
    private var initY: Float = 0.0f
    private var finalX: Float = 0.0f
    private var finalY: Float = 0.0f
    private var turretList = ArrayList<Turret>()
    private var enemyList = ArrayList<Enemy>()
    private val timer = Timer()
    private lateinit var enemyTask: TimerTask


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.test_de_tire)

        container = findViewById(R.id.container) as ConstraintLayout
        greenTower = findViewById(R.id.greenTower) as ImageView
        redTower = findViewById(R.id.redTower) as ImageView
        yellowTower = findViewById(R.id.yellowTower) as ImageView
        launcher = findViewById(R.id.target) as ImageView

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

                    createTurret(view as ImageView, view.x.toInt(), view.y.toInt())

                    view.x = initX
                    view.y = initY

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
        launcher.setOnClickListener{
            launcher.visibility = View.GONE
            enemyTask = timerTask{ createEnemy(launcher)}
            timer.schedule(enemyTask, 1L, 3000L)
        }
    }


    private fun enemyMove(enemy: Enemy){
        val enemyPath = Path()

        enemyPath.run {
            moveTo(0f, 540f)
            lineTo(260f, 540f)
            lineTo(260f, 215f)
            lineTo(650f, 215f)
            lineTo(650f, 650f)
            lineTo(1175f, 650f)
            lineTo(1175f, 430f)
            lineTo(1920f, 430f)
        }
        val objectAnimator  = ObjectAnimator.ofFloat(enemy.enemyView, "x", "y", enemyPath)

        objectAnimator.addUpdateListener {
            enemy.x = enemy.enemyView.x
            enemy.y = enemy.enemyView.y
            inRange(turretList, enemyList)

        }

        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 20000
        objectAnimator.start()
    }


    private fun inRange(turretList: ArrayList<Turret>, enemyList: ArrayList<Enemy>): Boolean{
        var inRange = false
        turretList.forEach{
            for(enemy in enemyList){
                val distance = (Math.sqrt(Math.pow((enemy.x - it.x).toDouble(), 2.0) + Math.pow((enemy.y - it.y).toDouble(), 2.0) ))
                if(distance <= it.range) {
                    inRange = true
                    it.addEnemy(enemy)
                    it.attackEnemy(createAmmo(it))
                } else {
                    inRange = false
                    it.removeEnemy(enemy)
                }
            }
        }
        return inRange
    }


    private fun createTurret(turretView: ImageView, posX: Int, posY: Int){
        val newTurretView = ImageView(this)
        when {
            turretView.id == R.id.greenTower ->{
                val greenTurret = Turret('G', posX, posY, 225f)
                newTurretView.setImageResource(R.drawable.green_tower)
                turretList.add(greenTurret)
            } turretView.id == R.id.redTower -> {
                val redTurret = Turret('R', posX, posY, 175f)
            newTurretView.setImageResource(R.drawable.red_tower)
                turretList.add(redTurret)
            } turretView.id == R.id.yellowTower -> {
                val yellowTurret = Turret('Y', posX, posY, 350f)
            newTurretView.setImageResource(R.drawable.yellow_tower)
                turretList.add(yellowTurret)
            }
        }
        newTurretView.x = posX.toFloat()
        newTurretView.y = posY.toFloat()

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

        ammoView.x = turret.x.toFloat() + 30
        ammoView.y = turret.y.toFloat()

        container.addView(ammoView)

        ammoView.layoutParams.width = 50
        ammoView.layoutParams.height = 65

        return Ammo(ammoView.x, ammoView.y, ammoView)
    }


    private fun createEnemy(launcher: ImageView){

        runOnUiThread {
            val enemyView = ImageView(this)
            enemyView.setImageResource(R.drawable.target)

            enemyView.x = launcher.x
            enemyView.y = launcher.y

            val enemy = Enemy(enemyView.x + enemyView.width / 2, enemyView.y + enemyView.height / 2, enemyView)

            container.addView(enemyView)
            enemyView.layoutParams.height = 100
            enemyView.layoutParams.width = 100

            enemyList.add(enemy)
            enemyMove(enemy)
        }
    }
}