package fulltext.search.engine.engine

import fulltext.search.engine.entities.Document
import fulltext.search.engine.entities.SearchResult
import org.apache.lucene.document.Field
import org.apache.lucene.document.IntField
import org.apache.lucene.document.TextField
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.util.UUID

@Service
class IndexService {

    private val indexes = HashMap<String, Index>()

    fun createIndex(id: String) {
        val path = Files.createTempDirectory(id)
        indexes[id] = Index(path)
    }

    fun getIndex(id: String): Index? {
        return indexes[id]
    }

    fun addDocument(id: String, input: Document) : String{
        if (indexes[id] == null) {
            throw NullPointerException("Can't find index with id=$id")
        }

        val docId = if (input.id.isNullOrEmpty()) UUID.randomUUID().toString() else input.id
        val doc = org.apache.lucene.document.Document()
        doc.add(Field("Id", docId, TextField.TYPE_STORED))
        doc.add(Field("Name", input.name, TextField.TYPE_STORED))
        doc.add(Field("Author", input.author, TextField.TYPE_STORED))
        doc.add(Field("Content", input.content, TextField.TYPE_NOT_STORED))
        doc.add(IntField("Page", input.page, Field.Store.YES))

        indexes[id]!!.addDocument(doc)
        return docId
    }

    fun search(id: String, query: String): SearchResult {
        if (indexes[id] == null) {
            throw Exception("Can't find index with id=$id")
        }

        return if (query.isPhrase()) indexes[id]!!.searchPhrase(query, 10, 2)
        else indexes[id]!!.search(query, 10)
    }

}

fun String.isPhrase() = this.split(" ").count() > 1