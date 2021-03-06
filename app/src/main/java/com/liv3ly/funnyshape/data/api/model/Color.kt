package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Color(
    @Json(name = "apiUrl")
    val apiUrl: String = "",
    @Json(name = "badgeUrl")
    val badgeUrl: String = "",
    @Json(name = "dateCreated")
    val dateCreated: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "hex")
    val hex: String = "",
    @Json(name = "hsv")
    val hsv: Hsv? = null,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "imageUrl")
    val imageUrl: String = "",
    @Json(name = "numComments")
    val numComments: Int = 0,
    @Json(name = "numHearts")
    val numHearts: Int = 0,
    @Json(name = "numViews")
    val numViews: Int = 0,
    @Json(name = "numVotes")
    val numVotes: Int = 0,
    @Json(name = "rank")
    val rank: Int = 0,
    @Json(name = "rgb")
    val rgb: Rgb,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "url")
    val url: String = "",
    @Json(name = "userName")
    val userName: String = ""
)