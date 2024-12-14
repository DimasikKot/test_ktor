package com.kouma.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserDTO(
    val login: String,
    val password: String,
    val email: String?,
    val username: String
)

object Users : Table("users") {
    val login = varchar("login", 20).uniqueIndex()
    private val password = varchar("password", 20)
    private val username = varchar("username", 20)
    private val email = varchar("email", 30).uniqueIndex().nullable()

    override val primaryKey = PrimaryKey(login, name = "pk_users")

    fun insertItem(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[email] = userDTO.email ?: ""
                it[username] = userDTO.username
            }
        }
    }

    fun fetchItem(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    login = userModel[Users.login],
                    password = userModel[password],
                    username = userModel[username],
                    email = userModel[email]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}