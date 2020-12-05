package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Rgb(
    @Json(name = "blue")
    val blue: Int,
    @Json(name = "green")
    val green: Int,
    @Json(name = "red")
    val red: Int
)