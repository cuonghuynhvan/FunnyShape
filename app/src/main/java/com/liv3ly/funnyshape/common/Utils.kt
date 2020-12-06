package com.liv3ly.funnyshape.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.liv3ly.funnyshape.widget.CircleView
import com.liv3ly.funnyshape.widget.ShapeLayout
import com.liv3ly.funnyshape.widget.ShapeView
import com.liv3ly.funnyshape.widget.SquareView
import kotlin.math.roundToInt


class Utils {
    companion object {
        private fun createViewByShape(context: Context, shape: Shape): ShapeView {
            val shapeView: ShapeView = when (shape) {
                is Square -> SquareView(context)
                is Circle -> CircleView(context)
            }

            return shapeView
        }

        fun addShapeIntoShapeLayout(shape: Shape, shapeLayout: ShapeLayout): ShapeView {
            val shapeView = createViewByShape(shapeLayout.context, shape)
            val layoutParam: FrameLayout.LayoutParams =
                FrameLayout.LayoutParams(shape.size, shape.size)
            layoutParam.leftMargin = (shape.centerX - shape.size / 2).roundToInt()
            layoutParam.topMargin = (shape.centerY - shape.size / 2).roundToInt()

            shapeLayout.addView(shapeView, layoutParam)

            return shapeView
        }

        fun makeRandomInt(maxValue: Int): Int = (Math.random() * maxValue).roundToInt()
    }
}