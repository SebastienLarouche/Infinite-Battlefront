package com.example.larse1432921.infinitebattlefront

import android.widget.ImageView
import java.util.*

var enemyStack: Stack<Enemy>? = null

open class Turret(_type: Char, _x: Int, _y: Int, _range: Float, _towerView: ImageView) {
    var type: Char = _type
    var x: Int = _x
    var y: Int = _y
    var range: Float = _range
    var towerView: ImageView = _towerView
    val _enemyStack: Stack<Enemy>? = enemyStack
}