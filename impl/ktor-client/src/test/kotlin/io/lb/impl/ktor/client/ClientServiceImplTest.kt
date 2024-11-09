package io.lb.impl.ktor.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.lb.impl.ktor.model.PokemonAPIResponse
import io.lb.impl.ktor.model.PokemonSprite
import io.lb.impl.ktor.service.ClientServiceImpl
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ClientServiceImplTest {
    private val json = Json { ignoreUnknownKeys = true }
    private lateinit var httpClient: HttpClient
    private lateinit var service: ClientServiceImpl
    private lateinit var mockEngine: MockEngine

    @BeforeEach
    fun setUp() {
        var id = 0
        mockEngine = MockEngine { request ->
            if (request.url.encodedPath.contains("/api/v2/pokemon/")) {
                respond(
                    content = json.encodeToString(
                        PokemonAPIResponse(
                            id = id++,
                            name = "Pokemon$id",
                            sprites = PokemonSprite(""),
                        )
                    ),
                    status = HttpStatusCode.OK,
                    headers = headersOf("Content-Type" to listOf("application/json"))
                )
            } else {
                respond(
                    content = """{"key":"error"}""",
                    status = HttpStatusCode.NotFound,
                    headers = headersOf("Content-Type" to listOf("application/json"))
                )
            }
        }

        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(
                    json
                )
            }
        }
        service = ClientServiceImpl(httpClient)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `When requests 5 Pokemon, expect a list with 10 Pokemon - repeating twice the five`() = runTest {
        val response = service.getPokemon(5)

        Assertions.assertEquals(5, response.distinct().size)
        Assertions.assertEquals(10, response.size)
    }
}
