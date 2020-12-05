package com.liv3ly.funnyshape.ui.circle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.ui.triangle.TriangleViewModel

class CircleFragment : Fragment() {

    private lateinit var circleViewModel: CircleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        circleViewModel =
            ViewModelProvider(this).get(CircleViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_funny_shape, container, false)

        return root
    }
}