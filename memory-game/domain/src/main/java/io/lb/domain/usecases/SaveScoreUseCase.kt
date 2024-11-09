package io.lb.domain.usecases

import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to save a score.
 *
 * @property repository The repository to save the score to.
 */
class SaveScoreUseCase @Inject constructor(
    private val repository: MemoryGameRepository
) {
    /**
     * Saves a score.
     *
     * @param score The score to save.
     * @param amount The amount of cards in the game.
     *
     * @return A [Flow] of [Resource] of a [Unit] object.
     */
    suspend operator fun invoke(score: Int, amount: Int) {
        if (score > 0) {
            repository.insertScore(score, amount)
        }
    }
}
