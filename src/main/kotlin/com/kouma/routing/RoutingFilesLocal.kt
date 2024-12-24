package com.kouma.routing

import com.kouma.serialization.fetchDir
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Application.configureRoutingFilesLocal() {
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
                HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(
                    ContentDisposition.Parameters.FileName,
                    URLEncoder.encode(file.name, StandardCharsets.UTF_8.toString()).replace("+", "%20")
                ).toString()
            )
            call.respondFile(file)
        }
    }
}