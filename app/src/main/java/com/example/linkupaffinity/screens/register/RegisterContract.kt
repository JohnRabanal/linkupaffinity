package com.example.linkupaffinity.screens.register

interface RegisterContract {

    interface View {
        fun updateViewState(
            firstName: String, isFirstNameValid: Boolean,
            lastName: String, isLastNameValid: Boolean,
            email: String, isEmailValid: Boolean,
            selectedCourse: String, isCourseDropdownExpanded: Boolean,
            password: String, isPasswordVisible: Boolean,
            confirmPassword: String, isConfirmPasswordVisible: Boolean,
            isTermsAccepted: Boolean,
            isCreateEnabled: Boolean
        )
        fun showRegistrationSuccess()
        fun showRegistrationError(message: String)
        fun navigateBackToLogin()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onFirstNameChanged(value: String)
        fun onLastNameChanged(value: String)
        fun onCourseSelected(value: String)
        fun onEmailChanged(value: String)
        fun onPasswordChanged(value: String)
        fun onConfirmPasswordChanged(value: String)
        fun onTermsToggled(checked: Boolean)
        fun onCreateAccountClicked()
        fun navigateBackToLogin()


        fun onCourseDropdownToggled(expanded: Boolean)
        fun onTogglePasswordVisibility()
        fun onToggleConfirmPasswordVisibility()
    }
}