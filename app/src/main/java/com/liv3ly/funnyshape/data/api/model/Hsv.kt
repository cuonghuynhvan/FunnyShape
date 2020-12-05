package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Hsv(
    @Json(name = "hue")
    val hue: Int,
    @Json(name = "saturation")
    val saturation: Int,
    @Json(name = "value")
    val value: Int
)