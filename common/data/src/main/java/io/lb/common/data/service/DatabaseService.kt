package io.lb.common.data.service

import io.lb.common.data.model.PokemonCard
import io.lb.common.data.model.Score

interface DatabaseService {
    suspend fun getScores(): List<Score>
    suspend fun getScoresByAmount(amount: Int): List<Score>
    suspend fun insertScore(score: Int, amount: Int)
    suspend fun insertPokemon(pokemonCard: PokemonCard)
    suspend fun getPokemonById(id: Int): PokemonCard?
}
