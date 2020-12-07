package com.liv3ly.funnyshape.repository

import com.liv3ly.funnyshape.common.Circle
import com.liv3ly.funnyshape.common.Square
import com.liv3ly.funnyshape.common.Triangle
import com.liv3ly.funnyshape.data.api.BackgroundAPIService
import com.liv3ly.funnyshape.data.api.model.Color
import com.liv3ly.funnyshape.data.api.model.Pattern
import com.liv3ly.funnyshape.data.api.model.Rgb
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ShapeRepositoryUnitTest {
    @Test
    fun `should return color from API service when call generateColor`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val colorList = listOf(
            Color(
                rgb = Rgb(
                    red = 50, // 0x32
                    green = 100, // 0x64
                    blue = 150 // 0x96
                )
            )
        )
        Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
        val shapeRepository = ShapeRepository(apiService)

        val color = shapeRepository.generateColor()

        assertEquals((0xFF326496).toInt(), color)
    }

    @Test
    fun `should return a random color if API service return empty list when call generateColor`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val colorList = emptyList<Color>()
            Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
            val shapeRepository = ShapeRepository(apiService)

            val color = shapeRepository.generateColor()

            assertFalse(color == 0)
        }

    @Test
    fun `should return image url from API service when call generateImage`() = runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val patternList = listOf(
                Pattern(
                    imageUrl = "test image url"
                )
            )
            Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
            val shapeRepository = ShapeRepository(apiService)

            val imageURl = shapeRepository.generateImage()

            assertEquals( "test image url", imageURl)
        }

    @Test
    fun `should return a random color if API service return empty list when call generateImage`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val patternList = emptyList<Pattern>()
            Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
            val shapeRepository = ShapeRepository(apiService)

            val result = shapeRepository.generateImage()

            assertTrue(result is Int)
            assertFalse(result == 0)
        }

    @Test
    fun `should return color from API service when call generateBackground with backgroundType = 0`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val colorList = listOf(
            Color(
                rgb = Rgb(
                    red = 50, // 0x32
                    green = 100, // 0x64
                    blue = 150 // 0x96
                )
            )
        )
        Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
        val shapeRepository = ShapeRepository(apiService)

        val color = shapeRepository.generateBackground(0)

        assertEquals((0xFF326496).toInt(), color)
    }

    @Test
    fun `should return color from API service when call generateBackground with backgroundType greater than 1`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val colorList = listOf(
            Color(
                rgb = Rgb(
                    red = 50, // 0x32
                    green = 100, // 0x64
                    blue = 150 // 0x96
                )
            )
        )
        Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
        val shapeRepository = ShapeRepository(apiService)

        val color = shapeRepository.generateBackground(2)

        assertEquals((0xFF326496).toInt(), color)
    }

    @Test
    fun `should return a random color if API service return empty list when call generateBackground with backgroundType = 0`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val colorList = emptyList<Color>()
            Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
            val shapeRepository = ShapeRepository(apiService)

            val color = shapeRepository.generateBackground(0)

            assertFalse(color == 0)
        }

    @Test
    fun `should return image url from API service when call generateBackground with backgroundType = 1`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val patternList = listOf(
            Pattern(
                imageUrl = "test image url"
            )
        )
        Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
        val shapeRepository = ShapeRepository(apiService)

        val imageURl = shapeRepository.generateBackground(1)

        assertEquals( "test image url", imageURl)
    }

    @Test
    fun `should return a random color if API service return empty list when call generateBackground with backgroundType = 1`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val patternList = emptyList<Pattern>()
            Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
            val shapeRepository = ShapeRepository(apiService)

            val result = shapeRepository.generateBackground(1)

            assertTrue(result is Int)
            assertFalse(result == 0)
        }

    @Test
    fun `should return square with image url from API service when call generateSquare`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val patternList = listOf(
            Pattern(
                imageUrl = "test image url"
            )
        )
        Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
        val shapeRepository = ShapeRepository(apiService)

        val shape = shapeRepository.generateSquare()

        assertTrue(shape is Square)
        assertEquals( "test image url", shape.background)
    }

    @Test
    fun `should return square with random color if API service return empty list when call generateSquare`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val patternList = emptyList<Pattern>()
            Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
            val shapeRepository = ShapeRepository(apiService)

            val shape = shapeRepository.generateSquare()

            assertTrue(shape is Square)
            assertTrue(shape.background is Int)
            assertFalse(shape.background == 0)
        }

    @Test
    fun `should return circle with color from API service when call generateCircle`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val colorList = listOf(
            Color(
                rgb = Rgb(
                    red = 50, // 0x32
                    green = 100, // 0x64
                    blue = 150 // 0x96
                )
            )
        )
        Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
        val shapeRepository = ShapeRepository(apiService)

        val shape = shapeRepository.generateCircle()

        assertTrue(shape is Circle)
        assertEquals( (0xFF326496).toInt(), shape.background)
    }

    @Test
    fun `should return circle with random color if API service return empty list when call generateCircle`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val colorList = emptyList<Color>()
            Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
            val shapeRepository = ShapeRepository(apiService)

            val shape = shapeRepository.generateCircle()

            assertTrue(shape is Circle)
            assertTrue(shape.background is Int)
            assertFalse(shape.background == 0)
        }







    @Test
    fun `should return triangle with image url from API service when call generateTriangle with backgroundType = 1`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val patternList = listOf(
            Pattern(
                imageUrl = "test image url"
            )
        )
        Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
        val shapeRepository = ShapeRepository(apiService)

        val shape = shapeRepository.generateTriangle(1)

        assertTrue(shape is Triangle)
        assertEquals( "test image url", shape.background)
    }

    @Test
    fun `should return triangle with random color if API service return empty list when call generateTriangle with backgroundType = 1`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val patternList = emptyList<Pattern>()
            Mockito.`when`(apiService.getRandomPatterns()).thenReturn(patternList)
            val shapeRepository = ShapeRepository(apiService)

            val shape = shapeRepository.generateTriangle(1)

            assertTrue(shape is Triangle)
            assertTrue(shape.background is Int)
            assertFalse(shape.background == 0)
        }

    @Test
    fun `should return triangle with color from API service when call generateTriangle with backgroundType = 0`() = runBlockingTest {
        val apiService = Mockito.mock(BackgroundAPIService::class.java)
        val colorList = listOf(
            Color(
                rgb = Rgb(
                    red = 50, // 0x32
                    green = 100, // 0x64
                    blue = 150 // 0x96
                )
            )
        )
        Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
        val shapeRepository = ShapeRepository(apiService)

        val shape = shapeRepository.generateTriangle(0)

        assertTrue(shape is Triangle)
        assertEquals( (0xFF326496).toInt(), shape.background)
    }

    @Test
    fun `should return triangle with random color if API service return empty list when call generateTriangle with backgroundType = 0`() =
        runBlockingTest {
            val apiService = Mockito.mock(BackgroundAPIService::class.java)
            val colorList = emptyList<Color>()
            Mockito.`when`(apiService.getRandomColors()).thenReturn(colorList)
            val shapeRepository = ShapeRepository(apiService)

            val shape = shapeRepository.generateTriangle(0)

            assertTrue(shape is Triangle)
            assertTrue(shape.background is Int)
            assertFalse(shape.background == 0)
        }
}