package com.liv3ly.funnyshape.ui.triangle

import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Utils
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.RandomTypeProvider
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class TriangleViewModel(
    private val shapeRepository: ShapeRepository,
    private val randomTypeProvider: RandomTypeProvider
) : ShapeViewModel() {
    override suspend fun callGenerateShape(): Shape =
        shapeRepository.generateTriangle(randomTypeProvider.getRandomBackgroundType())

    override suspend fun callGenerateShapeBackground(shape: Shape): Any =
        shapeRepository.generateBackground(randomTypeProvider.getRandomBackgroundType())
}