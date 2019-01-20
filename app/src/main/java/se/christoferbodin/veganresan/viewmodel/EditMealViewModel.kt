package se.christoferbodin.veganresan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import se.christoferbodin.veganresan.VeganApplication
import se.christoferbodin.veganresan.utils.SingleLiveEvent
import se.christoferbodin.veganresan.utils.default
import java.io.File

class EditMealViewModel(application: Application) : AndroidViewModel(application) {
    private val api = getApplication<VeganApplication>().mealApi
    private val loading = MutableLiveData<Boolean>().default(false)
    private val error = MutableLiveData<Boolean>().default(false)
    private val save = SingleLiveEvent<Void>()

    fun isLoading() = loading as LiveData<Boolean>
    fun hasError() = error as LiveData<Boolean>
    fun onSave() = save as LiveData<Void>

    fun uploadMeal(name: String, image: File) {
        val imagePart = RequestBody.create(MediaType.parse("image/jpeg"), image)

        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true
            error.value = false

            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", name)
                .addFormDataPart("image", "image.jpeg", imagePart)
                .addFormDataPart("rating", "1")
                .addFormDataPart("type", "RESTAURANT")
                .build()

            try {
                api.mealCreate(body).await()
                save.call()
            } catch (exception: Exception) {
                error.value = true
            }

            loading.value = false
        }
    }
}
