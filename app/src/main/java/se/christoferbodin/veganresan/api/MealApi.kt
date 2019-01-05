package se.christoferbodin.veganresan.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import se.christoferbodin.veganresan.model.Meal

interface MealApi {
    @GET("/meal/list")
    fun mealList(): Deferred<List<Meal>>
}
