package fulltext.search.engine.engine

import fulltext.search.engine.rest.entities.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.TextField
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class IndexService {

    private val indexes = HashMap<String, Index>()

    fun createIndex(id: String){
        val path = Files.createTempDirectory(id)
        indexes[id] = Index(path)
    }

    fun getIndex(id: String) : Index? {
        return indexes[id]
    }

    fun addDocument(id: String, input: Document){
        if (indexes[id] == null){
            return
        }

        val doc = org.apache.lucene.document.Document()
        doc.add(Field("Name", input.name, TextField.TYPE_STORED))
        doc.add(Field("Author", input.author, TextField.TYPE_STORED))
        doc.add(Field("Content", input.content, TextField.TYPE_NOT_STORED))

        indexes[id]!!.addDocument(doc)
    }

    fun search(id: String, query: String): List<Index.DocInfo> {
        if (indexes[id] == null){
            throw Exception("Can't find index with id=$id")
        }

        return indexes[id]!!.search(query, 10)
    }

}