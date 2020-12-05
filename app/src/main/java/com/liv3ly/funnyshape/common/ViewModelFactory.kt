package com.liv3ly.funnyshape.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.square.SquareViewModel

class ViewModelFactory constructor(
    private val shapeRepository: ShapeRepository,
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SquareViewModel::class.java)) {
            return SquareViewModel(shapeRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}