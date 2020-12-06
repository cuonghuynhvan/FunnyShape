package com.liv3ly.funnyshape.ui.circle

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class CircleViewModel(private val shapeRepository: ShapeRepository) : ShapeViewModel() {
    override suspend fun callGenerateShape(screenWidth: Int, screenHeight: Int): Shape =
        shapeRepository.generateCircle(screenWidth, screenHeight)

    override suspend fun callGenerateShapeBackground(): Any = shapeRepository.generateColor()
}