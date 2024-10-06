package io.lb.data.repository

import io.lb.common.data.model.Pokemon
import io.lb.common.data.model.Score
import io.lb.common.data.service.ClientService
import io.lb.data.data_source.MemoryGameDataSource
import io.lb.domain.repository.MemoryGameRepository
import javax.inject.Inject

/**
 * Implementation of [MemoryGameRepository] that fetches data from the network.
 *
 * @property dataSource The data source to fetch data from.
 */
internal class MemoryGameRepositoryImpl @Inject constructor(
    private val dataSource: MemoryGameDataSource
) : MemoryGameRepository {
    override suspend fun getPokemonPairs(amount: Int): List<Pokemon> {
        return dataSource.getPokemonPairs(amount)
    }

    override suspend fun getScores(): List<Score> {
        return dataSource.getScores()
    }

    override suspend fun insertScore(score: Int) {
        dataSource.insertScore(score)
    }
}
