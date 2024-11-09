package io.lb.impl.ktor.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.HttpStatusCode
import io.lb.common.data.model.PokemonCard
import io.lb.common.shared.error.MemoryGameException
import io.lb.impl.ktor.model.PokemonAPIResponse
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
    id: Int
): PokemonCard = withContext(coroutineDispatcher) {
    val response = get {
        url("https://pokeapi.co/api/v2/pokemon/$id/")
    }
    val body = response.body<PokemonAPIResponse?>() ?: throw MemoryGameException(
        HttpStatusCode.BadRequest.value,
        "Failed to get Pokemon"
    )
    return@withContext body.toPokemon()
}
