package com.hieu10.vendoza.utils.validation

import androidx.annotation.StringRes

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(@field:StringRes val error: Int) : ValidationResult()
}