package se.christoferbodin.veganresan

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.christoferbodin.veganresan.api.MealApi
import se.christoferbodin.veganresan.api.MealRepository
import se.christoferbodin.veganresan.api.OldMealApi
import java.util.Date

class VeganApplication: Application() {
    val mealApi: MealApi by lazy {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val baseUrl = getString(R.string.api_base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofit.create(MealApi::class.java)
    }

    val oldMealApi: OldMealApi by lazy {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val baseUrl = getString(R.string.old_api_base_url)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        retrofit.create(OldMealApi::class.java)
    }

    val mealRepository by lazy {
        MealRepository(mealApi, oldMealApi)
    }
}
