package fulltext.search.engine.engine

import be.rlab.search.annotation.IndexDocument
import be.rlab.search.annotation.IndexField
import be.rlab.search.annotation.IndexFieldType
import be.rlab.search.model.FieldType

@IndexDocument(namespace = "")
data class Book(
    @IndexField @IndexFieldType(FieldType.STRING) val name: String,
    @IndexField @IndexFieldType(FieldType.STRING) val author: String,
    @IndexField @IndexFieldType(FieldType.TEXT) val text: String
)

