package com.kouma.routing

import com.kouma.database.Tokens
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRoutingList() {
    routing {
        get("/list") {
            call.respond(Tokens.fetchAll())
        }
    }
}