package io.lb.room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.room.database.model.ScoreEntity

/**
 * The DAO for the memory game database.
 */
@Dao
internal interface MemoryGameDao {
    /**
     * Inserts a score into the database.
     *
     * @param score The score to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity)

    /**
     * Retrieves all scores from the database.
     *
     * @return A list of all scores in the database.
     */
    @Query("SELECT * FROM scores")
    suspend fun getScores(): List<ScoreEntity>
}
