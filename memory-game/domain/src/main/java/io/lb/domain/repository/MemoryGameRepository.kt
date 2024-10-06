package io.lb.domain.repository

import io.lb.common.data.model.Pokemon

/**
 * Repository for the Memory Game.
 */
interface MemoryGameRepository {
    /**
     * Get a list of Pokemon pairs.
     *
     * @param amount the amount of Pokemon pairs to get.
     *
     * @return a list of Pokemon pairs.
     */
    suspend fun getPokemonPairs(amount: Int): List<Pokemon>
}
