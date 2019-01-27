package se.christoferbodin.veganresan.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Link(val title: String, val url: String) : Parcelable
