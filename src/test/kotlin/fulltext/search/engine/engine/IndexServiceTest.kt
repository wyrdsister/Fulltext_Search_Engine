package fulltext.search.engine.engine

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class IndexServiceTest {

    private val indexService = IndexService()

    @Test
    fun testCreateIndex() {
        val id = "test_index"

        indexService.createIndex(id)

        assert(indexService.getIndex(id) != null)
    }

    @Test
    fun getIndex() {
    }

    @Test
    fun addDocument() {
    }
}