package com.hieu10.vendoza.utils.validation

import androidx.compose.ui.graphics.Color

enum class PasswordStrength {
    WEAK, MEDIUM, STRONG;

    fun getColor(): Color = when (this) {
        WEAK -> Color.Red
        MEDIUM -> Color.Yellow
        STRONG -> Color.Green
    }

    fun getProgress(): Float = when (this) {
        WEAK -> 0.33f
        MEDIUM -> 0.66f
        STRONG -> 1f
    }
}

fun evaluatePasswordStrength(password: String): PasswordStrength {
    return when {
        password.length < 6 -> PasswordStrength.WEAK
        password.length in 6..8 -> {
            val hasLetter = password.any { it.isLetter() }
            val hasDigit = password.any { it.isDigit() }
            if (hasLetter && hasDigit) PasswordStrength.MEDIUM else PasswordStrength.WEAK
        }
        else -> {
            val hasLetter = password.any { it.isLetter() }
            val hasDigit = password.any { it.isDigit() }
            val hasSpecial = password.any { !it.isLetterOrDigit() }
            when {
                hasLetter && hasDigit && hasSpecial -> PasswordStrength.STRONG
                (hasLetter && hasDigit) || (hasLetter && hasSpecial) -> PasswordStrength.MEDIUM
                else -> PasswordStrength.WEAK
            }
        }
    }
}