package com.kouma.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRoutingFiles() {
    routing {
        route("/files") {
            get("/") {
                call.respondFile(File("files/Как написать backend Свой сервер на Котлин. Ktor. Полный курс.mp4"))
            }
            get("/download") {
                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "")
                        .toString()
                )
                call.respondFile(File("files/0001.png"))
            }
        }
//        get("/files/{uuid}") {
//            val file = File("files/uuid")
//
//            call.response.header(
//                HttpHeaders.ContentDisposition,
//                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "uuid")
//                    .toString()
//            )
//            call.respondFile(file)
//        }
    }
}