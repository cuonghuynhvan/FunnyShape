package com.liv3ly.funnyshape

import android.content.Context
import android.widget.FrameLayout
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Square
import com.liv3ly.funnyshape.widget.ShapeLayout
import com.liv3ly.funnyshape.widget.ShapeView
import com.liv3ly.funnyshape.widget.SquareView
import kotlin.math.roundToInt

class Utils {
    companion object {
        fun createViewByShape(context: Context, shape: Shape): ShapeView {
            val shapeView: ShapeView
            when(shape) {
                is Square -> shapeView = SquareView(context)
            }

            shapeView.setBackgroundColor(shape.backgroundColor)
            return shapeView
        }

        fun addShapeIntoShapeLayout(data: Shape?, shapeLayout: ShapeLayout)  {
            val shape = data ?: return

            val shapeView = createViewByShape(shapeLayout.context, shape)
            val layoutParam: FrameLayout.LayoutParams = FrameLayout.LayoutParams(shape.size, shape.size)
            layoutParam.leftMargin = (shape.centerX - shape.size / 2).roundToInt()
            layoutParam.topMargin = (shape.centerY - shape.size / 2).roundToInt()

            shapeLayout.addView(shapeView, layoutParam)
        }
    }
}