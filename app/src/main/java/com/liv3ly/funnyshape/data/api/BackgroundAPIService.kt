package com.liv3ly.funnyshape.data.api

import com.liv3ly.funnyshape.data.api.model.Color
import com.liv3ly.funnyshape.data.api.model.Pattern
import retrofit2.http.GET

interface BackgroundAPIService {
    @GET("colors/random?format=json")
    suspend fun getRandomColors(): List<Color>

    @GET("patterns/random?format=json")
    suspend fun getRandomPatterns(): List<Pattern>
}