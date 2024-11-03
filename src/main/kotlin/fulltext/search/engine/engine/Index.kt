package fulltext.search.engine.engine

import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.slf4j.LoggerFactory
import java.nio.file.Path

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




}