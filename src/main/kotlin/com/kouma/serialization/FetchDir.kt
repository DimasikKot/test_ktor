package com.kouma.serialization

import io.ktor.util.*
import kotlinx.serialization.Serializable
import java.io.File

fun fetchDir(dir: File): List<CatalogItem> {
    val result = mutableListOf<CatalogItem>()
    for (item in dir.listFiles()!!) {
        if (!item.isHidden) {
            result.add(
                CatalogItem(
                    isDir = item.isDirectory,
                    name = item.name,
                    extension = if (!item.isDirectory) item.extension else null
                )
            )
        }
    }
    return result.sortedBy { it.name.toLowerCasePreservingASCIIRules() }.sortedBy { !it.isDir }
}

@Serializable
data class CatalogItem(
    val isDir: Boolean,
    val name: String,
    val extension: String?
)