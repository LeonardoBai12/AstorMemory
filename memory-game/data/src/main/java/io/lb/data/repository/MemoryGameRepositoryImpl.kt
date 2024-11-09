package io.lb.data.repository

import io.lb.common.data.model.PokemonCard
import io.lb.common.data.model.Score
import io.lb.data.datasource.MemoryGameDataSource
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
    override suspend fun getPokemonPairs(amount: Int): List<PokemonCard> {
        val pokemonList = mutableListOf<PokemonCard>()
        val usedIds = mutableSetOf<Int>()
        repeat(amount) {
            var randomId: Int
            do {
                randomId = (MIN_ID..MAX_ID).random()
            } while (!usedIds.add(randomId))
            val pokemon = dataSource.getPokemonFromLocal(randomId) ?: run {
                val remotePokemon = dataSource.getPokemonFromRemote(randomId)
                dataSource.insertPokemon(remotePokemon)
                remotePokemon
            }
            pokemonList.add(pokemon)
            pokemonList.add(pokemon)
        }
        return pokemonList.shuffled()
    }

    override suspend fun getScores(): List<Score> {
        return dataSource.getScores()
    }

    override suspend fun getScoresByAmount(amount: Int): List<Score> {
        return dataSource.getScoresByAmount(amount)
    }

    override suspend fun insertScore(score: Int, amount: Int) {
        dataSource.insertScore(score, amount)
    }

    companion object {
        private const val MIN_ID = 1
        private const val MAX_ID = 999
    }
}
