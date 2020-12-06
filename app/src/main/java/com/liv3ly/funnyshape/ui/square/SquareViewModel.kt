package com.liv3ly.funnyshape.ui.square

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class SquareViewModel(private val shapeRepository: ShapeRepository) : ShapeViewModel() {
    override suspend fun callGenerateShape(): Shape =
        shapeRepository.generateSquare()

    override suspend fun callGenerateShapeBackground(shape: Shape): Any =
        shapeRepository.generateImage()
}