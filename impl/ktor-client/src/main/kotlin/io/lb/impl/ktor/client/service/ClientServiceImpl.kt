package io.lb.impl.ktor.client.service

import io.ktor.client.HttpClient
import io.lb.common.data.model.Pokemon
import io.lb.common.data.service.ClientService
import io.lb.impl.ktor.client.util.request

/**
 * Implementation of the [ClientService] interface.
 *
 * @property client The HTTP client to make requests with.
 */
internal class ClientServiceImpl(
    private val client: HttpClient
) : ClientService {
    override suspend fun getPokemonPairs(amount: Int): List<Pokemon> {
        return client.request(amount = amount)
    }
}
