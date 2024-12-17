package fulltext.search.engine.entities

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val documents: List<DocInfo>,
    val size: Int
)

@Serializable
data class DocInfo(
    val id: String,
    val name: String,
    val author: String,
    val page: Int
)
