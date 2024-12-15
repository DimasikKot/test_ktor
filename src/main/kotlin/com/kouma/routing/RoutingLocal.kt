package com.kouma.routing

import com.kouma.serialization.fetchDir
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRoutingLocal() {
    routing {
        get("/local/") {
            val file = File("files/")
            if (file.isDirectory) {
                call.respond(fetchDir(file))
            }
            call.respondText("Такого диска нет или это файл?")
        }
        get("/local/{path}") {
            val path =
                "files/" + call.parameters["path"].toString().replace(":", "/").replace(">", "/").replace("*", "/")
            val file = File(path)
            if (file.isDirectory) {
                call.respond(fetchDir(file))
            }
            call.respondFile(file)
        }
        get("/local/{path}/d") {
            val path =
                "files/" + call.parameters["path"].toString().replace(":", "/").replace(">", "/").replace("*", "/")
            val file = File(path)
            if (file.isDirectory) {
                call.respond(fetchDir(file))
            }
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, file.name)
                    .toString()
            )
            call.respondFile(file)
        }
    }
}