package fulltext.search.engine.engine

import be.rlab.nlp.model.Language
import be.rlab.search.DocumentBuilder
import be.rlab.search.IndexManager
import be.rlab.search.IndexMapper
import be.rlab.search.model.*
import be.rlab.search.query.fuzzy
import be.rlab.search.query.term
import org.apache.lucene.search.similarities.BM25Similarity

class IndexBuilder {

    private lateinit var indexManager : IndexManager
    private lateinit var indexMapper: IndexMapper

    init {
        createIndexManager()
        indexMapper = IndexMapper(indexManager)
    }

    fun addSpace(){
        indexManager.apply {
            addSchema(""){
                string("name")
                string("author")
            }
        }
    }

    fun addEnDocument(name: String, author: String, content: String){
       indexMapper.index(Book(name, author, content), Language.ENGLISH)
    }

    fun searchEnText(text: String) : List<String> {
        return indexMapper.search<Book>(Language.ENGLISH){
            term("text", text)
        }
            .docs
            .map { "${it.name} of ${it.author}" }
    }

    private fun createIndexManager() = IndexManager.Builder("/tmp/indexes")
        .forLanguages(listOf(Language.ENGLISH, Language.RUSSIAN))
        .withSimilarity(BM25Similarity())
        .build()
}