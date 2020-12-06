package com.liv3ly.funnyshape.repository

import androidx.annotation.WorkerThread
import com.liv3ly.funnyshape.common.*
import com.liv3ly.funnyshape.data.api.BackgroundAPIService
import java.io.IOException
import kotlin.math.roundToInt

class ShapeRepository(private val backgroundAPIService: BackgroundAPIService) {
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
    suspend fun generateBackground(backgroundType: Int): Any =
        when (backgroundType) {
            Constant.BACKGROUND_TYPE_COLOR -> generateColor()
            Constant.BACKGROUND_TYPE_IMAGE -> generateImage()
            else -> generateColor()
        }

    @WorkerThread
    suspend fun generateSquare(): Shape {
        val square = Square()
        square.background = generateImage()
        return square
    }

    @WorkerThread
    suspend fun generateCircle(): Shape {
        val shape = Circle()
        shape.background = generateColor()
        return shape
    }

    @WorkerThread
    suspend fun generateTriangle(backgroundType: Int): Shape {
        val shape = Triangle()
        shape.background = generateBackground(backgroundType)
        return shape
    }
}