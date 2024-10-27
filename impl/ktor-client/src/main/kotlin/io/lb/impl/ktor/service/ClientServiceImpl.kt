package io.lb.impl.ktor.service

import io.ktor.client.HttpClient
import io.lb.common.data.model.PokemonCard
import io.lb.common.data.service.ClientService
import io.lb.impl.ktor.util.requestPokemonList

/**
 * Implementation of the [ClientService] interface.
 *
 * @property client The HTTP client to make requests with.
 */
internal class ClientServiceImpl(
    private val client: HttpClient
) : ClientService {
    override suspend fun getPokemonPairs(amount: Int): List<PokemonCard> {
        return client.requestPokemonList(amount = amount)
    }
}
