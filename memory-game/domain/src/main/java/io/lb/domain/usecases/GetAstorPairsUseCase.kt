package io.lb.domain.usecases

import io.lb.common.data.model.AstorCard
import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case to get a list of Astor pairs.
 *
 * @property repository The repository to fetch data from.
 */
class GetAstorPairsUseCase @Inject constructor(
    private val repository: MemoryGameRepository
) {
    /**
     * Fetches a list of Astor pairs.
     *
     * @param amount The amount of Astor pairs to fetch.
     * @return A [Flow] of [Resource] of a list of [AstorCard] objects.
     */
    operator fun invoke(amount: Int): Flow<Resource<List<AstorCard>>> = flow {
        emit(Resource.Loading())
        runCatching {
            val response = repository.getAstorPairs(amount)
            emit(Resource.Success(response))
        }.getOrElse {
            emit(Resource.Error(it.message.toString()))
        }
    }
}
