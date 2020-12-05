package com.liv3ly.funnyshape.data.api.model


import com.squareup.moshi.Json

data class Pattern(
    @Json(name = "apiUrl")
    val apiUrl: String,
    @Json(name = "badgeUrl")
    val badgeUrl: String,
    @Json(name = "colors")
    val colors: List<String>,
    @Json(name = "dateCreated")
    val dateCreated: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "numComments")
    val numComments: Int,
    @Json(name = "numHearts")
    val numHearts: Int,
    @Json(name = "numViews")
    val numViews: Int,
    @Json(name = "numVotes")
    val numVotes: Int,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "template")
    val template: Template,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "userName")
    val userName: String
)