package io.lb.data.datasource

import io.lb.common.data.service.DatabaseService
import javax.inject.Inject

/**
 * Data source for the memory game.
 *
 * @property databaseService The service to interact with the database.
 */
internal class MemoryGameDataSource @Inject constructor(
    private val databaseService: DatabaseService,
) {
    /**
     * Get the scores from the database.
     *
     * @return The scores.
     */
    suspend fun getScores() = databaseService.getScores()

    /**
     * Get the scores from the database by amount of cards.
     *
     * @param amount The amount of cards in the game.
     *
     * @return The scores.
     */
    suspend fun getScoresByAmount(amount: Int) = databaseService.getScoresByAmount(amount)

    /**
     * Insert a score into the database.
     *
     * @param score The score to insert.
     * @param amount The amount of cards in the game.
     */
    suspend fun insertScore(score: Int, amount: Int) = databaseService.insertScore(score, amount)
}
