package com.example.linkupaffinity.screens.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.linkupaffinity.R
import com.example.linkupaffinity.screens.login.LoginActivity


class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var presenter: RegisterPresenter

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etCourse: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnCreateAccount: Button
    private lateinit var textviewSignIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        presenter = RegisterPresenter(this)

        // Bind cleanly to our custom XML layout views
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etCourse = findViewById(R.id.etCourse)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        cbTerms = findViewById(R.id.cbTerms)
        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        textviewSignIn = findViewById(R.id.textviewSignIn)

        // Text listener events connecting cleanly back to our Presenter architecture contract
        etFirstName.doOnTextChanged { text, _, _, _ -> presenter.onFirstNameChanged(text.toString()) }
        etLastName.doOnTextChanged { text, _, _, _ -> presenter.onLastNameChanged(text.toString()) }
        etEmail.doOnTextChanged { text, _, _, _ -> presenter.onEmailChanged(text.toString()) }
        etPassword.doOnTextChanged { text, _, _, _ -> presenter.onPasswordChanged(text.toString()) }
        etConfirmPassword.doOnTextChanged { text, _, _, _ -> presenter.onConfirmPasswordChanged(text.toString()) }

        // Setup drop-down dialog choice when Course Field is selected
        etCourse.setOnClickListener {
            // Inform presenter that user interacted with the dropdown state
            presenter.onCourseDropdownToggled(expanded = true)

            val courses = arrayOf("Computer Science", "Information Technology", "BS Nursing", "BS Pharmacy")
            AlertDialog.Builder(this)
                .setTitle("Select Course")
                .setItems(courses) { _, which ->
                    val selected = courses[which]
                    etCourse.setText(selected)
                    presenter.onCourseSelected(selected)
                }
                .setOnDismissListener {
                    presenter.onCourseDropdownToggled(expanded = false)
                }
                .show()
        }

        cbTerms.setOnCheckedChangeListener { _, isChecked ->
            presenter.onTermsToggled(isChecked)
        }

        btnCreateAccount.setOnClickListener {
            presenter.onCreateAccountClicked()
        }

        textviewSignIn.setOnClickListener {
            presenter.navigateBackToLogin()
        }

        // 3. Attach this activity instance as the View contract layer
        presenter.attachView(this)
    }

    // --- 4. Implement RegisterContract.View Interface Methods directly at class level ---

    override fun updateViewState(
        firstName: String, isFirstNameValid: Boolean,
        lastName: String, isLastNameValid: Boolean,
        email: String, isEmailValid: Boolean,
        selectedCourse: String, isCourseDropdownExpanded: Boolean,
        password: String, isPasswordVisible: Boolean,
        confirmPassword: String, isConfirmPasswordVisible: Boolean,
        isTermsAccepted: Boolean,
        isCreateEnabled: Boolean
    ) {
        btnCreateAccount.isEnabled = isCreateEnabled
        btnCreateAccount.alpha = if (isCreateEnabled) 1.0f else 0.5f
        // Optional: Provide visual error styling if text fields are invalid/empty after user types
        etFirstName.error = if (!isFirstNameValid && firstName.isNotEmpty()) "Invalid First Name" else null
        etLastName.error = if (!isLastNameValid && lastName.isNotEmpty()) "Invalid Last Name" else null
        etEmail.error = if (!isEmailValid && email.isNotEmpty()) "Invalid Email Address" else null
    }

    override fun showRegistrationSuccess() {
        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun showRegistrationError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun navigateBackToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    // --- 5. Clean up lifecycle to prevent memory leaks ---
    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}