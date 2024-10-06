package io.lb.data.repository

import io.lb.common.data.model.Pokemon
import io.lb.common.data.service.ClientService
import io.lb.domain.repository.MemoryGameRepository
import javax.inject.Inject

/**
 * Implementation of [MemoryGameRepository] that fetches data from the network.
 *
 * @property service [ClientService] to fetch data from.
 */
internal class MemoryGameRepositoryImpl @Inject constructor(
    private val service: ClientService
) : MemoryGameRepository {
    override suspend fun getPokemonPairs(amount: Int): List<Pokemon> {
        return service.getPokemonPairs(amount)
    }
}
