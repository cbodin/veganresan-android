package se.christoferbodin.veganresan

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.fragment.AppUpdateFragment
import se.christoferbodin.veganresan.fragment.DialogLoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appbar))

        if (savedInstanceState == null) {
            checkForUpdates()
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()

    private fun checkForUpdates() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val info = (application as VeganApplication).mealApi
                    .appInfo(BuildConfig.VERSION_NAME)
                    .await()

                if (!info.isLatest) {
                    AppUpdateFragment.newInstance(info)
                        .show(supportFragmentManager, null)
                }
            } catch (exception: Exception) {
                // No required data, retry next time app is opened
            }
        }
    }
}
