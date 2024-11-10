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
//        val localPokemon = dataSource.getPokemonListFromLocal()
//        if (localPokemon.size >= 999) {
//            val localList = localPokemon.take(amount).shuffled().toMutableList()
//            localList.addAll(localList.shuffled())
//            return localList.shuffled()
//        }
        val remotePokemon = // kotlin.runCatching {
            dataSource.getPokemonListFromRemote(amount)
//        }.getOrElse {
//            if (localPokemon.size < amount) {
//                throw it
//            }
//            val localList = localPokemon.take(amount).shuffled().toMutableList()
//            localList.addAll(localList.shuffled())
//            return localList.shuffled()
//        }
        remotePokemon.forEach {
            if (dataSource.getPokemonFromLocal(it.pokemonId) == null) {
                dataSource.insertPokemon(it)
            }
        }
        val remoteList = remotePokemon.take(amount).shuffled().toMutableList()
        remoteList.addAll(remoteList.shuffled())
        return remoteList.shuffled()
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
}
