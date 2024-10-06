package io.lb.common.data.service

import io.lb.common.data.model.Pokemon

/**
 * Interface for the client service.
 */
interface ClientService {
    /**
     * Gets a list of Pokemon pais.
     *
     * @param amount The amount of Pokemon pairs to get.
     */
    suspend fun getPokemonPairs(
        amount: Int,
    ): List<Pokemon>
}
