package com.liv3ly.funnyshape.common

import android.content.Context
import android.util.Log
import android.widget.FrameLayout
import androidx.core.view.contains
import com.liv3ly.funnyshape.widget.*
import kotlin.math.roundToInt

class Utils {
    companion object {
        fun createViewByShape(context: Context, shape: Shape): ShapeView = when (shape) {
            is Square -> SquareView(context)
            is Circle -> CircleView(context)
            is Triangle -> TriangleView(context)
        }

        fun addShapeViewIntoShapeLayout(shapeView: ShapeView, shapeLayout: ShapeLayout, shape: Shape) {
            val layoutParam: FrameLayout.LayoutParams =
                FrameLayout.LayoutParams(shape.size, shape.size)
            layoutParam.leftMargin = (shape.centerX - shape.size / 2).roundToInt()
            layoutParam.topMargin = (shape.centerY - shape.size / 2).roundToInt()

            if(!shapeLayout.contains(shapeView)) {
                shapeLayout.addView(shapeView, layoutParam)
            }
        }

        fun makeRandomInt(maxValue: Int): Int {
            val result = (Math.random() * maxValue)
            Log.d("MAKE_RANDOM_INT", "${maxValue}: ${result} - ${result.roundToInt()}")
            return result.roundToInt()
        }
    }
}