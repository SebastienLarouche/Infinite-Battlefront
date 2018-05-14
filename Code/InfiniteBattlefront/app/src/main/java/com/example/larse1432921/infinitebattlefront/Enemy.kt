package com.example.larse1432921.infinitebattlefront

import android.widget.ImageView

class Enemy(_x: Float, _y: Float, _enemyView: ImageView) {
    var x = _x
    var y = _y
    var enemyView = _enemyView
    /*fun move(turretList: ArrayList<Turret>, enemy: Enemy){
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
        val objectAnimator  = ObjectAnimator.ofFloat(enemyView, "x", "y", enemyPath)

        objectAnimator.addUpdateListener {
            x = enemyView.x
            y = enemyView.y
            enemyInRange(turretList, enemy)
        }

        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 20000
        objectAnimator.start()
    }

    fun enemyInRange(turretList: ArrayList<Turret>, enemy: Enemy){
        turretList.forEach{
            val distance = (Math.sqrt(Math.pow((enemy.x - it.x).toDouble(), 2.0) + Math.pow((enemy.y - it.y).toDouble(), 2.0) ))
            if(distance <= it.range) {
                it.addEnemy(enemy)
            }
        }
    }*/
}