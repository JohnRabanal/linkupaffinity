package com.example.linkupaffinity.screens.register

import android.content.Context

class RegisterPresenter(
    private val context: Context
) : RegisterContract.Presenter {

    private var view: RegisterContract.View? = null

    // Form State Variables
    private var firstName = ""
    private var lastName = ""
    private var email = ""
    private var selectedCourse = ""
    private var password = ""
    private var confirmPassword = ""

    private var isCourseDropdownExpanded = false
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false
    private var isTermsAccepted = false

    override fun attachView(view: RegisterContract.View) {
        this.view = view
        updateView()
    }

    override fun detachView() {
        this.view = null
    }

    override fun onCourseDropdownToggled(expanded: Boolean) {
        this.isCourseDropdownExpanded = expanded
        updateView()
    }

    override fun onTogglePasswordVisibility() {
        this.isPasswordVisible = !this.isPasswordVisible
        updateView()
    }

    override fun onToggleConfirmPasswordVisibility() {
        this.isConfirmPasswordVisible = !this.isConfirmPasswordVisible
        updateView()
    }

    override fun onFirstNameChanged(value: String) {
        this.firstName = value
        updateView()
    }

    override fun onLastNameChanged(value: String) {
        this.lastName = value
        updateView()
    }

    override fun onCourseSelected(value: String) {
        this.selectedCourse = value
        this.isCourseDropdownExpanded = false
        updateView()
    }

    override fun onEmailChanged(value: String) {
        this.email = value
        updateView()
    }

    override fun onPasswordChanged(value: String) {
        this.password = value
        updateView()
    }

    override fun onConfirmPasswordChanged(value: String) {
        this.confirmPassword = value
        updateView()
    }

    override fun onTermsToggled(checked: Boolean) {
        this.isTermsAccepted = checked
        updateView()
    }


    override fun onCreateAccountClicked() {
        if (firstName.isBlank() || lastName.isBlank()) {
            view?.showRegistrationError("Please enter your full name.")
            return
        }

        if (!email.contains("@") || email.isBlank()) {
            view?.showRegistrationError("Please enter a valid email address.")
            return
        }

        // Match your UI requirements: 8+ chars, 1 number, 1 symbol
        val hasDigit = password.any { it.isDigit() }
        val hasSpecial = password.any { !it.isLetterOrDigit() }
        if (password.length < 8 || !hasDigit || !hasSpecial) {
            view?.showRegistrationError("Password must be 8+ characters and contain at least 1 number and 1 symbol.")
            return
        }

        if (password != confirmPassword) {
            view?.showRegistrationError("Passwords do not match!")
            return
        }

        if (!isTermsAccepted) {
            view?.showRegistrationError("You must accept the Terms and Conditions.")
            return
        }

        // --- Save user locally safely using shared preferences context ---
        val sharedPrefs = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putString("REGISTERED_EMAIL", email)
            putString("REGISTERED_PASSWORD", password)
            putString("USER_FIRSTNAME", firstName)
            apply()
        }

        // 🔥 Trigger the Success callback routing directly to the Login Screen!
        view?.showRegistrationSuccess()
    }

    // 🔥 FIX 2: Correct navigation routing action back to sign-in view
    override fun navigateBackToLogin() {
        view?.navigateBackToLogin()
    }

    private fun updateView() {
        val isFirstNameValid = firstName.isNotBlank()
        val isLastNameValid = lastName.isNotBlank()
        val isEmailValid = email.contains("@") && email.isNotBlank()

        // Matching rules on validation flags
        val isPasswordValid = password.length >= 8 && password.any { it.isDigit() } && password.any { !it.isLetterOrDigit() }
        val isConfirmPasswordValid = confirmPassword == password && confirmPassword.isNotBlank()

        // Form layout validation state logic rules
        val isCreateEnabled = isFirstNameValid && isLastNameValid && isEmailValid &&
                isPasswordValid && isConfirmPasswordValid && isTermsAccepted

        view?.updateViewState(
            firstName = firstName, isFirstNameValid = isFirstNameValid,
            lastName = lastName, isLastNameValid = isLastNameValid,
            email = email, isEmailValid = isEmailValid,
            selectedCourse = selectedCourse, isCourseDropdownExpanded = isCourseDropdownExpanded,
            password = password, isPasswordVisible = isPasswordVisible,
            confirmPassword = confirmPassword, isConfirmPasswordVisible = isConfirmPasswordVisible,
            isTermsAccepted = isTermsAccepted,
            isCreateEnabled = isCreateEnabled
        )
    }
}