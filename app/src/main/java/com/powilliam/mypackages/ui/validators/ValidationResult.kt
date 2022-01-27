package com.powilliam.mypackages.ui.validators

sealed class ValidationResult {
    object Empty : ValidationResult()
    object Valid : ValidationResult()
    data class InValid(val reason: String) : ValidationResult()
}
