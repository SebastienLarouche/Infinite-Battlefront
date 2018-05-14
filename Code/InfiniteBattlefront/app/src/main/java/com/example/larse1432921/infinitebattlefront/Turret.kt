package com.example.larse1432921.infinitebattlefront

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.View
import android.widget.ImageView

open class Turret(_type: Char, _x: Int, _y: Int, _range: Float) {
    var type: Char = _type
    var x: Int = _x
    var y: Int = _y
    var range: Float = _range
    var enemyQueue: ArrayList<Enemy> = ArrayList()


    fun addEnemy(enemy: Enemy){
        var enemyExist= false
        enemyQueue.forEach {
            if (it != enemy)
                enemyExist = true
        }
        if(!enemyExist)
            enemyQueue.add(enemy)
    }

    fun removeEnemy(enemy: Enemy){
        var enemyExist= false
        enemyQueue.forEach {
            if (it == enemy)
                enemyExist = true
        }
        if(enemyExist)
            enemyQueue.remove(enemy)
    }

    fun attackEnemy(ammo: Ammo){
        val ammoRect = Rect(ammo.ammoView.x.toInt(),
                ammo.ammoView.y.toInt(),
                (ammo.ammoView.x+ammo.width).toInt(),
                (ammo.ammoView.y+ammo.height).toInt())
        val enemyRect = Rect(enemyQueue.first().enemyView.x.toInt(),
                enemyQueue.first().enemyView.y.toInt(),
                (enemyQueue.first().enemyView.x+enemyQueue.first().enemyView.width).toInt(),
                (enemyQueue.first().enemyView.y+enemyQueue.first().enemyView.height).toInt())

        val animX = ObjectAnimator.ofFloat(ammo.ammoView, "X", enemyQueue.first().enemyView.x+(enemyQueue.first().enemyView.width/2))
        val animY = ObjectAnimator.ofFloat(ammo.ammoView, "Y", enemyQueue.first().enemyView.y+(enemyQueue.first().enemyView.height/2))
        val animXY = AnimatorSet()
        animXY.duration = 200
        animXY.playTogether(animX, animY)

        animXY.start()
        ammo.x = ammo.ammoView.x
        ammo.y = ammo.ammoView.y
        if(ammoRect.intersect(enemyRect)){
            ammo.ammoView.visibility = View.GONE
            enemyQueue.first().enemyView.visibility = View.GONE
        }
    }
}