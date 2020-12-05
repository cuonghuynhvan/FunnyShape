package com.liv3ly.funnyshape.ui.square

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ActionResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SquareViewModel(private val shapeRepository: ShapeRepository) : ViewModel() {

    private val _generateActionResult = MutableLiveData<ActionResult<Shape>>()
    val generateActionResult: LiveData<ActionResult<Shape>> = _generateActionResult

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
            val shape = shapeRepository.generateSquare(screenWidth, screenHeight)
            shape.setCenterPoint(x, y)
            _generateActionResult.postValue(ActionResult.success(shape))
        }
    }
}