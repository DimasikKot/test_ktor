package com.kouma.serialization

import kotlinx.serialization.Serializable

fun String.isValidEmail(): Boolean = true

@Serializable
data class RegisterIn(
    val login: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterOut(
    val token: String
)