package com.liv3ly.funnyshape.widget

import android.content.Context
import android.graphics.*
import android.view.MotionEvent


class TriangleView(
    context: Context
) : ShapeView(context) {
    private val paint = Paint()

    fun sign(p1: PointF, p2: PointF, p3: PointF): Float {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y)
    }

    override fun isDownEventInside(event: MotionEvent): Boolean {
        if(event.action != MotionEvent.ACTION_DOWN) {
            return super.isDownEventInside(event)
        }

        val point75PercentWidth = (width * 0.75).toFloat()
        val point75PercentHeight = (height * 0.75).toFloat()

        val point = PointF(event.x, event.y)
        val v1 = PointF(0F, point75PercentHeight)
        val v2 = PointF(point75PercentWidth, 0F)
        val v3 = PointF(width.toFloat(), height.toFloat())

        val d1 = sign(point, v1, v2)
        val d2 = sign(point, v2, v3)
        val d3 = sign(point, v3, v1)

        val hasNeg = d1 < 0 || d2 < 0 || d3 < 0
        val hasPos = d1 > 0 || d2 > 0 || d3 > 0

        return !(hasNeg && hasPos)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) {
            return
        }

        val path = createPath()

        if (shapeBitmap == null) {
            paint.color = shapeColor;
            paint.style = Paint.Style.FILL;
            canvas.drawPath(path, paint)
            return
        }

        val destRect = Rect(0, 0, width, height)
        val nonNullShapeBitmap = shapeBitmap!!
        val srcRect = Rect(0, 0, nonNullShapeBitmap.width, nonNullShapeBitmap.height)
        canvas.clipPath(path)
        canvas.drawBitmap(nonNullShapeBitmap, srcRect, destRect, paint)
    }

    fun createPath(): Path {
        val point75PercentWidth = (width * 0.75).toFloat()
        val point75PercentHeight = (height * 0.75).toFloat()
        val path = Path()
        path.moveTo(0F, point75PercentHeight)
        path.lineTo(point75PercentWidth, 0F)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0F, point75PercentHeight)

        return path
    }
}