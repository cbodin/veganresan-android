package se.christoferbodin.veganresan.api

import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import se.christoferbodin.veganresan.model.AppInfo
import se.christoferbodin.veganresan.model.LoginValidate
import se.christoferbodin.veganresan.model.Meal

interface MealApi {
    @GET("/meal/list")
    fun mealList(): Deferred<List<Meal>>

    @POST("/meal/create")
    fun mealCreate(@Body body: MultipartBody): Deferred<Meal>

    @GET("/app-info/android-{version}")
    fun appInfo(@Path("version") version: String):Deferred<AppInfo>

    @POST("/login/validate")
    fun loginValidate(@Header("Authorization") bearer: String):Deferred<LoginValidate>
}
