package io.lb.room.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.lb.room.database.model.PokemonCardEntity
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
    @Query("SELECT * FROM scores WHERE amount = :amount ORDER BY score DESC LIMIT 10")
    suspend fun getScoresByAmount(amount: Int): List<ScoreEntity>

    /**
     * Inserts a Pokemon into the database.
     *
     * @param pokemonCard The Pokemon to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonCard: PokemonCardEntity)

    /**
     * Retrieves all scores from the database.
     *
     * @return A list of all scores in the database.
     */
    @Query("SELECT * FROM pokemon_cards WHERE pokemonId = :id LIMIT 1")
    suspend fun getPokemonById(id: Int): PokemonCardEntity?

    /**
     * Retrieves a list of Pokemon from the database.
     *
     * @param amount The amount of Pokemon to get.
     *
     * @return A list of Pokemon.
     */
    @Query("SELECT * FROM pokemon_cards ORDER BY RANDOM()")
    suspend fun getPokemonList(): List<PokemonCardEntity>
}
