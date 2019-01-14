package se.christoferbodin.veganresan

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.fragment.AppUpdateFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.appbar))

        if (savedInstanceState == null) {
            checkForUpdates()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

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
