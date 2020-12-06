package com.liv3ly.funnyshape.ui.circle

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class CircleViewModel(private val shapeRepository: ShapeRepository) : ShapeViewModel() {
    override suspend fun callGenerateShape(): Shape =
        shapeRepository.generateCircle()

    override suspend fun callGenerateShapeBackground(): Any = shapeRepository.generateColor()
}