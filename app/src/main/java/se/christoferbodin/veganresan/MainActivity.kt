package se.christoferbodin.veganresan

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.christoferbodin.veganresan.model.AppInfo

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
                    showUpdateDialog(info)
                }
            } catch (exception: Exception) {
                // No required data, retry next time app is opened
            }
        }
    }

    private fun showUpdateDialog(appInfo: AppInfo) {
        val updates = ArrayList(appInfo.news)
        updates.add(0, getString(R.string.dialog_update_text, BuildConfig.VERSION_NAME, appInfo.latestVersion))

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        adapter.addAll(updates)

        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_update)
            .setTitle(R.string.dialog_update_title)
            .setAdapter(adapter, null)
            .setPositiveButton(R.string.dialog_update_update) { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appInfo.downloadUrl))
                startActivity(intent)
            }
            .setNegativeButton(R.string.dialog_update_cancel, null)
            .create()
            .show()
    }
}
