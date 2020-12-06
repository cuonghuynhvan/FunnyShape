package com.liv3ly.funnyshape.ui.all

import com.liv3ly.funnyshape.ui.common.ShapeFragment

class AllFragment : ShapeFragment<AllViewModel>() {
    override fun getShapeViewModelClass(): Class<AllViewModel> = AllViewModel::class.java
}