package com.liv3ly.funnyshape.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Shape
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

abstract class ShapeViewModel() : ViewModel() {
    private val _generateShapeActionResult = MutableLiveData<ActionResult<Shape>>()
    val generateShapeActionResult: LiveData<ActionResult<Shape>> = _generateShapeActionResult

    private val _generateBackgroundActionResult = MutableLiveData<ActionResult<Any>>()
    val generateBackgroundActionResult: LiveData<ActionResult<Any>> = _generateBackgroundActionResult

    private var screenWidth = 0
    private var screenHeight = 0

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _generateShapeActionResult.postValue(ActionResult.error(R.string.common_error))
    }

    fun setScreenSize(width: Int, height: Int) {
        screenHeight = height
        screenWidth = width
    }

    fun generateShape(x: Float, y: Float) {
        viewModelScope.launch(exceptionHandler) {
            _generateShapeActionResult.postValue(ActionResult.loading())
            val shape = callGenerateShape()
            shape.size = generateRandomSize()
            shape.setCenterPoint(x, y)
            _generateShapeActionResult.postValue(ActionResult.success(shape))
        }
    }

    fun changeShapeBackground(shape: Shape) {
        viewModelScope.launch(exceptionHandler) {
            _generateBackgroundActionResult.postValue(ActionResult.loading())
            val background = callGenerateShapeBackground(shape)
            shape.background = background
            _generateBackgroundActionResult.postValue(ActionResult.success(background))
        }
    }

    private fun generateRandomSize(): Int {
        //        Create a shape at a random size within appropriate ranges.
        //        A shape should not be more than 45% the width or height of the screen size
        //        and should never be less than 10% the width or height.
        val maxSize = Math.min(screenHeight, screenWidth) * 0.45
        val minSize = Math.max(screenHeight, screenWidth) * 0.1

        return (minSize + Math.random() * (maxSize - minSize)).roundToInt()
    }

    abstract suspend fun callGenerateShape(): Shape
    abstract suspend fun callGenerateShapeBackground(shape: Shape): Any
}