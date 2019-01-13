package se.christoferbodin.veganresan.model

import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class Meal(
    val id: Int,
    val name: String,
    val type: MealType,
    val rating: Int,
    val photo: String,
    val published: Date,
    val links: List<Link>
)
