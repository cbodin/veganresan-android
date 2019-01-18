package se.christoferbodin.veganresan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.appbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.fragment.AppUpdateFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(appbar)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        appbar.setupWithNavController(navController, appBarConfiguration)

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
