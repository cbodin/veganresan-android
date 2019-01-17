package se.christoferbodin.veganresan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.VeganApplication
import se.christoferbodin.veganresan.default
import se.christoferbodin.veganresan.model.Meal

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val api = getApplication<VeganApplication>().mealApi
    private val loading = MutableLiveData<Boolean>().default(false)
    private val error = MutableLiveData<Boolean>().default(false)
    private val meals = MutableLiveData<List<Meal>>()

    fun isLoading() = loading as LiveData<Boolean>
    fun hasError() = error as LiveData<Boolean>
    fun getMeals() = meals as LiveData<List<Meal>>

    fun loadMeals(refresh: Boolean = false) {
        if (loading.value == true || !refresh && meals.value != null) {
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true
            error.value = false

            try {
                meals.value = api.mealList().await()
            } catch (exception: Exception) {
                error.value = true
            }

            loading.value = false
        }
    }
}
