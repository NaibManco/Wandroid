package com.naib.wandroid.user

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.user.data.UserViewModel
import kotlinx.coroutines.*

class LoginActivity : BaseActivity(), TextWatcher {

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
        }
    }

    private var model = viewModels<UserViewModel>()

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var errorText: TextView

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_login, container)

        userName = findViewById(R.id.login_account_edit)
        userName.addTextChangedListener(this)
        password = findViewById(R.id.login_password_edit)
        password.addTextChangedListener(this)
        loginButton = findViewById(R.id.login_btn)
        registerButton = findViewById(R.id.login_register_btn)
        errorText = findViewById(R.id.login_error)
    }

    fun onLogin(view: View) {
        if (!check()) {
            errorText.setText(R.string.username_or_password_incorrect_toast)
            return
        }

        mainScope.launch {
            val response =
                model.value.doLogin(userName.text.toString(), password.text.toString())
            response?.apply {
                if (data == null) {
                    errorText.text = errorMsg
                } else {
                    finish()
                }
            } ?: run {
                errorText.setText(R.string.network_error)
            }
        }
    }

    fun onRegister(view: View) {
        if (!check()) {
            errorText.setText(R.string.username_or_password_incorrect_toast)
            return
        }

        mainScope.launch {
            val response =
                model.value.doRegister(userName.text.toString(), password.text.toString())
            response?.apply {
                if (data == null) {
                    errorText.text = errorMsg
                } else {
                    finish()
                }
            } ?: run {
                errorText.setText(R.string.network_error)
            }
        }
    }

    private fun check(): Boolean {
        if (TextUtils.isEmpty(userName.text) || TextUtils.isEmpty(password.text)) {
            return false
        }
        return true
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        errorText.text = ""
    }
}