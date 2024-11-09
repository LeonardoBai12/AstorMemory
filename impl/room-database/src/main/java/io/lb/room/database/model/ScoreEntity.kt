package io.lb.room.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.lb.common.data.model.Score
import java.util.UUID

/**
 * Represents a score in the database.
 *
 * @property id The unique identifier of the score.
 * @property score The score value.
 * @property timeMillis The time the score was recorded.
 */
@Entity(tableName = "scores")
internal data class ScoreEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val score: Int,
    val amount: Int,
    val timeMillis: Long = System.currentTimeMillis()
) {
    fun toScore() = Score(
        score = score,
        timeMillis = timeMillis
    )
}
