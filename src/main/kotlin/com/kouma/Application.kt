package com.kouma

import com.kouma.routing.*
import com.kouma.serialization.configureSerialization
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/test_ktor",
        user = "postgres",
        password = "Dima2004",
        driver = "org.postgresql.Driver"
    )
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureRoutingList()
    configureRoutingLogin()
    configureRoutingRegister()
    configureRoutingFiles()
}