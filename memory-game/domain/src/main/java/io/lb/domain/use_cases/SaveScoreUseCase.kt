package io.lb.domain.use_cases

import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
     * @return A [Flow] of [Resource] of a [Unit] object.
     */
    suspend operator fun invoke(score: Int) {
        repository.insertScore(score)
    }
}
