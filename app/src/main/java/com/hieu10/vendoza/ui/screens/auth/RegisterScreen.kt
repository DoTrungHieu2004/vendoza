package com.hieu10.vendoza.ui.screens.auth

import android.util.Patterns
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hieu10.vendoza.R
import com.hieu10.vendoza.ui.components.PasswordMeter
import com.hieu10.vendoza.ui.components.TermsCheckbox
import com.hieu10.vendoza.ui.components.bg.AppBG
import com.hieu10.vendoza.ui.theme.VendozaTheme
import com.hieu10.vendoza.utils.validation.AuthValidation.PHONE_REGEX
import com.hieu10.vendoza.viewmodel.AuthViewModel
import com.hieu10.vendoza.viewmodel.state.AuthUIState

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState) {
        when (uiState) {
            is AuthUIState.Success -> {
                onRegisterSuccess()
                viewModel.resetState()
            }
            is AuthUIState.Error -> {
                // Error displayed inside RegisterContent
            }
            else -> {}
        }
    }

    AppBG {
        RegisterContent(
            onRegisterClick = { username, email, phone, password ->
                viewModel.register(username, email, password, phone)
            },
            isLoading = uiState is AuthUIState.Loading,
            errorMessage = (uiState as? AuthUIState.Error)?.message,
            onNavigateBack = onNavigateBack,
            onNavigateToLogin = onNavigateToLogin,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun RegisterContent(
    onRegisterClick: (String, String, String, String) -> Unit,
    isLoading: Boolean,
    errorMessage: String?,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    // Validation flags
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var termsError by remember { mutableStateOf(false) }

    // Reset field errors when errorMessage changes (new failed attempt)
    LaunchedEffect(key1 = errorMessage) {
        if (errorMessage != null) {

        }
    }

    fun validateAndRegister() {
        // Run validation
        val isUsernameValid = username.length in 3..50
        val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPhoneValid = PHONE_REGEX.matches(phone)
        val isPasswordValid = password.length in 6..100
        val doPasswordsMatch = password == confirmPassword && password.isNotBlank()
        val isTermsAccepted = termsAccepted

        usernameError = !isUsernameValid
        emailError = !isEmailValid
        phoneError = !isPhoneValid
        passwordError = !isPasswordValid
        confirmPasswordError = !doPasswordsMatch
        termsError = !isTermsAccepted

        if (isUsernameValid && isEmailValid && isPhoneValid && isPasswordValid && doPasswordsMatch && isTermsAccepted) {
            onRegisterClick(username, email, phone, password)
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top bar with back button and title
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.headline_create_account),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(end = 48.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        // Username field
        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = false
            },
            label = { Text(text = stringResource(id = R.string.label_username)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = usernameError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            },
            supportingText = if (usernameError) {
                { Text(text = stringResource(id = R.string.error_username_required)) }
            } else null
        )

        Spacer(modifier = Modifier.height(12.dp))

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
                { Text(text = stringResource(id = R.string.error_email_invalid)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone field
        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
                phoneError = false
            },
            label = { Text(text = stringResource(id = R.string.label_phone)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = emailError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = null
                )
            },
            supportingText = if (emailError) {
                { Text(text = stringResource(id = R.string.error_phone_invalid)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password error
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
                confirmPasswordError = false    // recheck match
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
        
        // Password meter
        if (password.isNotBlank()) {
            PasswordMeter(
                password = password,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm password field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = false
            },
            label = { Text(text = stringResource(id = R.string.label_confirm_password)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = confirmPasswordError,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { confirmVisible = !confirmVisible }) {
                    Icon(
                        imageVector = if (confirmVisible)
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
                { Text(text = stringResource(id = R.string.error_password_not_match)) }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Terms checkbox
        TermsCheckbox(
            checked = termsAccepted,
            termsError = termsError,
            onCheckedChange = {
                termsAccepted = it
                if (it) termsError = false
            },
            onTermsClick = {},
            onPrivacyClick = {}
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Error message from backend
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Register button
        Button(
            onClick = { validateAndRegister() },
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
                    text = stringResource(id = R.string.btn_sign_up),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenLight() {
    VendozaTheme(darkTheme = false) {
        AppBG {
            RegisterContent(
                onRegisterClick = { _, _, _, _ -> },
                isLoading = false,
                errorMessage = null,
                onNavigateBack = {},
                onNavigateToLogin = {},
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
            RegisterContent(
                onRegisterClick = { _, _, _, _ -> },
                isLoading = false,
                errorMessage = null,
                onNavigateBack = {},
                onNavigateToLogin = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}