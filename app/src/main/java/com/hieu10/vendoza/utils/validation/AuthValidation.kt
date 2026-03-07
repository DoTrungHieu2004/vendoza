package com.hieu10.vendoza.utils.validation

import android.util.Patterns
import com.hieu10.vendoza.R

object AuthValidation {
    private val PHONE_REGEX = Regex("^[\\\\+]?[(]?[0-9]{1,4}[)]?[-\\\\s.]?[(]?[0-9]{1,4}[)]?[-\\\\s.]?[0-9]{1,9}$")

    fun validateUsername(username: String): ValidationResult {
        return when {
            username.isBlank() -> ValidationResult.Invalid(R.string.error_username_required)
            username.length < 3 -> ValidationResult.Invalid(R.string.error_username_min_length)
            username.length > 50 -> ValidationResult.Invalid(R.string.error_username_max_length)
            else -> ValidationResult.Valid
        }
    }

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Invalid(R.string.error_email_required)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> ValidationResult.Invalid(R.string.error_email_invalid)
            else -> ValidationResult.Valid
        }
    }

    fun validatePhone(phone: String): ValidationResult {
        return when {
            phone.isBlank() -> ValidationResult.Invalid(R.string.error_phone_required)
            !PHONE_REGEX.matches(phone) -> ValidationResult.Invalid(R.string.error_phone_invalid)
            else -> ValidationResult.Valid
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult.Invalid(R.string.error_password_required)
            password.length < 6 -> ValidationResult.Invalid(R.string.error_password_min_length)
            password.length > 100 -> ValidationResult.Invalid(R.string.error_password_max_length)
            else -> ValidationResult.Valid
        }
    }

    // For login screen
    fun validateLoginInput(email: String, password: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult.Invalid(R.string.error_email_required)
            password.isBlank() -> ValidationResult.Invalid(R.string.error_password_required)
            else -> ValidationResult.Valid
        }
    }
}