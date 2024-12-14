package com.kouma.routing

import com.kouma.database.TokenDTO
import com.kouma.database.Tokens
import com.kouma.database.UserDTO
import com.kouma.database.Users
import com.kouma.serialization.RegisterIn
import com.kouma.serialization.RegisterOut
import com.kouma.serialization.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.util.*

fun Application.configureRoutingRegister() {
    routing {
        post("/register") {
            val requestIn = call.receive<RegisterIn>()

            if (!requestIn.email.isValidEmail()) {
                call.respond(HttpStatusCode.BadRequest, "Email is not Valid")
            }

            val userDTO = Users.fetchItem(login = requestIn.login)

            if (userDTO != null) {
                call.respond(HttpStatusCode.Conflict, "User already exists with such login")
            } else {

                try {
                    Users.insertItem(
                        UserDTO(
                            login = requestIn.login,
                            password = requestIn.password,
                            email = requestIn.email,
                            username = ""
                        )
                    )
                } catch (e: ExposedSQLException) {
                    call.respond(HttpStatusCode.Conflict, "User already exists with such email")
                }

                val token = UUID.randomUUID().toString()
                Tokens.insertItem(
                    TokenDTO(
                        token = token,
                        login = requestIn.login
                    )
                )

                call.respond(RegisterOut(token))
            }
        }
    }
}