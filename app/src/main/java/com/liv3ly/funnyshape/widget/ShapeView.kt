package com.liv3ly.funnyshape.widget

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

open class ShapeView : View, GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    private lateinit var mDetector: GestureDetectorCompat
    private var mOnDoubleTapListener: ((shapeView: ShapeView) -> Boolean)? = null

    constructor(
        context: Context
    ) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        mDetector = GestureDetectorCompat(context, this)
        mDetector.setOnDoubleTapListener(this);
    }

    open fun setShapeColor(color: Int) {

    }

    fun setOnDoubleTabListener(onDoubleTapListener: (shapeView: ShapeView) -> Boolean) {
        mOnDoubleTapListener = onDoubleTapListener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {}

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return mOnDoubleTapListener != null && mOnDoubleTapListener!!(this)
    }
}