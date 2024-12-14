package com.kouma.serialization

import kotlinx.serialization.Serializable

@Serializable
data class LoginIn(
    val login: String,
    val password: String
)

@Serializable
data class LoginOut(
    val token: String
)