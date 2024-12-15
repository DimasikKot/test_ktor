package com.kouma.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRoutingLocal() {
    routing {
        route("/local") {
            get("/") {
                val file = File("files/")
                if (file.isDirectory) {
                    call.respond(fetchDir(file))
                }
            }
            get("/{dir}/d") {
                val dir = call.parameters["dir"].toString()
                val file = File("files/$dir")
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, dir)
                        .toString()
                )
                call.respondFile(file)
            }
            get("/{dir}") {
                val dir = call.parameters["dir"].toString()
                val file = File("files/$dir")
                call.respondFile(file)
            }
        }
    }
}