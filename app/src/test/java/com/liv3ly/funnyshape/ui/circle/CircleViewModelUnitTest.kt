package com.liv3ly.funnyshape.ui.circle

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Circle
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ActionResult
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CircleViewModelUnitTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var generateShapeActionResultObserver: Observer<ActionResult<Shape>>

    @Mock
    private lateinit var generateBackgroundActionResultObserver: Observer<ActionResult<Any>>

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `Should emit loading state when call generateShape`() {
        val shapeRepository = Mockito.mock(ShapeRepository::class.java)
        val viewModel = CircleViewModel(shapeRepository)
        viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)

        viewModel.generateShape(100F, 200F)

        Mockito.verify(generateShapeActionResultObserver)
            .onChanged(ActionResult.loading())

        viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
    }

    @Test
    fun `Should emit circle that get from ShapeRepository state when call generateShape`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val viewModel = CircleViewModel(shapeRepository)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val shape = Circle(
                background = "test background"
            )
            Mockito.`when`(shapeRepository.generateCircle()).thenReturn(shape)

            viewModel.generateShape(100F, 200F)

            Mockito.verify(generateShapeActionResultObserver)
                .onChanged(ActionResult.success(shape))
            assertEquals(100F, shape.centerX)
            assertEquals(200F, shape.centerY)

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `shape should not be more than 45% the width or height of the screen size and should never be less than 10% the width or height`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val viewModel = CircleViewModel(shapeRepository)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val width = 100
            val height = 200
            viewModel.setScreenSize(width, height)
            val shape = Circle(
                background = "test background"
            )
            Mockito.`when`(shapeRepository.generateCircle()).thenReturn(shape)

            for (i in 1..1000) {
                viewModel.generateShape(100F, 200F)
                assertTrue(shape.size >= 200 * 0.1 && shape.size <= 100 * 0.45)
            }

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `Should emit error when call generateShape and get exception`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val viewModel = CircleViewModel(shapeRepository)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            Mockito.`when`(shapeRepository.generateCircle()).thenThrow(RuntimeException())

            viewModel.generateShape(100F, 200F)

            Mockito.verify(generateShapeActionResultObserver)
                .onChanged(ActionResult.error(R.string.common_error))

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `Should emit loading state when call changeShapeBackground`() {
        val shapeRepository = Mockito.mock(ShapeRepository::class.java)
        val viewModel = CircleViewModel(shapeRepository)
        viewModel.generateBackgroundActionResult.observeForever(
            generateBackgroundActionResultObserver
        )
        val shape = Circle()

        viewModel.changeShapeBackground(shape)

        Mockito.verify(generateBackgroundActionResultObserver)
            .onChanged(ActionResult.loading())

        viewModel.generateBackgroundActionResult.removeObserver(
            generateBackgroundActionResultObserver
        )
    }

    @Test
    fun `Should emit background get from repository when call changeShapeBackground`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            Mockito.`when`(shapeRepository.generateColor()).thenReturn(123456)
            val viewModel = CircleViewModel(shapeRepository)
            viewModel.generateBackgroundActionResult.observeForever(
                generateBackgroundActionResultObserver
            )
            val shape = Circle()

            viewModel.changeShapeBackground(shape)

            Mockito.verify(generateBackgroundActionResultObserver)
                .onChanged(ActionResult.success(123456))

            viewModel.generateBackgroundActionResult.removeObserver(
                generateBackgroundActionResultObserver
            )
        }

    @Test
    fun `Should emit error when call changeShapeBackground and get exception`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val viewModel = CircleViewModel(shapeRepository)
            viewModel.generateShapeActionResult.observeForever(generateBackgroundActionResultObserver)
            Mockito.`when`(shapeRepository.generateColor()).thenThrow(RuntimeException())
            val shape = Circle()

            viewModel.changeShapeBackground(shape)

            Mockito.verify(generateBackgroundActionResultObserver)
                .onChanged(ActionResult.error(R.string.common_error))

            viewModel.generateShapeActionResult.removeObserver(generateBackgroundActionResultObserver)
        }
}