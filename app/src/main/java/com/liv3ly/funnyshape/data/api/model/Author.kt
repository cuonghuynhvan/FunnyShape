package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Author(
    @Json(name = "url")
    val url: String,
    @Json(name = "userName")
    val userName: String
)