package se.christoferbodin.veganresan.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppInfo(
    @Json(name = "latest_version")
    val latestVersion: String,
    @Json(name = "is_latest")
    val isLatest: Boolean,
    @Json(name = "download_url")
    val downloadUrl: String,
    val news: List<String>
)
