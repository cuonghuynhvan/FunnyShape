package com.liv3ly.funnyshape.common

import android.graphics.Color
import androidx.annotation.ColorInt

sealed class Shape {
    open var centerX: Float = 0F
    open var centerY: Float = 0F
    open var size: Int = 0

    open var background: Any = Color.BLACK

    fun setCenterPoint(x: Float, y: Float) {
        centerX = x
        centerY = y
    }
}

data class Square(
    override var centerX: Float = 0F,
    override var centerY: Float = 0F,
    override var size: Int = 0,
    override var background: Any = Color.BLACK,
) : Shape()

data class Circle(
    override var centerX: Float = 0F,
    override var centerY: Float = 0F,
    override var size: Int = 0,
    override var background: Any = Color.BLACK,
) : Shape()

data class Triangle(
    override var centerX: Float = 0F,
    override var centerY: Float = 0F,
    override var size: Int = 0,
    override var background: Any = Color.BLACK,
) : Shape()
