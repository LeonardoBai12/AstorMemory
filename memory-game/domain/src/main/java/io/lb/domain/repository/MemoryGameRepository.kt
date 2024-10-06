package io.lb.domain.repository

import io.lb.common.data.model.PokemonCard
import io.lb.common.data.model.Score
import io.lb.common.shared.error.MemoryGameException

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
    suspend fun getPokemonPairs(amount: Int): List<PokemonCard>

    /**
     * Get a list of scores.
     *
     * @return a list of scores.
     */
    suspend fun getScores(): List<Score>

    /**
     * Insert a score into the database.
     *
     * @param score the score to insert.
     */
    @Throws(MemoryGameException::class)
    suspend fun insertScore(score: Int)
}
