package fulltext.search.engine.rest.entities

import kotlinx.serialization.Serializable

@Serializable
data class IndexInfo(
    val language: String,
    val libraryInfo: Library
)

@Serializable
data class Library(
    val name: String
)
