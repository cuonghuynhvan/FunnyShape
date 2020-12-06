package com.liv3ly.funnyshape.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Shape
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class ShapeViewModel() : ViewModel() {

    private val _generateActionResult = MutableLiveData<ActionResult<Shape>>()
    val generateActionResult: LiveData<ActionResult<Shape>> = _generateActionResult

    private val _generateBackgroundActionResult = MutableLiveData<ActionResult<Any>>()
    val generateBackgroundActionResult: LiveData<ActionResult<Any>> = _generateBackgroundActionResult

    private var screenWidth = 0
    private var screenHeight = 0

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _generateActionResult.postValue(ActionResult.error(R.string.common_error))
    }

    fun setScreenSize(width: Int, height: Int) {
        screenHeight = height
        screenWidth = width
    }

    fun generateShape(x: Float, y: Float) {
        viewModelScope.launch(exceptionHandler) {
            _generateActionResult.postValue(ActionResult.loading())
            val shape = calGenerateShape(screenHeight, screenHeight)
            shape.setCenterPoint(x, y)
            _generateActionResult.postValue(ActionResult.success(shape))
        }
    }

    fun changeShapeBackground() {
        viewModelScope.launch(exceptionHandler) {
            _generateBackgroundActionResult.postValue(ActionResult.loading())
            val color = calGenerateShapeBackground()
            _generateBackgroundActionResult.postValue(ActionResult.success(color))
        }
    }

    abstract suspend fun calGenerateShape(screenWidth: Int, screenHeight: Int): Shape
    abstract suspend fun calGenerateShapeBackground(): Any
}