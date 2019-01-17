package se.christoferbodin.veganresan.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.VeganApplication
import se.christoferbodin.veganresan.utils.SingleLiveEvent
import se.christoferbodin.veganresan.utils.default

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val api = getApplication<VeganApplication>().mealApi
    private val loading = MutableLiveData<Boolean>().default(false)
    private val loggedIn = SingleLiveEvent<Boolean>()

    fun isLoading() = loading as LiveData<Boolean>
    fun onLogin() = loggedIn as LiveData<Boolean>

    fun login(password: String) {
        if (loading.value == true) {
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            loading.value = true

            try {
                api.loginValidate("Bearer $password").await()
                loggedIn.value = true
            } catch (exception: Exception) {
                loggedIn.value = false
            }

            loading.value = false
        }
    }
}
