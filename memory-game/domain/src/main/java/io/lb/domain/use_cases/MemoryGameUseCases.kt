package io.lb.domain.use_cases

/**
 * Use cases for the memory game.
 *
 * @property getScoresUseCase The use case to get the scores.
 * @property saveScoreUseCase The use case to save a score.
 * @property getMemoryGameUseCase The use case to get the memory game.
 * @property calculateScoreUseCase The use case to calculate the score.
 */
data class MemoryGameUseCases(
    val getScoresUseCase: GetScoresUseCase,
    val saveScoreUseCase: SaveScoreUseCase,
    val getMemoryGameUseCase: GetPokemonPairsUseCase,
    val calculateScoreUseCase: CalculateScoreUseCase
)
