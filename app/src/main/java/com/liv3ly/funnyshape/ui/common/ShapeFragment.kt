package com.liv3ly.funnyshape.ui.common

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

abstract class ShapeFragment<T : ShapeViewModel> : Fragment() {
    private lateinit var viewModel: T
    private lateinit var shapeLayout: ShapeLayout
    private lateinit var loadingView: View
    private lateinit var tempShapeView: ShapeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory =
            ViewModelFactory(ShapeRepository(APIBuilder.getBackgroundAPIService()))
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(getShapeViewModelClass())
    }

    abstract fun getShapeViewModelClass(): Class<T>

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
        viewModel.generateActionResult.observe(viewLifecycleOwner, Observer {
            val shapeResult = it ?: return@Observer

            when (shapeResult.status) {
                ActionStatus.SUCCESS -> addShapeView(shapeResult.data!!)
                ActionStatus.LOADING -> showLoading()
                ActionStatus.ERROR -> hideLoading()
            }
        })
        viewModel.generateBackgroundActionResult.observe(viewLifecycleOwner, Observer {
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
            viewModel.changeShapeBackground()
            return@setOnDoubleTabListener true
        }
    }

    private fun changeShapeBackground(background: Any) {
        hideLoading()

        if (background is Int) {
            tempShapeView.shapeColor = background
        }
    }

    private fun bindViewModelAction() {
        val displayMetrics = Resources.getSystem().displayMetrics
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        viewModel.setScreenSize(width, height)

        shapeLayout.setOnTapListener {
            if (it == null) {
                return@setOnTapListener false
            }

            viewModel.generateShape(it.x, it.y)
            return@setOnTapListener true
        }
    }
}