package fulltext.search.engine.engine

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.slf4j.LoggerFactory
import java.nio.file.Path
import javax.print.Doc

class Index {

    private val logger = LoggerFactory.getLogger(Index::class.java)

    private lateinit var directory: Directory

    private lateinit var iWriter: IndexWriter

    private lateinit var iSearcher: IndexSearcher

    private lateinit var iReader: IndexReader

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

    fun search(query : String, maxHints: Int) : List<DocInfo> {
        if (iReader == null)
            iReader = DirectoryReader.open(directory)

        if (iSearcher == null)
            iSearcher = IndexSearcher(iReader)

        val queryParser = QueryParser("Content", StandardAnalyzer())
        val query = queryParser.parse(query)

        val docs = iSearcher.search(query, maxHints)
        val storedFields = iSearcher.storedFields()

        return docs.scoreDocs
            .map { storedFields.document(it.doc) }
            .map { DocInfo(it.get("Name"), it.get("Author")) }
    }


    data class DocInfo(val name: String, val author: String)


}