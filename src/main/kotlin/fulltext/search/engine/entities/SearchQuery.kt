package fulltext.search.engine.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchQuery(
    val text: String
)
