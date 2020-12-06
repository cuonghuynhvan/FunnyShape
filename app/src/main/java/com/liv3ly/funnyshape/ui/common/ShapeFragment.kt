package com.liv3ly.funnyshape.ui.common

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.liv3ly.funnyshape.MainActivity
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Constant
import com.liv3ly.funnyshape.common.Utils
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.ViewModelFactory
import com.liv3ly.funnyshape.data.api.APIBuilder
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.widget.CircleView
import com.liv3ly.funnyshape.widget.ShapeLayout
import com.liv3ly.funnyshape.widget.ShapeView
import com.liv3ly.funnyshape.widget.SquareView
import com.nimble.survey.module.common.ActionStatus

abstract class ShapeFragment<T : ShapeViewModel> : Fragment() {
    private lateinit var viewModel: T
    private lateinit var shapeLayout: ShapeLayout
    private lateinit var loadingView: View
    private lateinit var tempShapeView: ShapeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelFactory = (activity as MainActivity).viewModelFactory
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
        val shapeView = Utils.addShapeIntoShapeLayout(shape, shapeLayout)
        setBackgroundForShapeView(shapeView, shape.background)

        shapeView.setOnDoubleTabListener {
            tempShapeView = it
            viewModel.changeShapeBackground(shape)
            return@setOnDoubleTabListener true
        }
    }

    private fun changeShapeBackground(background: Any) {
        setBackgroundForShapeView(tempShapeView, background)
    }

    fun setBackgroundForShapeView(shapeView: ShapeView, background: Any) {
        when (background) {
            is Int -> {
                shapeView.shapeBitmap = null
                shapeView.shapeColor = background
                startShapeAnimation(shapeView)
                hideLoading()
            }
            is String -> {
                Glide.with(shapeView).asBitmap().load(background)
                    .centerCrop()
                    .override(shapeView.width, shapeView.height)
                    .into(object : CustomTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {
                            shapeView.shapeBitmap = resource
                            startShapeAnimation(shapeView)
                            hideLoading()
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}

                        override fun onStop() {
                            super.onStop()
                            hideLoading()
                        }
                    })
            }
            else -> {
            }
        }
    }

    private fun startShapeAnimation(shapeView: ShapeView)  {
        val anim = AnimationUtils.loadAnimation(context, R.anim.anim_appear)
        shapeView.startAnimation(anim)
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