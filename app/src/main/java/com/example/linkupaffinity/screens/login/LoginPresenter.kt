package com.example.linkupaffinity.screens.login

import android.content.Context

class LoginPresenter : LoginContract.Presenter {

    private var view: LoginContract.View? = null

    private var identifierText = ""
    private var passwordText = ""
    private var isPasswordVisible = false
    private var isRememberMeChecked = false

    override fun attachView(view: LoginContract.View) {
        this.view = view
        pushStateToView()
    }

    override fun detachView() {
        this.view = null
    }

    override fun onIdentifierInputChanged(value: String) {
        if (value.length <= 50) {
            identifierText = value
            pushStateToView()
        } else {
            view?.showGenericLoginError()
        }
    }

    override fun onPasswordInputChanged(value: String) {
        passwordText = value
        pushStateToView()
    }

    override fun onTogglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        pushStateToView()
    }

    override fun onRememberMeToggled(checked: Boolean) {
        isRememberMeChecked = checked
        pushStateToView()
    }

    override fun onSignInClicked() {
        val context = view?.viewContext

        // Hardcoded developer admin fallback
        val isAdmin = identifierText == "admin@linkup.com" && passwordText == "secure_p@ssw0rd!1"

        // Read the correct keys saved by RegisterPresenter
        val isRegisteredUser = context?.let { ctx ->
            val prefs = ctx.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

            val savedEmail = prefs.getString("REGISTERED_EMAIL", null)
            val savedPassword = prefs.getString("REGISTERED_PASSWORD", null)

            // Check if credentials exist and exactly match the user's inputs
            savedEmail != null && savedPassword != null &&
                    identifierText.trim() == savedEmail.trim() && passwordText == savedPassword
        } ?: false

        if (isAdmin || isRegisteredUser) {
            // 🔥 FIXED: Handles auto-login by writing to "UserSession" matching LoginActivity's check
            if (context != null) {
                val sessionPrefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                if (isRememberMeChecked) {
                    sessionPrefs.edit().putBoolean("IS_LOGGED_IN", true).apply()
                } else {
                    // If they unchecked it, make sure previous sessions are cleared out
                    sessionPrefs.edit().putBoolean("IS_LOGGED_IN", false).apply()
                }
            }

            // Direct successfully logged-in user to dashboard
            view?.navigateToDashboard()
        } else {
            view?.showGenericLoginError()
        }
    }

    override fun onSignUpClicked() {
        view?.navigateToSignUp()
    }

    private fun pushStateToView() {
        // Validation rule logic matching form conditions
        val isFormValid = identifierText.isNotBlank() && passwordText.length >= 6

        view?.updateViewState(
            identifier = identifierText,
            password = passwordText,
            isPasswordVisible = isPasswordVisible,
            rememberMe = isRememberMeChecked,
            isSignInEnabled = isFormValid
        )
    }
}