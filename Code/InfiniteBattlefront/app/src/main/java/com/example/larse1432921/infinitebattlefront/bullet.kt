package com.example.larse1432921.infinitebattlefront

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView

open class bullet : AppCompatActivity{

    private var x: Float
    private var y: Float
    var ammoView: ImageView
    private var towerView: ImageView
    private lateinit var constraintLayout: ConstraintLayout

    constructor(_x: Float, _y: Float, _towerView: ImageView){
        x = _x
        y = _y
        towerView = _towerView

        ammoView = ImageView(this)
        ammoView.setImageResource(R.drawable.pepper_ammo)
        findViewById(R.id.container) as ConstraintLayout
        constraintLayout.addView(ammoView)
    }

    fun OnCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }
}
