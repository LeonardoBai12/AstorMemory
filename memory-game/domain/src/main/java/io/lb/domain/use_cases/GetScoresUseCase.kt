package io.lb.domain.use_cases

import io.lb.common.data.model.Score
import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to get a list of scores.
 *
 * @property repository The repository to fetch data from.
 */
class GetScoresUseCase @Inject constructor(
    private val repository: MemoryGameRepository
) {
    /**
     * Fetches a list of scores.
     *
     * @return A [Flow] of [Resource] of a list of [Score] objects.
     */
    suspend operator fun invoke(): Flow<Resource<List<Score>>> = flow {
        emit(Resource.Loading())
        runCatching {
            val scores = repository.getScores()
            emit(Resource.Success(scores))
        }.getOrElse {
            emit(Resource.Error(it.message.toString()))
        }
    }
}
