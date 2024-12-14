package com.kouma.database

import com.kouma.cache.TokenCache
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class TokenDTO(
    val token: String,
    val login: String
)

object Tokens : Table("tokens") {
    private val token = varchar("token", 36).uniqueIndex()
    private val login = varchar("login", 20) references Users.login

    override val primaryKey = PrimaryKey(token, name = "pk_tokens")

    fun insertItem(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[token] = tokenDTO.token
                it[login] = tokenDTO.login
            }
        }
    }

    fun fetchAll(): List<TokenBD> {
        return transaction {
            val result = mutableListOf<TokenBD>()
            Tokens.selectAll().forEach { row ->
                result.add(TokenBD(token = row[token], login = row[login]))
            }
            result
        }
    }
}

@Serializable
data class TokenBD(
    val token: String,
    val login: String
)