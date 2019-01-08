package se.christoferbodin.veganresan.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.model.Meal

class MealRepository(private val mealApi: MealApi) {
    fun mealList(): LiveData<Resource<List<Meal>>> = apiCall(mealApi.mealList())

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
