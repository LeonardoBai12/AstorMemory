package io.lb.room.database.service

import io.lb.common.data.model.AstorCard
import io.lb.common.data.model.Score
import io.lb.common.data.service.DatabaseService
import io.lb.common.shared.error.MemoryGameException
import io.lb.room.database.dao.MemoryGameDao
import io.lb.room.database.model.AstorCardEntity
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

    override suspend fun insertAstor(astorCard: AstorCard) {
        dao.insertAstor(
            AstorCardEntity(
                astorId = astorCard.astorId,
                name = astorCard.name,
                imageUrl = astorCard.imageUrl.orEmpty(),
                imageData = astorCard.imageData
            )
        )
    }

    override suspend fun getAstorById(id: Int): AstorCard? {
        return dao.getAstorById(id)?.toAstorCard()
    }

    override suspend fun getAstorList(): List<AstorCard> {
        return dao.getAstorList().map { it.toAstorCard() }
    }

    companion object {
        private const val MAX_SCORES = 10
    }
}
