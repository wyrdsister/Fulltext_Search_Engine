package fulltext.search.engine.entities

import kotlinx.serialization.Serializable

@Serializable
data class Document(
    val id: String?,
    val name: String,
    val author: String,
    val content: String,
    val page: Int = -1
)