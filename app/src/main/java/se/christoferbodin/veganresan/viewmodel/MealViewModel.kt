package se.christoferbodin.veganresan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import se.christoferbodin.veganresan.VeganApplication
import se.christoferbodin.veganresan.api.Resource
import se.christoferbodin.veganresan.model.Meal

class MealViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = getApplication<VeganApplication>().mealRepository
    private var mealsData: LiveData<Resource<List<Meal>>>? = null

    fun loadMeals(refresh: Boolean = false): LiveData<Resource<List<Meal>>> {
        if (refresh || mealsData == null) {
            mealsData = repo.mealList()
        }

        return mealsData as LiveData<Resource<List<Meal>>>
    }
}
