package io.lb.room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.room.database.model.AstorCardEntity
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
    @Query("SELECT * FROM scores ORDER BY score DESC")
    suspend fun getScores(): List<ScoreEntity>

    /**
     * Retrieves all scores from the database.
     *
     * @param amount The amount of cards in the game.
     *
     * @return A list of all scores in the database.
     */
    @Query("SELECT * FROM scores WHERE amount = :amount ORDER BY score DESC")
    suspend fun getScoresByAmount(amount: Int): List<ScoreEntity>

    /**
     * Inserts a Astor into the database.
     *
     * @param astorCard The Astor to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAstor(astorCard: AstorCardEntity)

    /**
     * Retrieves all scores from the database.
     *
     * @return A list of all scores in the database.
     */
    @Query("SELECT * FROM astor_cards WHERE astorId = :id LIMIT 1")
    suspend fun getAstorById(id: Int): AstorCardEntity?

    /**
     * Retrieves a list of Astor from the database.
     *
     * @param amount The amount of Astor to get.
     *
     * @return A list of Astor.
     */
    @Query("SELECT * FROM astor_cards ORDER BY RANDOM()")
    suspend fun getAstorList(): List<AstorCardEntity>
}
