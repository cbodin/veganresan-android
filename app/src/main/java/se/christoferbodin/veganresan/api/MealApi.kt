package se.christoferbodin.veganresan.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import se.christoferbodin.veganresan.model.AppInfo
import se.christoferbodin.veganresan.model.Meal

interface MealApi {
    @GET("/meal/list")
    fun mealList(): Deferred<List<Meal>>

    @GET("/app-info/android-{version}")
    fun appInfo(@Path("version") version: String):Deferred<AppInfo>
}
