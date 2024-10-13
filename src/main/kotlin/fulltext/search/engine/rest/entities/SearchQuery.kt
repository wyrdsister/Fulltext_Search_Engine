package fulltext.search.engine.rest.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchQuery(
    val text: String
)
