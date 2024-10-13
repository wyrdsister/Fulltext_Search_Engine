package fulltext.search.engine.engine

interface IndexService {
    fun createIndex() : String

    fun addDocument()
}