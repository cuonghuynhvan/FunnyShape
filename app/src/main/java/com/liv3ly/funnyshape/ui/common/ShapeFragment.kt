package com.liv3ly.funnyshape.ui.common

import android.app.Activity
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorManager
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
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Utils
import com.liv3ly.funnyshape.widget.ShapeLayout
import com.liv3ly.funnyshape.widget.ShapeView
import com.nimble.survey.module.common.ActionStatus


abstract class ShapeFragment<T : ShapeViewModel> : Fragment() {
    private lateinit var viewModel: T
    private lateinit var shapeLayout: ShapeLayout
    private lateinit var loadingView: View
    private lateinit var tempShapeView: ShapeView
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private lateinit var shakeDetector: ShakeDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivity = (activity as MainActivity)
        val viewModelFactory = mainActivity.viewModelFactory
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(getShapeViewModelClass())

        sensorManager = mainActivity.getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        shakeDetector = ShakeDetector {
            handleShakeEvent()
        }
        sensorManager.registerListener(shakeDetector, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(shakeDetector, sensor)
        super.onDestroy()
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
        viewModel.generateShapeActionResult.observe(viewLifecycleOwner, Observer {
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
        val shapeView = Utils.createViewByShape(requireContext(), shape)
        setBackgroundForShapeView(shapeView, shape.background) {
            Utils.addShapeViewIntoShapeLayout(shapeView, shapeLayout, shape)
            startShapeAnimation(shapeView)
            hideLoading()
        }

        shapeView.setOnDoubleTabListener {
            tempShapeView = it
            viewModel.changeShapeBackground(shape)
            return@setOnDoubleTabListener true
        }
    }

    private fun changeShapeBackground(background: Any) {
        setBackgroundForShapeView(tempShapeView, background) {
            hideLoading()
        }
    }

    fun setBackgroundForShapeView(shapeView: ShapeView, background: Any, onDone: () -> Unit) {
        when (background) {
            is Int -> {
                shapeView.shapeBitmap = null
                shapeView.shapeColor = background
                onDone()
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
                            onDone()
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}

                        override fun onStop() {
                            super.onStop()
                            onDone()
                        }
                    })
            }
            else -> {
            }
        }
    }

    private fun startShapeAnimation(shapeView: ShapeView) {
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

    private fun handleShakeEvent() {
        // TODO connect to view model
        shapeLayout.removeAllViews()
    }
}