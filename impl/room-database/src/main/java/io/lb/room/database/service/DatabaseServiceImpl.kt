package io.lb.room.database.service

import io.lb.common.data.model.PokemonCard
import io.lb.common.data.model.Score
import io.lb.common.data.service.DatabaseService
import io.lb.common.shared.error.MemoryGameException
import io.lb.room.database.dao.MemoryGameDao
import io.lb.room.database.model.PokemonCardEntity
import io.lb.room.database.model.ScoreEntity
import javax.inject.Inject

/**
 * Implementation of the [DatabaseService] interface.
 *
 * @property dao The DAO for the database.
 */
internal class DatabaseServiceImpl @Inject constructor(
    private val dao: MemoryGameDao
) : DatabaseService {
    override suspend fun getScores(): List<Score> {
        return dao.getScores().map { it.toScore() }
            .distinctBy {
                it.score
            }
    }

    override suspend fun getScoresByAmount(amount: Int): List<Score> {
        return dao.getScoresByAmount(amount).map { it.toScore() }
            .distinctBy {
                it.score
            }.take(MAX_SCORES)
    }

    @Throws(MemoryGameException::class)
    override suspend fun insertScore(
        score: Int,
        amount: Int
    ) {
        val scores = dao.getScoresByAmount(amount)
        if (scores.size < MAX_SCORES || score > scores.last().score) {
            dao.insertScore(ScoreEntity(score = score, amount = amount))
        }
    }

    override suspend fun insertPokemon(pokemonCard: PokemonCard) {
        dao.insertPokemon(
            PokemonCardEntity(
                pokemonId = pokemonCard.pokemonId,
                name = pokemonCard.name,
                imageUrl = pokemonCard.imageUrl,
                imageData = pokemonCard.imageData
            )
        )
    }

    override suspend fun getPokemonById(id: Int): PokemonCard? {
        return dao.getPokemonById(id)?.toPokemonCard()
    }

    override suspend fun getPokemonList(): List<PokemonCard> {
        return dao.getPokemonList().map { it.toPokemonCard() }
    }

    companion object {
        private const val MAX_SCORES = 10
    }
}
