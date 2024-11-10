package io.lb.impl.ktor.service

import io.ktor.client.HttpClient
import io.lb.common.data.model.PokemonCard
import io.lb.common.data.service.ClientService
import io.lb.impl.ktor.util.requestPokemon

/**
 * Implementation of the [ClientService] interface.
 *
 * @property client The HTTP client to make requests with.
 */
internal class ClientServiceImpl(
    private val client: HttpClient
) : ClientService {
    override suspend fun getPokemon(amount: Int): List<PokemonCard> {
        return client.requestPokemon(amount = amount)
    }
}
