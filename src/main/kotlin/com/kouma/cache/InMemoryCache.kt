package com.kouma.cache

import com.kouma.serialization.RegisterIn

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    val userList: MutableList<RegisterIn> = mutableListOf()
    val tokenList: MutableList<TokenCache> = mutableListOf()
}