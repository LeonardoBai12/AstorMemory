package io.lb.common.data.service

import io.lb.common.data.model.Score

interface DatabaseService {
    suspend fun getScores(): List<Score>
    suspend fun insertScore(score: Int)
}
