package io.lb.domain.use_cases

import io.lb.common.data.model.Pokemon
import io.lb.common.shared.flow.Resource
import io.lb.domain.repository.MemoryGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetPokemonPairsUseCase @Inject constructor(
    private val repository: MemoryGameRepository
) {
    suspend operator fun invoke(amount: Int): Flow<Resource<List<Pokemon>>> = flow {
        emit(Resource.Loading())
        try {
            val response = repository.getPokemonPairs(amount)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}
