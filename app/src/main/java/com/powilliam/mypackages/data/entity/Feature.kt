package com.powilliam.mypackages.data.entity

sealed class Feature(val flag: String) {
    object Auth : Feature("isAuthenticationFeatureEnabled")
}