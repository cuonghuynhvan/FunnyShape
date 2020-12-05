package com.liv3ly.funnyshape.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.R

class AllFragment : Fragment() {

    private lateinit var allViewModel: AllViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allViewModel =
                ViewModelProvider(this).get(AllViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_funny_shape, container, false)

        return root
    }
}