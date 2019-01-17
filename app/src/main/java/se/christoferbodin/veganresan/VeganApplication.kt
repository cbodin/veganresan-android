package se.christoferbodin.veganresan

import android.app.Application
import android.preference.PreferenceManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.christoferbodin.veganresan.api.MealApi
import java.util.Date

class VeganApplication : Application() {
    val mealApi: MealApi by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()

                getUploadPassword()?.apply {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $this")
                        .build()
                }

                chain.proceed(request)
            }
            .build()

        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val baseUrl = getString(R.string.api_base_url)
        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofit.create(MealApi::class.java)
    }

    private fun getUploadPassword(): String? = PreferenceManager.getDefaultSharedPreferences(this)
        .getString("UPLOAD_PASSWORD", null)
}
