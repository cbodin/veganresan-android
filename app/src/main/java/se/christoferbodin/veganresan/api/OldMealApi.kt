package se.christoferbodin.veganresan.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface OldMealApi {
    @GET("/meals.json")
    @Headers("Authorization: Basic Y2xpZW50Om1vcmVjbGllbnQ=")
    fun meals(): Deferred<List<OldMeal>>
}

data class OldMeal(
    val title: String,
    val date: String,
    val image: String
)
