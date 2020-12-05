package com.liv3ly.funnyshape.ui.triangle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.R

class TriangleFragment : Fragment() {

    private lateinit var dashboardViewModel: TriangleViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(TriangleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_funny_shape, container, false)

        return root
    }
}