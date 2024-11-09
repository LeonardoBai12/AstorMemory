package io.lb.common.data.service

import io.lb.common.data.model.PokemonCard

/**
 * Interface for the client service.
 */
interface ClientService {
    /**
     * Get a Pokemon.
     *
     * @param id The ID of the Pokemon.
     */
    suspend fun getPokemon(id: Int): PokemonCard
}
