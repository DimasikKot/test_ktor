package com.kouma.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/connect") {
            call.respondText("true")
        }
    }
}