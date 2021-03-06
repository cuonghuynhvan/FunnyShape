package com.liv3ly.funnyshape.ui.all

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liv3ly.funnyshape.R
import com.liv3ly.funnyshape.common.Circle
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Square
import com.liv3ly.funnyshape.common.Triangle
import com.liv3ly.funnyshape.repository.ShapeRepository
import com.liv3ly.funnyshape.ui.common.ActionResult
import com.liv3ly.funnyshape.ui.common.RandomTypeProvider
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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AllViewModelUnitTest {
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
        val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
        val viewModel = AllViewModel(shapeRepository, typeProvider)
        viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)

        viewModel.generateShape(100F, 200F)

        Mockito.verify(generateShapeActionResultObserver)
            .onChanged(ActionResult.loading())

        viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
    }

    @Test
    fun `Should emit square that get from ShapeRepository state when call generateShape`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val shape = Square(
                background = "test background"
            )
            Mockito.`when`(typeProvider.getRandomShapeType()).thenReturn(0)
            Mockito.`when`(shapeRepository.generateSquare()).thenReturn(shape)

            viewModel.generateShape(100F, 200F)

            Mockito.verify(generateShapeActionResultObserver)
                .onChanged(ActionResult.success(shape))
            assertEquals(100F, shape.centerX)
            assertEquals(200F, shape.centerY)

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `Should emit circle that get from ShapeRepository state when call generateShape`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val shape = Circle(
                background = 123456
            )
            Mockito.`when`(typeProvider.getRandomShapeType()).thenReturn(1)
            Mockito.`when`(shapeRepository.generateCircle()).thenReturn(shape)

            viewModel.generateShape(100F, 200F)

            Mockito.verify(generateShapeActionResultObserver)
                .onChanged(ActionResult.success(shape))
            assertEquals(100F, shape.centerX)
            assertEquals(200F, shape.centerY)

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `Should emit triangle that get from ShapeRepository state when call generateShape`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val shape = Triangle(
                background = "triangle background"
            )
            Mockito.`when`(typeProvider.getRandomShapeType()).thenReturn(2)
            Mockito.`when`(typeProvider.getRandomBackgroundType()).thenReturn(1)
            Mockito.`when`(shapeRepository.generateTriangle(1)).thenReturn(shape)

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
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            val width = 100
            val height = 200
            viewModel.setScreenSize(width, height)
            val shape = Square(
                background = "test background"
            )
            Mockito.`when`(typeProvider.getRandomShapeType()).thenReturn(0)
            Mockito.`when`(shapeRepository.generateSquare()).thenReturn(shape)

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
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateShapeActionResultObserver)
            Mockito.`when`(typeProvider.getRandomShapeType()).thenReturn(0)
            Mockito.`when`(shapeRepository.generateSquare()).thenThrow(RuntimeException())

            viewModel.generateShape(100F, 200F)

            Mockito.verify(generateShapeActionResultObserver)
                .onChanged(ActionResult.error(R.string.common_error))

            viewModel.generateShapeActionResult.removeObserver(generateShapeActionResultObserver)
        }

    @Test
    fun `Should emit loading state when call changeShapeBackground`() {
        val shapeRepository = Mockito.mock(ShapeRepository::class.java)
        val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
        val viewModel = AllViewModel(shapeRepository, typeProvider)
        viewModel.generateBackgroundActionResult.observeForever(
            generateBackgroundActionResultObserver
        )
        val shape = Square()

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
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateBackgroundActionResult.observeForever(
                generateBackgroundActionResultObserver
            )
            val shape = Triangle(
                background = "current background"
            )
            Mockito.`when`(typeProvider.getRandomBackgroundType()).thenReturn(1)
            Mockito.`when`(shapeRepository.generateBackground(1)).thenReturn("test background")

            viewModel.changeShapeBackground(shape)

            Mockito.verify(generateBackgroundActionResultObserver)
                .onChanged(ActionResult.success("test background"))

            viewModel.generateBackgroundActionResult.removeObserver(
                generateBackgroundActionResultObserver
            )
        }

    @Test
    fun `Should emit error when call changeShapeBackground and get exception`() =
        runBlockingTest {
            val shapeRepository = Mockito.mock(ShapeRepository::class.java)
            val typeProvider = Mockito.mock(RandomTypeProvider::class.java)
            val viewModel = AllViewModel(shapeRepository, typeProvider)
            viewModel.generateShapeActionResult.observeForever(generateBackgroundActionResultObserver)
            Mockito.`when`(typeProvider.getRandomBackgroundType()).thenReturn(1)
            Mockito.`when`(shapeRepository.generateBackground(1)).thenThrow(RuntimeException())
            val shape = Triangle()

            viewModel.changeShapeBackground(shape)

            Mockito.verify(generateBackgroundActionResultObserver)
                .onChanged(ActionResult.error(R.string.common_error))

            viewModel.generateShapeActionResult.removeObserver(generateBackgroundActionResultObserver)
        }
}