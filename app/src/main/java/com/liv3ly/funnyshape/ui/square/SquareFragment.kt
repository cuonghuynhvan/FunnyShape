package com.liv3ly.funnyshape.ui.square

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.Utils
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.ViewModelFactory
import com.liv3ly.funnyshape.data.api.APIBuilder
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.widget.ShapeLayout
import com.liv3ly.funnyshape.widget.ShapeView
import com.nimble.survey.module.common.ActionStatus

class SquareFragment : Fragment() {
    private lateinit var squareViewModel: SquareViewModel
    private lateinit var shapeLayout: ShapeLayout
    private lateinit var loadingView: View
    private lateinit var tempShapeView: ShapeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory =
            ViewModelFactory(ShapeRepository(APIBuilder.getBackgroundAPIService()))
        squareViewModel =
            ViewModelProvider(this, viewModelFactory).get(SquareViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_funny_shape, container, false)
        shapeLayout = root.findViewById(R.id.shapeLayout)
        loadingView = root.findViewById(R.id.loadingLayout)
        loadingView.setOnClickListener { }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        bindViewModelAction()
    }

    private fun observeViewModel() {
        squareViewModel.generateActionResult.observe(viewLifecycleOwner, Observer {
            val shapeResult = it ?: return@Observer

            when (shapeResult.status) {
                ActionStatus.SUCCESS -> addShapeView(shapeResult.data!!)
                ActionStatus.LOADING -> showLoading()
                ActionStatus.ERROR -> hideLoading()
            }
        })
        squareViewModel.generateBackgroundActionResult.observe(viewLifecycleOwner, Observer {
            val shapeResult = it ?: return@Observer

            when (shapeResult.status) {
                ActionStatus.SUCCESS -> changeShapeBackground(shapeResult.data!!)
                ActionStatus.LOADING -> showLoading()
                ActionStatus.ERROR -> hideLoading()
            }
        })
    }

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loadingView.visibility = View.GONE
    }

    private fun addShapeView(shape: Shape) {
        hideLoading()
        val shapeView = Utils.addShapeIntoShapeLayout(shape, shapeLayout)

        shapeView.setOnDoubleTabListener {
            tempShapeView = it
            squareViewModel.changeShapeBackground()
            return@setOnDoubleTabListener true
        }
    }

    private fun changeShapeBackground(color: Int) {
        hideLoading()
        tempShapeView.setShapeColor(color)
    }

    private fun bindViewModelAction() {
        val displayMetrics = Resources.getSystem().displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        squareViewModel.setScreenSize(width, height)

        shapeLayout.setOnTapListener {
            if (it == null) {
                return@setOnTapListener false;
            }

            squareViewModel.generateShape(it.x, it.y)
            return@setOnTapListener true
        }
    }
}