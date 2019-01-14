package se.christoferbodin.veganresan.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import se.christoferbodin.veganresan.BuildConfig
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.model.AppInfo

class AppUpdateFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val updates = ArrayList(appInfo.news)
        updates.add(0, getString(R.string.dialog_update_text, BuildConfig.VERSION_NAME, appInfo.latestVersion))

        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1)
        adapter.addAll(updates)

        return AlertDialog.Builder(context!!)
            .setIcon(R.drawable.ic_update)
            .setTitle(R.string.dialog_update_title)
            .setAdapter(adapter, null)
            .setPositiveButton(R.string.dialog_update_update) { _, _ ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appInfo.downloadUrl))
                startActivity(intent)
            }
            .setNegativeButton(R.string.dialog_update_cancel, null)
            .create()
    }

    private val appInfo: AppInfo by lazy {
        arguments!!.getSerializable(ARG_APP_INFO) as AppInfo
    }

    companion object {
        private const val ARG_APP_INFO = "APP_INFO"

        fun newInstance(appInfo: AppInfo): AppUpdateFragment {
            val args = Bundle()
            args.putSerializable(ARG_APP_INFO, appInfo)

            val fragment = AppUpdateFragment()
            fragment.arguments = args

            return fragment
        }
    }
}
