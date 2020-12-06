package com.liv3ly.funnyshape.widget

import android.content.Context
import android.view.View

class SquareView @JvmOverloads constructor(
    context: Context
) : ShapeView(context) {

    override fun setShapeColor(color: Int) {
        setBackgroundColor(color)
    }
}