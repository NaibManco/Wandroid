package com.naib.wandroid.user

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import com.naib.wandroid.R
import com.naib.wandroid.base.BaseActivity
import com.naib.wandroid.base.utils.show
import com.naib.wandroid.user.data.LoginViewModel
import kotlinx.coroutines.*

class LoginActivity : BaseActivity() {
    private var model = viewModels<LoginViewModel>()

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreateContentView(container: ViewGroup) {
        layoutInflater.inflate(R.layout.activity_login, container)

        userName = findViewById(R.id.login_account_edit)
        password = findViewById(R.id.login_password_edit)
        loginButton = findViewById(R.id.login_btn)
        registerButton = findViewById(R.id.login_register_btn)
    }

    fun onLogin(view: View) {
        if (!check()) {
            return
        }

        mainScope.launch {
            model.value.doLogin(userName.text.toString(), password.text.toString())
        }
    }

    fun onRegister(view: View) {
        if (!check()) {
            return
        }

        mainScope.launch {
            model.value.doRegister(userName.text.toString(), password.text.toString())
        }
    }

    private fun check(): Boolean {
        if (TextUtils.isEmpty(userName.text) || TextUtils.isEmpty(password.text)) {
            show(this, R.string.username_or_password_incorrect_toast)
            return false
        }
        return true
    }
}