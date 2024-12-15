package com.kouma.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File

fun Application.configureRoutingFiles() {
    routing {
        route("/files") {
            get("/") {
                call.respondText("В будущем будет возможность увидеть список files/")
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
        route("/T") {
            get("/") {
                val dir = File("T://")
                if (dir.isDirectory) {
                    call.respond(fetchDir(dir))
                }
                call.respondText("В будущем будет возможность увидеть список T://")
            }
            get("/{dir}/d") {
                val dir = call.parameters["dir"].toString()
                val filePath = "T://" + dir.replace(":", "/").replace(">", "/").replace("*", "/")
                try {
                    val file = File(filePath)
                    if (file.isDirectory) {
                        call.respond(fetchDir(file))
                    }
                    call.response.header(
                        HttpHeaders.ContentDisposition,
                        ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, file.name)
                            .toString()
                    )
                    call.respondFile(file)
                } catch (e: Exception) {
                    println("[ERROR] $e")
                    call.respondText("[ERROR] $filePath")
                }
            }
            get("/{dir}") {
                val dir = call.parameters["dir"].toString()
                val filePath = "T://" + dir.replace(":", "/").replace(">", "/").replace("*", "/")
                try {
                    val file = File(filePath)
                    if (file.isDirectory) {
                        call.respond(fetchDir(file))
                    }
                    call.respondFile(file)
                } catch (e: Exception) {
                    println("[ERROR] $e")
                    call.respondText("[ERROR] $filePath")
                }
            }
        }
    }
}

fun fetchDir(dir: File): List<CatalogItem> {
    val result = mutableListOf<CatalogItem>()
    for (item in dir.listFiles()!!) {
        result.add(
            CatalogItem(
                isDir = item.isDirectory,
                name = item.name
            )
        )
    }
    return result
}

@Serializable
data class CatalogItem(
    val isDir: Boolean,
    val name: String
)