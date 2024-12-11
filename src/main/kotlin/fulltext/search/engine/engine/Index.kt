package fulltext.search.engine.engine

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.Term
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.FuzzyQuery
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.PhraseQuery
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.*
import javax.print.Doc

class Index {

    private val logger = LoggerFactory.getLogger(Index::class.java)

    private lateinit var directory: Directory

    private lateinit var iWriter: IndexWriter

    constructor(path: Path){
        this.directory = FSDirectory.open(path)
        this.iWriter = IndexWriter(directory, IndexWriterConfig())
    }

    fun addDocument(doc: Document){
        if (iWriter == null)
            throw IllegalArgumentException("IndexWriter instance must not be null")

        if (!iWriter.isOpen)
            throw IllegalArgumentException("IndexWriter instance closed")

        iWriter.addDocument(doc)
        iWriter.commit()
    }

    fun searchPhrase(phrase: String, maxHints: Int, accuracy: Int) : List<DocInfo>{
        val iReader = DirectoryReader.open(directory)
        iReader.apply {
            val iSearcher = IndexSearcher(iReader)

            iSearcher.apply {
                //val queryParser = QueryParser("Content", StandardAnalyzer())

                val terms = phrase.lowercase(Locale.US).split(" ").toTypedArray()
                val query = PhraseQuery(accuracy, "Content", *terms)

                val docs = iSearcher.search(query, maxHints)
                val storedFields = iSearcher.storedFields()

                return docs.scoreDocs
                    .map { storedFields.document(it.doc) }
                    .map { DocInfo(it.get("Name"), it.get("Author")) }
            }
        }
    }

    fun search(query : String, maxHints: Int) : List<DocInfo> {
        val iReader = DirectoryReader.open(directory)
        iReader.apply {
            val iSearcher = IndexSearcher(iReader)

            iSearcher.apply {
                //val queryParser = QueryParser("Content", StandardAnalyzer())
                //val query = queryParser.parse("$query~")

                val fuzzyQuery = FuzzyQuery(Term("Content", query), 1)

                val docs = iSearcher.search(fuzzyQuery, maxHints)
                val storedFields = iSearcher.storedFields()

                return docs.scoreDocs
                    .map { storedFields.document(it.doc) }
                    .map { DocInfo(it.get("Name"), it.get("Author")) }
                    .sortedBy { it.name }
            }

        }
    }


    data class DocInfo(val name: String, val author: String)


}