package com.liv3ly.funnyshape.ui.square

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class SquareViewModel(private val shapeRepository: ShapeRepository) : ShapeViewModel() {
    override suspend fun calGenerateShape(screenWidth: Int, screenHeight: Int): Shape =
        shapeRepository.generateSquare(screenWidth, screenHeight)

    override suspend fun calGenerateShapeBackground(): Any = shapeRepository.generateColor()
}