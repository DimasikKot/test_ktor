package com.kouma.routing

import com.kouma.serialization.fetchDir
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRoutingFiles() {
    routing {
        get("/{disk}/") {
            val disk = call.parameters["disk"].toString()
            val file = File("$disk:/")
            if (file.isDirectory) {
                call.respond(fetchDir(file))
            }
            call.respondText("Такого диска нет или это файл?")
        }
        get("/{disk}/{path}") {
            val disk = call.parameters["disk"].toString()
            val path =
                "$disk://" + call.parameters["path"].toString().replace(":", "/").replace(">", "/").replace("*", "/")
            val file = File(path)
            if (file.isDirectory) {
                call.respond(fetchDir(file))
            }
            call.respondFile(file)
        }
        get("/{disk}/{path}/d") {
            val disk = call.parameters["disk"].toString()
            val path =
                "$disk://" + call.parameters["path"].toString().replace(":", "/").replace(">", "/").replace("*", "/")
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