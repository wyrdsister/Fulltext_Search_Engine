package fulltext.search.engine.engine

import fulltext.search.engine.entities.DocInfo
import fulltext.search.engine.entities.SearchResult
import org.apache.lucene.document.Document
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.Term
import org.apache.lucene.search.FuzzyQuery
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.PhraseQuery
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.BytesRef
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.*

class Index {

    private val logger = LoggerFactory.getLogger(Index::class.java)

    private lateinit var directory: Directory

    private lateinit var iWriter: IndexWriter

    constructor(path: Path) {
        this.directory = FSDirectory.open(path)
        this.iWriter = IndexWriter(directory, IndexWriterConfig())
    }

    fun addDocument(doc: Document) {
        if (iWriter == null)
            throw IllegalArgumentException("IndexWriter instance must not be null")

        if (!iWriter.isOpen)
            throw IllegalArgumentException("IndexWriter instance closed")

        iWriter.addDocument(doc)
        iWriter.commit()
    }



    fun searchPhrase(phrase: String, maxHints: Int, accuracy: Int): SearchResult {
        val iReader = DirectoryReader.open(directory)
        iReader.apply {
            val iSearcher = IndexSearcher(iReader)

            iSearcher.apply {
                //val queryParser = QueryParser("Content", StandardAnalyzer())

                val terms = phrase.lowercase(Locale.US).split(" ").map { BytesRef(it) }.toTypedArray()
                val query = PhraseQuery(accuracy, "Content", *terms)

                val docs = iSearcher.search(query, maxHints)
                val storedFields = iSearcher.storedFields()

                return SearchResult(docs.scoreDocs
                    .map { storedFields.document(it.doc) }
                    .map { DocInfo(it.get("Id"), it.get("Name"), it.get("Author"), it.get("Page").toInt()) }
                    .sortedBy { it.name }
                    .sortedBy { it.page },
                    docs.scoreDocs.size
                )
            }
        }
    }

    fun search(query: String, maxHints: Int): SearchResult {
        val iReader = DirectoryReader.open(directory)
        iReader.apply {
            val iSearcher = IndexSearcher(iReader)

            iSearcher.apply {
                val fuzzyQuery = FuzzyQuery(Term("Content", query), 2)
                val foundDocs = iSearcher.search(fuzzyQuery, maxHints)
                val storedFields = iSearcher.storedFields()

                return SearchResult(foundDocs.scoreDocs
                    .asSequence()
                    .filter { it.score > 0.2f }
                    .map { storedFields.document(it.doc) }
                    .map { DocInfo(it.get("Id"), it.get("Name"), it.get("Author"), it.get("Page").toInt()) }
                    .sortedBy { it.name }
                    .sortedBy { it.page }
                    .toList(),
                    foundDocs.scoreDocs.size
                )
            }

        }
    }



}