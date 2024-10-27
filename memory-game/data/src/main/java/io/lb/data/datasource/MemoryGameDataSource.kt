package io.lb.data.datasource

import io.lb.common.data.service.ClientService
import io.lb.common.data.service.DatabaseService
import javax.inject.Inject

/**
 * Data source for the memory game.
 *
 * @property databaseService The service to interact with the database.
 * @property clientService The service to interact with the client.
 */
internal class MemoryGameDataSource @Inject constructor(
    private val databaseService: DatabaseService,
    private val clientService: ClientService
) {
    /**
     * Get the scores from the database.
     *
     * @return The scores.
     */
    suspend fun getScores() = databaseService.getScores()

    /**
     * Insert a score into the database.
     *
     * @param score The score to insert.
     */
    suspend fun insertScore(score: Int) = databaseService.insertScore(score)

    /**
     * Get a list of Pokemon pairs.
     *
     * @param amount The amount of Pokemon pairs to get.
     *
     * @return A list of Pokemon pairs.
     */
    suspend fun getPokemonPairs(amount: Int) = clientService.getPokemonPairs(amount)
}
