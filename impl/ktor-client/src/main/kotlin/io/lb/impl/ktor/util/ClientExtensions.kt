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
internal suspend fun HttpClient.requestPokemonList(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    amount: Int
): List<PokemonCard> = withContext(coroutineDispatcher) {
    val pokemon = mutableListOf<PokemonCard>()
    val usedIds = mutableSetOf<Int>()

    repeat(amount) {
        var randomId: Int
        do {
            randomId = (MIN_ID..MAX_ID).random()
        } while (!usedIds.add(randomId))
        val response = get {
            url("https://pokeapi.co/api/v2/pokemon/$randomId/")
        }
        val body = response.body<PokemonAPIResponse?>() ?: throw MemoryGameException(
            HttpStatusCode.BadRequest.value,
            "Failed to get Pokemon"
        )

        pokemon.add(body.toPokemon())
        pokemon.add(body.toPokemon())
    }

    return@withContext pokemon.shuffled()
}

private const val MIN_ID = 1
private const val MAX_ID = 999
