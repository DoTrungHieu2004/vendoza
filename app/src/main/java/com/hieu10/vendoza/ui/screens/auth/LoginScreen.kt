package com.hieu10.vendoza.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hieu10.vendoza.BuildConfig
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.components.bg.AppBG
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.viewmodel.AuthViewModel
import com.hieu10.vendoza.viewmodel.state.AuthUIState

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is AuthUIState.Success -> {
                onLoginSuccess()
                viewModel.resetState()
            }
            is AuthUIState.Error -> {
                // Error is displayed inside LoginContent via the error message
            }
            else -> {}
        }
    }

    AppBG {
        LoginContent(
            onLoginClick = { email, password ->
                viewModel.login(email, password)
            },
            onNavigateToRegister = onNavigateToRegister,
            onNavigateToForgotPassword = onNavigateToForgotPassword,
            isLoading = uiState is AuthUIState.Loading,
            errorMessage = (uiState as? AuthUIState.Error)?.message,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun LoginContent(
    onLoginClick: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // Reset failed errors when errorMessage changes (new failed attempt)
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage != null) {
            emailError = true
            passwordError = true
        }
    }

    // Validate on submit
    fun validateAndLogin() {
        val isEmailValid = email.isNotBlank()
        val isPasswordValid = password.isNotBlank()
        emailError = !isEmailValid
        passwordError = !isPasswordValid
        if (isEmailValid && isPasswordValid) {
            onLoginClick(email, password)
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Fixed-color logo (independent of theme)
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(id = R.string.cd_app_logo),
            modifier = Modifier.size(120.dp),
            tint = Color.Unspecified    // prevent theme tinting
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.headline_welcome_back),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        Text(
            text = stringResource(id = R.string.subtitle_sign_in),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text(text = stringResource(id = R.string.label_email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = emailError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },
            supportingText = if (emailError) {
                { Text(text = stringResource(id = R.string.error_email_required)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(12.dp))
        
        // Password field with visibility toggle
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            label = { Text(text = stringResource(id = R.string.label_password)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = passwordError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible)
                            Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            supportingText = if (emailError) {
                { Text(text = stringResource(id = R.string.error_password_required)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Forgot password link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onNavigateToForgotPassword) {
                Text(text = stringResource(id = R.string.txt_btn_forgot_password))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        
        // Login button with loading indicator
        Button(
            onClick = { validateAndLogin() },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = stringResource(id = R.string.btn_sign_in),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign up link
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.no_account_question))
            TextButton(onClick = onNavigateToRegister) {
                Text(text = stringResource(id = R.string.txt_btn_sign_up))
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Bottom info
        val appVersion = BuildConfig.VERSION_NAME
        val versionText = stringResource(id = R.string.app_version, appVersion)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = versionText,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = stringResource(id = R.string.copyright),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        AppBG {
            LoginContent(
                onLoginClick = { _, _ -> },
                onNavigateToRegister = {},
                onNavigateToForgotPassword = {},
                isLoading = false,
                errorMessage = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenDark() {
    VendozaTheme(darkTheme = true) {
        AppBG {
            LoginContent(
                onLoginClick = { _, _ -> },
                onNavigateToRegister = {},
                onNavigateToForgotPassword = {},
                isLoading = false,
                errorMessage = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}