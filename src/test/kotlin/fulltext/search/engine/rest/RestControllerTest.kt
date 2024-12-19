package fulltext.search.engine.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.UUID


@SpringBootTest
@AutoConfigureMockMvc
class RestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testCreateIndexSuccess() {
        val json = "{\"language\": \"EN\", \"libraryInfo\" : { \"name\" : \"test\"}}"

        val result = mockMvc.perform(
            post("/api/v1/index")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )

        result.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value("test"))
    }

    @Test
    fun testAddDocumentSuccess() {
        val indexId = "test"
        val json = "{\"language\": \"EN\", \"libraryInfo\" : { \"name\" : \"$indexId\"}}"
        val result = mockMvc.perform(
            post("/api/v1/index")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )

        val docId = UUID.randomUUID().toString()
        val documentJson = "{\"id\": \"$docId\", \"name\" : \"Book\", \"author\": \"Will Smith\", \"content\" : \"bla bla bla\"}"

        val operationResult = mockMvc.perform(
            post("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(documentJson)
        )

        operationResult.andExpect(status().isOk())
                        .andExpect(content().string(docId))

    }

    @Test
    fun testSearchWordSuccess() {
        val indexId = createIndexWithDocuments()

        val query = "{\"text\" : \"boats\"}"

        val result = mockMvc.perform(
            get("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))

        result.andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.documents[0].name").value("The Great Gatsby"))
    }

    @Test
    fun testSearchWordNothing() {
        val indexId = createIndexWithDocuments()

        val query = "{\"text\" : \"punishment\"}"

        val result = mockMvc.perform(
            get("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))

        result.andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size").value(0))
    }

    @Test
    fun testSearchPhraseSuccess() {
        val indexId = createIndexWithDocuments()

        val query = "{\"text\" : \"want of wife\"}"

        val result = mockMvc.perform(
            get("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))

        result.andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.documents[0].name").value("Pride and Prejudice"))
    }

    @Test
    fun testSearchPhraseNothing() {
        val indexId = createIndexWithDocuments()

        val query = "{\"text\" : \"this is life\"}"

        val result = mockMvc.perform(
            get("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))

        result.andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.size").value(0))
    }

    private fun createIndexWithDocuments(): String {
        val indexId = "test"
        val json = "{\"language\": \"EN\", \"libraryInfo\" : { \"name\" : \"$indexId\"}}"
        mockMvc.perform(
            post("/api/v1/index")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )

        val docId1 = UUID.randomUUID().toString()
        val documentJson1 =
            "{\"id\": \"$docId1\", \"name\" : \"Pride and Prejudice\", \"author\": \"Jane Austen\", \"content\" : \"It is a truth universally acknowledged, that a single man in possession of a good fortune, must be in want of a wife.\"}"

        val docId2 = UUID.randomUUID().toString()
        val documentJson2 =
            "{\"id\": \"$docId2\", \"name\" : \"The Great Gatsby\", \"author\": \"F. Scott Fitzgerald\", \"content\" : \"So we beat on, boats against the current, borne back ceaselessly into the past.\"}"

        mockMvc.perform(
            post("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(documentJson1)
        )
        mockMvc.perform(
            post("/api/v1/index/$indexId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(documentJson2)
        )
        return indexId
    }

}