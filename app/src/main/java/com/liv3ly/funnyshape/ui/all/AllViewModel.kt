package com.liv3ly.funnyshape.ui.all

import com.liv3ly.funnyshape.common.*
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.RandomTypeProvider
import com.liv3ly.funnyshape.ui.common.ShapeViewModel

class AllViewModel(
    private val shapeRepository: ShapeRepository,
    private val randomTypeProvider: RandomTypeProvider
) : ShapeViewModel() {
    override suspend fun callGenerateShape(): Shape =
        when (randomTypeProvider.getRandomShapeType()) {
            Constant.SHAPE_TYPE_SQUARE -> shapeRepository.generateSquare()
            Constant.SHAPE_TYPE_CIRCLE -> shapeRepository.generateCircle()
            else -> shapeRepository.generateTriangle(randomTypeProvider.getRandomBackgroundType())
        }

    override suspend fun callGenerateShapeBackground(shape: Shape): Any = when (shape) {
        is Square -> shapeRepository.generateImage()
        is Circle -> shapeRepository.generateColor()
        is Triangle -> shapeRepository.generateBackground(randomTypeProvider.getRandomBackgroundType())
    }
}