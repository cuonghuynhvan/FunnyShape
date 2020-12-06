package com.liv3ly.funnyshape.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class SquareView(
    context: Context
) : ShapeView(context) {
    private val paint = Paint()

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) {
            return
        }

        val destRect = Rect(0, 0, width, height)

        if(shapeBitmap == null) {
            paint.color = shapeColor
            canvas.drawRect(destRect, paint)
            return
        }

        val nonNullShapeBitmap = shapeBitmap!!
        val srcRect = Rect(0, 0, nonNullShapeBitmap.width, nonNullShapeBitmap.height)
        canvas.drawBitmap(nonNullShapeBitmap, srcRect, destRect, paint)
    }
}