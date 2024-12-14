package com.kouma.routing

import com.kouma.database.TokenDTO
import com.kouma.database.Tokens
import com.kouma.database.Users
import com.kouma.serialization.LoginIn
import com.kouma.serialization.LoginOut
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRoutingLogin() {
    routing {
        post("/login") {
            val requestIn = call.receive<LoginIn>()

            val userDTO = Users.fetchItem(login = requestIn.login)

            if (userDTO == null) {
                call.respond(HttpStatusCode.BadRequest, "User not found")
            } else {

                if (userDTO.password != requestIn.password) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid password")
                } else {

                    val token = UUID.randomUUID().toString()
                    Tokens.insertItem(
                        TokenDTO(
                            token = token,
                            login = requestIn.login
                        )
                    )

                    call.respond(LoginOut(token))

                    return@post
                }
            }
        }
    }
}