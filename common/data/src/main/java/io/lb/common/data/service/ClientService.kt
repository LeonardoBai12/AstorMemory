package io.lb.common.data.service

import io.lb.common.data.model.PokemonCard

/**
 * Interface for the client service.
 */
interface ClientService {
    /**
     * Get a list of Pokemon.
     *
     * @param amount The amount of Pokemon to get.
     *
     * @return The list of Pokemon.
     */
    suspend fun getPokemon(amount: Int): List<PokemonCard>
}
