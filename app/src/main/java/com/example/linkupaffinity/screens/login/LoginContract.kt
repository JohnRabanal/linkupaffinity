package com.example.linkupaffinity.screens.login

/**
 * Standard MVP Architectural Contract for the Login Flow Feature.
 */
interface LoginContract {

    interface View {
        val viewContext: android.content.Context
        fun updateViewState(
            identifier: String,
            password: String,
            isPasswordVisible: Boolean,
            rememberMe: Boolean,
            isSignInEnabled: Boolean
        )
        fun showGenericLoginError()
        fun navigateToDashboard()
        fun navigateToSignUp()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onIdentifierInputChanged(value: String)
        fun onPasswordInputChanged(value: String)
        fun onTogglePasswordVisibility()
        fun onRememberMeToggled(checked: Boolean)
        fun onSignInClicked()
        fun onSignUpClicked() // Enforces a standardized name across all files
    }
}