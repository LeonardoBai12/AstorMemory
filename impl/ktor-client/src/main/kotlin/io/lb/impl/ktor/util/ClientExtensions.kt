package io.lb.impl.ktor.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
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

    repeat(amount) {
        val randomId = (1..500).random()
        val response = get {
            url("https://pokeapi.co/api/v2/pokemon/$randomId/")
        }
        val body = response.body<PokemonAPIResponse?>() ?: throw MemoryGameException(500, "Failed to get Pokemon")

        pokemon.add(body.toPokemon())
        pokemon.add(body.toPokemon())
    }

    return@withContext pokemon.shuffled()
}
