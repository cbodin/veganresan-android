package se.christoferbodin.veganresan.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Link(val title: String, val url: String)
