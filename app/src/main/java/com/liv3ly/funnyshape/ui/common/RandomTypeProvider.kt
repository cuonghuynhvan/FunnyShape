package com.liv3ly.funnyshape.ui.common

import com.liv3ly.funnyshape.common.Utils

class RandomTypeProvider {
    fun getRandomBackgroundType() : Int = Utils.makeRandomInt(1)

    fun getRandomShapeType() : Int = Utils.makeRandomInt(2)
}