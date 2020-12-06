package com.liv3ly.funnyshape.repository

import androidx.annotation.WorkerThread
import com.liv3ly.funnyshape.common.Circle
import com.liv3ly.funnyshape.common.Shape
import com.liv3ly.funnyshape.common.Square
import com.liv3ly.funnyshape.data.api.BackgroundAPIService
import java.io.IOException
import kotlin.math.roundToInt

class ShapeRepository(private val backgroundAPIService: BackgroundAPIService) {
    private fun generateRandomSize(screenWidth: Int, screenHeight: Int): Int {
        //        Create a shape at a random size within appropriate ranges.
        //        A shape should not be more than 45% the width or height of the screen size
        //        and should never be less than 10% the width or height.
        val maxSize = Math.min(screenHeight, screenWidth) * 0.45
        val minSize = Math.max(screenHeight, screenWidth) * 0.1

        return (minSize + Math.random() * (maxSize - minSize)).roundToInt()
    }

    private fun generateRandomColor(): Int {
        val r = (Math.random() * 255).roundToInt()
        val g = (Math.random() * 255).roundToInt()
        val b = (Math.random() * 255).roundToInt()
        return convertColorFromRGB(r, g, b)
    }

    private fun convertColorFromRGB(red: Int, green: Int, blue: Int) =
        (255 and 0xff shl 24) or (red and 0xff shl 16) or (green and 0xff shl 8) or (blue and 0xff)

    @WorkerThread
    suspend fun generateColor(): Int {
        try {
            val colorList = backgroundAPIService.getRandomColors()

            if (colorList.isNotEmpty()) {
                val rgb = colorList[0].rgb
                return convertColorFromRGB(rgb.red, rgb.green, rgb.blue)
            }
        } catch (e: IOException) {
        }

        return generateRandomColor()
    }

    @WorkerThread
    suspend fun generateImage(): Any {
        try {
            val patternList = backgroundAPIService.getRandomPatterns()

            if (patternList.isNotEmpty()) {
                return patternList[0].imageUrl
            }
        } catch (e: IOException) {
        }

        return generateRandomColor()
    }

    @WorkerThread
    suspend fun generateSquare(screenWidth: Int, screenHeight: Int): Shape {
        val square = Square(
            size = generateRandomSize(screenWidth, screenHeight),
        )
        square.background = generateImage()
        return square
    }

    @WorkerThread
    suspend fun generateCircle(screenWidth: Int, screenHeight: Int): Shape {
        val circle = Circle(
            size = generateRandomSize(screenWidth, screenHeight),
        )
        circle.background = generateColor()
        return circle
    }
}