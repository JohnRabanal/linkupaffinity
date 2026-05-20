package com.example.linkupaffinity.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkupaffinity.R
import com.example.linkupaffinity.screens.home.DashboardActivity
import com.example.linkupaffinity.screens.register.RegisterActivity

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginPresenter = LoginPresenter()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFFDFBF7)
            ) {
                LinkupAffinityLoginView(presenter = loginPresenter)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkupAffinityLoginView(presenter: LoginContract.Presenter) {
    val context = LocalContext.current

    var identifierTextState by remember { mutableStateOf("") }
    var passwordTextState by remember { mutableStateOf("") }
    var isPasswordVisibleState by remember { mutableStateOf(false) }
    var rememberMeCheckedState by remember { mutableStateOf(false) }
    var isSignInEnabledState by remember { mutableStateOf(false) }

    DisposableEffect(key1 = presenter) {
        val viewImplementation = object : LoginContract.View {
            override val viewContext: android.content.Context
                get() = context

            override fun updateViewState(
                identifier: String,
                password: String,
                isPasswordVisible: Boolean,
                rememberMe: Boolean,
                isSignInEnabled: Boolean
            ) {
                identifierTextState = identifier
                passwordTextState = password
                isPasswordVisibleState = isPasswordVisible
                rememberMeCheckedState = rememberMe
                isSignInEnabledState = isSignInEnabled
            }

            override fun navigateToDashboard() {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

                // 🔥 FIXED: Direct cleanly to dashboard and strip login activity out of the backstack
                val intent = Intent(context, DashboardActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            }

            override fun navigateToSignUp() {
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)

                // 🔥 FIXED: Finish this instance so "Sign In" link works on the register view without stacking
                (context as? ComponentActivity)?.finish()
            }

            override fun showGenericLoginError() {
                Toast.makeText(context, "Invalid email/username or password.", Toast.LENGTH_SHORT).show()
            }
        }

        presenter.attachView(viewImplementation)

        onDispose {
            presenter.detachView()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.height(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Username", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = identifierTextState,
                onValueChange = { presenter.onIdentifierInputChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                leadingIcon = {
                    Row(
                        modifier = Modifier.padding(start = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(id = R.drawable.email), contentDescription = "Email Icon", modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.size(16.dp).background(Color.Gray, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("i", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
            Text(text = "${identifierTextState.length}/50", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End).padding(top = 4.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Password", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = passwordTextState,
                onValueChange = { presenter.onPasswordInputChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (isPasswordVisibleState) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                leadingIcon = {
                    Icon(painter = painterResource(id = R.drawable.lock), contentDescription = "Lock Icon", modifier = Modifier.size(22.dp))
                },
                trailingIcon = {
                    IconButton(onClick = { presenter.onTogglePasswordVisibility() }) {
                        val iconResource = if (isPasswordVisibleState) {
                            R.drawable.login_openeye
                        } else {
                            R.drawable.login_closeeye
                        }
                        Icon(
                            painter = painterResource(id = iconResource),
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
            Text(text = "Maximum 5 attempts", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.align(Alignment.End).padding(top = 4.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMeCheckedState, onCheckedChange = { presenter.onRememberMeToggled(it) })
                Text(text = "Remember me", fontSize = 14.sp)
            }
            TextButton(onClick = { }) { Text(text = "Forgot?", color = Color.Black, fontWeight = FontWeight.SemiBold) }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Handle Google Login Logic */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.login_google), contentDescription = "Google Logo", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continue with Google", fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Handle Apple Login Logic */ },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.login_ios), contentDescription = "Apple Logo", modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continue with Apple", fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { presenter.onSignInClicked() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D9CEC), disabledContainerColor = Color(0xFF5D9CEC).copy(alpha = 0.5f)),
            enabled = isSignInEnabledState
        ) {
            Text("Sign In", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.padding(bottom = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account? ", color = Color.Gray)
            TextButton(onClick = { presenter.onSignUpClicked() }, contentPadding = PaddingValues(0.dp)) {
                Text("Sign up", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}