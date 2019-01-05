package se.christoferbodin.veganresan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import se.christoferbodin.veganresan.api.MealApi
import se.christoferbodin.veganresan.model.Meal
import java.util.Date

class MealViewModel : ViewModel() {
    private val api: MealApi
    private var mealsJob: Deferred<List<Meal>>? = null
    val meals = MutableLiveData<List<Meal>>()

    init {
        // TODO: Inject using Dagger or some other method?
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            // TODO: Move to values xml
            .baseUrl("https://veganresan.christoferbodin.se")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        api = retrofit.create(MealApi::class.java)
    }

    fun loadMeals(refresh: Boolean = false) {
        val isLoading = mealsJob?.isActive ?: false
        val shouldRefresh = refresh || meals.value == null

        if (!isLoading && shouldRefresh) {
            CoroutineScope(Dispatchers.Main).launch {
                mealsJob = api.mealList()
                meals.value = mealsJob?.await()
            }
        }
    }
}
