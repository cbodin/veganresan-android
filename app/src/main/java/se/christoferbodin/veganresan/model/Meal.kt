package se.christoferbodin.veganresan.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
@JsonClass(generateAdapter = true)
data class Meal(
    val id: Int,
    val name: String,
    val type: MealType,
    val rating: Int,
    val photo: String,
    val published: Date,
    val links: List<Link>
) : Parcelable
