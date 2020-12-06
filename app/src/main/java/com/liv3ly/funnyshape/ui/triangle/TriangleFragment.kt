package com.liv3ly.funnyshape.ui.triangle

import com.liv3ly.funnyshape.ui.common.ShapeFragment

class TriangleFragment : ShapeFragment<TriangleViewModel>() {
    override fun getShapeViewModelClass(): Class<TriangleViewModel> = TriangleViewModel::class.java
}