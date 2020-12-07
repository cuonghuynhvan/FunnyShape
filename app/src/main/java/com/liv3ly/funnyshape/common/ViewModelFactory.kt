package com.liv3ly.funnyshape.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.all.AllViewModel
import com.liv3ly.funnyshape.ui.circle.CircleViewModel
import com.liv3ly.funnyshape.ui.common.RandomTypeProvider
import com.liv3ly.funnyshape.ui.square.SquareViewModel
import com.liv3ly.funnyshape.ui.triangle.TriangleViewModel

class ViewModelFactory constructor(
    private val shapeRepository: ShapeRepository,
    private val typeProvider: RandomTypeProvider
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SquareViewModel::class.java)) {
            return SquareViewModel(shapeRepository) as T
        }

        if (modelClass.isAssignableFrom(CircleViewModel::class.java)) {
            return CircleViewModel(shapeRepository) as T
        }

        if (modelClass.isAssignableFrom(TriangleViewModel::class.java)) {
            return TriangleViewModel(shapeRepository, typeProvider) as T
        }

        if (modelClass.isAssignableFrom(AllViewModel::class.java)) {
            return AllViewModel(shapeRepository, typeProvider) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}