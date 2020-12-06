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

    private val _generateActionResult = MutableLiveData<ActionResult<Shape>>()
    val generateActionResult: LiveData<ActionResult<Shape>> = _generateActionResult

    private val _generateBackgroundActionResult = MutableLiveData<ActionResult<Any>>()
    val generateBackgroundActionResult: LiveData<ActionResult<Any>> = _generateBackgroundActionResult

    private var screenWidth = 0
    private var screenHeight = 0

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _generateActionResult.postValue(ActionResult.error(R.string.common_error))
    }

    fun setScreenSize(width: Int, height: Int) {
        screenHeight = height
        screenWidth = width
    }

    fun generateShape(x: Float, y: Float) {
        viewModelScope.launch(exceptionHandler) {
            _generateActionResult.postValue(ActionResult.loading())
            val shape = callGenerateShape()
            shape.size = generateRandomSize()
            shape.setCenterPoint(x, y)
            _generateActionResult.postValue(ActionResult.success(shape))
        }
    }

    fun changeShapeBackground() {
        viewModelScope.launch(exceptionHandler) {
            _generateBackgroundActionResult.postValue(ActionResult.loading())
            val color = callGenerateShapeBackground()
            _generateBackgroundActionResult.postValue(ActionResult.success(color))
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
    abstract suspend fun callGenerateShapeBackground(): Any
}