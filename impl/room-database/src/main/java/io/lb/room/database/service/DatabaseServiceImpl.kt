package io.lb.room.database.service

import io.lb.common.data.model.Score
import io.lb.common.data.service.DatabaseService
import io.lb.common.shared.error.MemoryGameException
import io.lb.room.database.dao.MemoryGameDao
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
        return dao.getScores().map { it.toPokemon() }
    }

    @Throws(MemoryGameException::class)
    override suspend fun insertScore(score: Int) {
        val scores = dao.getScores().sortedByDescending { it.score }
        if (scores.size < MAX_SCORES || score > scores.last().score) {
            dao.insertScore(ScoreEntity(score = score))
        }
    }
    companion object {
        private const val MAX_SCORES = 10
    }
}
