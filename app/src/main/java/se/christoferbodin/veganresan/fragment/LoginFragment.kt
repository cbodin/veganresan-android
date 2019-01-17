package se.christoferbodin.veganresan.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.dialog_login.btn_login
import kotlinx.android.synthetic.main.dialog_login.input_password
import kotlinx.android.synthetic.main.dialog_login.progress_circular
import se.christoferbodin.veganresan.R
import se.christoferbodin.veganresan.viewmodel.LoginViewModel

class LoginFragment : DialogFragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var passwordInput: EditText
    private lateinit var progress: ProgressBar
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.isLoading().observe(this, Observer { loading ->
            progress.visibility = if (loading) View.VISIBLE else View.GONE
            loginBtn.isEnabled = !loading
            passwordInput.isEnabled = !loading
        })
        viewModel.onLogin().observe(this, Observer { success ->
            val text = if (success) R.string.toast_login_successful else R.string.toast_login_error
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

            if (success) {
                dismiss()
            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!)
            .setIcon(R.drawable.ic_password)
            .setTitle(R.string.dialog_login_title)
            .setView(R.layout.dialog_login)
            .create()

    override fun onStart() {
        super.onStart()

        passwordInput = dialog.input_password
        progress = dialog.progress_circular

        loginBtn = dialog.btn_login
        loginBtn.setOnClickListener {
            viewModel.login(passwordInput.text.toString())
        }
    }

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }
}
