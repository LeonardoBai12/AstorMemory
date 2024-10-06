package io.lb.impl.ktor.client.util

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.lb.common.data.model.Pokemon
import io.lb.impl.ktor.client.model.PokemonAPIResponse
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
): List<Pokemon> = withContext(coroutineDispatcher) {
    val pokemon = mutableListOf<Pokemon>()

    repeat(amount) {
        val response = get("https://pokeapi.co/api/v2/pokemon/")
            .body<PokemonAPIResponse>()
        pokemon.add(response.toPokemon())
        pokemon.add(response.toPokemon())
    }

    return@withContext pokemon.shuffled()
}
