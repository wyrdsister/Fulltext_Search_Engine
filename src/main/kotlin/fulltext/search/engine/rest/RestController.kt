package fulltext.search.engine.rest

import fulltext.search.engine.engine.IndexService
import fulltext.search.engine.rest.entities.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class RestController(
    @Autowired val indexService: IndexService
) {

    @PostMapping("/index")
    fun createSearchIndexes(@RequestBody indexInfo: IndexInfo) : IndexDto {
        indexService.createIndex(indexInfo.libraryInfo.name)
        return IndexDto(indexInfo.libraryInfo.name)
    }

    @PostMapping("/index/{id}")
    fun addDocument(@RequestBody doc: Document, @PathVariable id: String){
        indexService.addDocument(id, doc)
    }

    @GetMapping("index/{id}")
    fun searchText(searchQuery: SearchQuery, @PathVariable id: String) : List<SearchResult> {
        return indexService.search(id, searchQuery.text).map { SearchResult(it.name, it.author) }
    }
}