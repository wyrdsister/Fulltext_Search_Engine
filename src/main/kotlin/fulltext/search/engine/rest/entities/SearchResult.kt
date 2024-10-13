package fulltext.search.engine.rest.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val documentName: String,
    val documentAuthor: String
)
