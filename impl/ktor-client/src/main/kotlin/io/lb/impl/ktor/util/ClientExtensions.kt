package io.lb.impl.ktor.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.lb.common.data.model.PokemonCard
import io.lb.common.shared.error.MemoryGameException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Extension function to make a request to the API.
 *
 * @throws IllegalArgumentException If the base URL does not start with "https://".
 *
 * @return The response from the API.
 */
internal suspend fun HttpClient.requestPokemon(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    amount: Int
): List<PokemonCard> = withContext(coroutineDispatcher) {
    val response = get {
        accept(ContentType.Any)
        header("Content-Type", "application/json")
        bearerAuth("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1c2VycyIsImlzcyI6Imh0dHA6Ly8wLjAuMC4wOjgwODAiLCJleHAiOjE3NjI3NDIyMDIsInVzZXJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4ifQ.bXQnXi51Bp3KFFSUmIKUkvVqlexOOB0mOXbmUsHZTzc")
        url("https://pokamann-env.eba-p9qmtfkx.sa-east-1.elasticbeanstalk.com/api/pokemon?amount=$amount")
    }
    val body = response.body<List<PokemonCard>?>() ?: throw MemoryGameException(
        HttpStatusCode.BadRequest.value,
        "Failed to get Pokemon"
    )
    return@withContext body
}
