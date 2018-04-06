package com.example.larse1432921.infinitebattlefront

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager

class MainMenu : AppCompatActivity() {

    protected lateinit var start: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        start = findViewById(R.id.play)
        start.setOnClickListener { startGame() }
    }

    private fun startGame() {
        val changeMenu = Intent(this, Animations::class.java)
        startActivity(changeMenu)
    }
}