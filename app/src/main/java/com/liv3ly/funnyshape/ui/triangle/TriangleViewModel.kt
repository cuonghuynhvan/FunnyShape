package com.liv3ly.funnyshape.ui.triangle

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Utils
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class TriangleViewModel(private val shapeRepository: ShapeRepository) : ShapeViewModel() {
    override suspend fun callGenerateShape(): Shape =
        shapeRepository.generateTriangle(Utils.makeRandomInt(1))

    override suspend fun callGenerateShapeBackground(shapeType: Int): Any =
        shapeRepository.generateBackground(Utils.makeRandomInt(1))
}