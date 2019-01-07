package se.christoferbodin.veganresan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.VeganApplication
import se.christoferbodin.veganresan.api.MealApi
import se.christoferbodin.veganresan.model.Meal

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val api: MealApi = getApplication<VeganApplication>().mealApi
    private val meals = MutableLiveData<List<Meal>>()
    private val mealsError = MutableLiveData<Boolean>().apply { value = false }
    private val mealsLoading = MutableLiveData<Boolean>().apply { value = false }

    fun meals(refresh: Boolean = false): LiveData<List<Meal>> {
        if (mealsLoading.value == true) {
            return meals
        }

        // Only fetch if forcing a refresh or if we have no data
        if (refresh || meals.value == null) {
            mealsLoading.value = true
            mealsError.value = false

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    meals.value = api.mealList().await()
                } catch (error: Exception) {
                    mealsError.value = true
                }

                mealsLoading.value = false
            }
        }

        // Return immutable LiveData
        return meals
    }

    fun mealsError(): LiveData<Boolean> {
        return mealsError
    }

    fun mealsLoading(): LiveData<Boolean> {
        return mealsLoading
    }
}
