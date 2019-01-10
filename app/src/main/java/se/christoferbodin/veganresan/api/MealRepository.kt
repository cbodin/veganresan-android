package se.christoferbodin.veganresan.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.model.Meal
import se.christoferbodin.veganresan.model.MealType
import java.text.SimpleDateFormat
import java.util.Date

class MealRepository(private val mealApi: MealApi, private val oldMealApi: OldMealApi) {
    // fun mealList(): LiveData<Resource<List<Meal>>> = apiCall(mealApi.mealList())
    fun mealList(): LiveData<Resource<List<Meal>>> {
        val data = MutableLiveData<Resource<List<Meal>>>()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val meals = oldMealApi.meals().await()
                val newMeals = ArrayList<Meal>()

                for ((index, meal) in meals.withIndex()) {
                    val date: Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(meal.date)
                    val newMeal = Meal(index, meal.title, MealType.HOMEMADE, 1, meal.image, date, ArrayList())
                    newMeals.add(newMeal)
                }

                data.value = Resource.success(newMeals)
            } catch (exception: Exception) {
                println(exception)
                data.value = Resource.error<List<Meal>>("Unexpected error", null)
            }
        }

        return data
    }

    private fun <T> apiCall(call: Deferred<T>): LiveData<Resource<T>> {
        val data = MutableLiveData<Resource<T>>()
        data.value = Resource.loading(null)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                data.value = Resource.success(call.await())
            } catch (exception: Exception) {
                data.value = Resource.error<T>("Unexpected error", null)
            }
        }

        return data
    }
}
