package fulltext.search.engine.rest

import fulltext.search.engine.engine.IndexBuilder
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
class RestController {

    @PostMapping("/index")
    fun createSearchIndexes(@RequestBody indexInfo: IndexInfo) : Index {
        return Index(indexInfo.libraryInfo.name + "1")
    }

    @PostMapping("/index/{id}")
    fun addDocument(@RequestBody doc: Document, @PathVariable id: String){

    }

    @GetMapping("index/{id}")
    fun searchText(searchQuery: SearchQuery) : List<SearchResult> {
        return listOf(SearchResult("1", "T"))
    }
}