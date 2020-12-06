package com.liv3ly.funnyshape.ui.square

import com.liv3ly.funnyshape.ui.common.ShapeFragment

class SquareFragment : ShapeFragment<SquareViewModel>() {
    override fun getShapeViewModelClass(): Class<SquareViewModel> = SquareViewModel::class.java
}