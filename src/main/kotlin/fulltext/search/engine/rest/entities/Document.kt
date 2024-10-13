package fulltext.search.engine.rest.entities

import kotlinx.serialization.Serializable

@Serializable
data class Document(
    val name: String,
    val author: String,
    val content: String
)