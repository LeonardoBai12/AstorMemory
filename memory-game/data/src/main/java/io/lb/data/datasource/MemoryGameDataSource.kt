package io.lb.data.datasource

import io.lb.common.data.model.PokemonCard
import io.lb.common.data.service.ClientService
import io.lb.common.data.service.DatabaseService
import javax.inject.Inject

/**
 * Data source for the memory game.
 *
 * @property databaseService The service to interact with the database.
 * @property clientService The service to interact with the client.
 */
internal class MemoryGameDataSource @Inject constructor(
    private val databaseService: DatabaseService,
    private val clientService: ClientService
) {
    /**
     * Get the scores from the database.
     *
     * @return The scores.
     */
    suspend fun getScores() = databaseService.getScores()

    /**
     * Get the scores from the database by amount of cards.
     *
     * @param amount The amount of cards in the game.
     *
     * @return The scores.
     */
    suspend fun getScoresByAmount(amount: Int) = databaseService.getScoresByAmount(amount)

    /**
     * Insert a score into the database.
     *
     * @param score The score to insert.
     * @param amount The amount of cards in the game.
     */
    suspend fun insertScore(score: Int, amount: Int) = databaseService.insertScore(score, amount)

    /**
     * Get a Pokemon from the API.
     *
     * @param id The ID of the Pokemon to get.
     *
     * @return The Pokemon.
     */
    suspend fun getPokemonListFromRemote(amount: Int) = clientService.getPokemon(amount)

    /**
     * Get a Pokemon from the database.
     *
     * @param id The ID of the Pokemon to get.
     *
     * @return The Pokemon.
     */
    suspend fun getPokemonFromLocal(id: Int) = databaseService.getPokemonById(id)

    /**
     * Get a list of Pokemon from the database.
     *
     * @param amount The amount of Pokemon to get.
     *
     * @return The list of Pokemon.
     */
    suspend fun getPokemonListFromLocal() = databaseService.getPokemonList()

    /**
     * Insert a Pokemon into the database.
     *
     * @param pokemonCard The Pokemon to insert.
     */
    suspend fun insertPokemon(pokemonCard: PokemonCard) {
        databaseService.insertPokemon(pokemonCard)
    }
}
