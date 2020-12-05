package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Template(
    @Json(name = "author")
    val author: Author,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String
)