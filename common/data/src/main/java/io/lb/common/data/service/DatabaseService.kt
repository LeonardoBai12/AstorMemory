package io.lb.common.data.service

import io.lb.common.data.model.AstorCard
import io.lb.common.data.model.Score

interface DatabaseService {
    suspend fun getScores(): List<Score>
    suspend fun getScoresByAmount(amount: Int): List<Score>
    suspend fun insertScore(score: Int, amount: Int)
    suspend fun insertAstor(astorCard: AstorCard)
    suspend fun getAstorById(id: Int): AstorCard?
    suspend fun getAstorList(): List<AstorCard>
}
