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

class CircleFragment : Fragment() {

    private lateinit var notificationsViewModel: CircleViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(CircleViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_funny_shape, container, false)

        return root
    }
}