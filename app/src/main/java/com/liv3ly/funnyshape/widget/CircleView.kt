package com.liv3ly.funnyshape.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

class CircleView(context: Context) : ShapeView(context) {
    private val paint = Paint()

    override fun isDownEventInside(event: MotionEvent): Boolean {
        if(event.action != MotionEvent.ACTION_DOWN) {
            return super.isDownEventInside(event)
        }
        val x = event.x
        val y  = event.y
        val centerPoint: Float = width / 2F
        val radius = centerPoint
        val pointToCenterSquareDelta = (x - centerPoint) * (x - centerPoint) + (y - centerPoint) * (y - centerPoint)

        return pointToCenterSquareDelta < (radius * radius)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) {
            return
        }

        val x: Float = width / 2F
        paint.setColor(shapeColor)
        canvas.drawCircle(x, x, x, paint)
    }
}