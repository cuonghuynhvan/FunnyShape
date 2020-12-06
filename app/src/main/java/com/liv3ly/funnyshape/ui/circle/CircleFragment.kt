package com.liv3ly.funnyshape.ui.circle

import com.liv3ly.funnyshape.ui.common.ShapeFragment

class CircleFragment : ShapeFragment<CircleViewModel>() {
    override fun getShapeViewModelClass(): Class<CircleViewModel> = CircleViewModel::class.java
}